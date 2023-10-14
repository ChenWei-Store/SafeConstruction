package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityAddFineItemBinding
import com.shuangning.safeconstruction.databinding.ActivityAddFinesBinding

/**
 * Created by Chenwei on 2023/10/14.
 */
class AddFineItemActivity: BaseActivity<ActivityAddFineItemBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAddFineItemBinding? {
        return ActivityAddFineItemBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("罚款项")
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