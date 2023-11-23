package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityAddFineItemBinding
import com.shuangning.safeconstruction.databinding.ActivityAddFinesBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils

/**
 * Created by Chenwei on 2023/10/14.
 */
class AddFineItemActivity: BaseActivity<ActivityAddFineItemBinding>() {
    private var selectedPosition = 0
    private var views = mutableListOf<View>()
    private var tvs = mutableListOf<TextView>()
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
        }
    }

    override fun observeViewModel() {
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
}