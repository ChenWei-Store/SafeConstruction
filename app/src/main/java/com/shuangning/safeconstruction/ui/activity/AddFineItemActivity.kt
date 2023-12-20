package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.other.FineItem
import com.shuangning.safeconstruction.bean.request.AddFineItemReq
import com.shuangning.safeconstruction.databinding.ActivityAddFineItemBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.viewmodel.AddFineItemViewModel
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/10/14.
 */
class AddFineItemActivity: BaseActivity<ActivityAddFineItemBinding>() {
    private var selectedPosition = 0
    private var views = mutableListOf<View>()
    private var tvs = mutableListOf<TextView>()
    private val viewModel by viewModels<AddFineItemViewModel>()
    private var id: Int = 0
    private val types = arrayOf("安全", "质量", "环保", "日常管理")
    private val fineItem = FineItem()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAddFineItemBinding? {
        return ActivityAddFineItemBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("罚款项")
        binding?.apply {
            views.add(viewSafe)
            views.add(viewQuantity)
            views.add(viewGreen)
            views.add(viewRoutineManage)
            tvs.add(tvSafe)
            tvs.add(tvQuantity)
            tvs.add(tvGreen)
            tvs.add(tvRouteManage)
        }
        views[selectedPosition].isSelected = true
        tvs[selectedPosition].isSelected = true
    }

    override fun initData() {
        LoadingManager.startLoading(this)
        viewModel.getId()
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        for (idx in 0..views.size - 1){
            views[idx].setOnClickListener {
                if (selectedPosition == idx){
                    return@setOnClickListener
                }
                selectedPosition = idx
                updateType()
            }
        }

        binding?.viewBottom?.setOnClickListener {
            val price = binding?.etMoney?.text
            if (price.isNullOrBlank()){
                XPopCreateUtils.showTipDialog(this, "提示", "请输入处罚金额")
                return@setOnClickListener
            }
            val desc = binding?.etInstruction?.text

            if (desc.isNullOrBlank()){
                XPopCreateUtils.showTipDialog(this, "提示", "请输入处罚说明")
                return@setOnClickListener
            }
            val type = types[selectedPosition]
            fineItem.dealType = type
            fineItem.money = price.toString().toFloat()
            fineItem.desc = desc.toString()
            val req = AddFineItemReq(desc.toString(), price.toString(), type, id)
            LoadingManager.startLoading(this)
            viewModel.commit(req)
        }
    }

    override fun observeViewModel() {
        viewModel.id.observe(this){
            it?.let {
                id = it
            }
            LoadingManager.stopLoading()
        }

        viewModel.result.observe(this){
            it?.let {
                if (it){
                    val intent = Intent()
                    intent.putExtra("money", fineItem.money)
                    intent.putExtra("dealType", fineItem.dealType)
                    intent.putExtra("desc", fineItem.desc)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    private fun updateType(){
        for(idx in 0..views.size - 1){
            if (selectedPosition == idx){
                views[idx].isSelected = true
                tvs[idx].isSelected = true
            }else{
                views[idx].isSelected = false
                tvs[idx].isSelected = false
            }
        }
    }

    companion object{
        fun startForResult(ctx: Context, requestCode: Int){
            ActivityUtils.startForResult(ctx, AddFineItemActivity::class.java, requestCode)
        }
    }
}