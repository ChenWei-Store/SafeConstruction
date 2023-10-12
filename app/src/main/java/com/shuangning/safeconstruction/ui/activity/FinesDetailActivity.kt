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