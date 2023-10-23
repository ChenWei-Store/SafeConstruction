package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityLoginBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.utils.APPUtils
import com.shuangning.safeconstruction.utils.ToastUtil

/**
 * Created by Chenwei on 2023/10/7.
 */
class LoginActivity: BaseActivity<ActivityLoginBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityLoginBinding? {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.tvVersion?.text = "v${APPUtils.getVersionName()}"
        UserInfoManager.getUserInfo()?.apply {
            binding?.etName?.setText(userName)
            binding?.etPwd?.setText(pwd)
        }
    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.btnLogin?.setOnClickListener {
            startToLogin()
        }
    }

    override fun observeViewModel() {
    }

    private fun startToLogin(){
        val name = binding?.etName?.text.toString()
        val pwd = binding?.etPwd?.text.toString()
        if(name.isBlank()){
            XPopCreateUtils.showTipDialog(this, "提示", "请输入手机号或用户名")
            return
        }
        if (pwd.isBlank()){
            XPopCreateUtils.showTipDialog(this, "提示", "请输入密码")
            return
        }
        UserInfoManager.setUserInfo(name, pwd)
        StartActivityManager.startToMain(LoginActivity@this)
        finish()
    }
}