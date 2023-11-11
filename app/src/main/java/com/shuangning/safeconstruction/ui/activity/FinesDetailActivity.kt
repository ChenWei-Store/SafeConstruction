package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityFinesDetailBinding

/**
 * Created by Chenwei on 2023/10/12.
 */
class FinesDetailActivity: BaseActivity<ActivityFinesDetailBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityFinesDetailBinding? {
        return ActivityFinesDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("罚款详情")
        binding?.apply {
            tvTitle.text = "审批中 处理人：监理"
            tvSafe.text = "安全"
            tvPrice.text = "100.0"
            tvCompany.text = "南京交通工程有限公司"
            tvMoney.text = "100.0"
            tvVetting.text = "是"
            tvCheckPerson.text = "监理"
            tvCheckUnit.text = "江苏润通项目管理有限公司"
        }
    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
    }

    override fun observeViewModel() {
    }
}