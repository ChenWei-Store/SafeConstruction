package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.FinesDetailResp
import com.shuangning.safeconstruction.databinding.ActivityFinesDetailBinding
import com.shuangning.safeconstruction.ui.viewmodel.FinesDetailViewModel
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/10/12.
 */
class FinesDetailActivity: BaseActivity<ActivityFinesDetailBinding>() {
    private var id = ""
    private val viewModel by viewModels<FinesDetailViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityFinesDetailBinding? {
        return ActivityFinesDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("罚款详情")
    }

    private fun showDataToUi(finesDetailResp: FinesDetailResp) {
        val statusStr = when(finesDetailResp.status) {
            0 -> "已完成"
            1 ->"待审核"
            2-> "待整改"
            else -> {
                "待审核"
            }
        }
        binding?.apply {
            tvTitle.text = "$statusStr 处理人：${finesDetailResp.jiancharen}"
            tvSafe.text = "安全"
            tvPrice.text = finesDetailResp.leijijine.toString()
            tvCompany.text = finesDetailResp.beichufadanwei
            tvMoney.text = finesDetailResp.leijijine.toString()
            tvVetting.text = finesDetailResp.shifoushenpi
            tvCheckPerson.text = finesDetailResp.jiancharen
            tvCheckUnit.text = finesDetailResp.jianchadanwei
        }
    }

    override fun initData() {
        id = intent?.getStringExtra(ID)?:""
        LoadingManager.startLoading(this)
        viewModel.getData(id)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
    }

    override fun observeViewModel() {
        viewModel.data.observe(this){
            it?.let {
                showDataToUi(it)
            }
        }
    }

    companion object{
        const val ID = "ID"
        fun startTo(ctx: Context, id: String){
            ActivityUtils.start(ctx, FinesDetailActivity::class.java){
                putExtra(ID, id)
            }
        }
    }

}