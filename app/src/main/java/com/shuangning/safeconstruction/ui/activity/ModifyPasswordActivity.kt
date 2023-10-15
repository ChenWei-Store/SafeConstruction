package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityModifyPasswordBinding

/**
 * Created by Chenwei on 2023/10/14.
 */
class ModifyPasswordActivity: BaseActivity<ActivityModifyPasswordBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityModifyPasswordBinding? {
        return ActivityModifyPasswordBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("修改密码")
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