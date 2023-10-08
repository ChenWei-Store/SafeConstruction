package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityMainBinding
import com.shuangning.safeconstruction.ui.fragment.MineFragment

/**
 * Created by Chenwei on 2023/10/7.
 */
class MainActivity: BaseActivity<ActivityMainBinding>() {
    private val mineFragment = MineFragment()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding? {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        switchFragment(mineFragment, R.id.fl_container)
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