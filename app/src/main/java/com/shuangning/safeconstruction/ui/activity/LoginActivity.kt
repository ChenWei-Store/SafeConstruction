package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityLoginBinding

/**
 * Created by Chenwei on 2023/10/7.
 */
class LoginActivity: BaseActivity<ActivityLoginBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityLoginBinding? {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
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