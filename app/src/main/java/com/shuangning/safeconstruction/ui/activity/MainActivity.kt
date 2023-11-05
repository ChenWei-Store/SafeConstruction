package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityMainBinding
import com.shuangning.safeconstruction.manager.PermissionManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.fragment.HomeFragment
import com.shuangning.safeconstruction.ui.fragment.MineFragment
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/7.
 */
class MainActivity: BaseActivity<ActivityMainBinding>() {
    private val mineFragment = MineFragment()
    private val homeFragment = HomeFragment()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding? {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        switchFragment(homeFragment, R.id.fl_container)
        setSelected(true)
        PermissionManager.reqFile(this)
    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewLeft?.setOnClickListener {
            switchFragment(homeFragment, R.id.fl_container)
            setSelected(true)
        }
        binding?.viewRight?.setOnClickListener {
            switchFragment(mineFragment, R.id.fl_container)
            setSelected(false)
        }

        binding?.ivCenter?.setOnClickListener {
            binding?.let {
                XPopCreateUtils.showAttachAdd(this@MainActivity, it.viewTabBottom)
            }
        }
    }

    override fun observeViewModel() {
    }

    fun setSelected(isHomeSelected: Boolean){
        binding?.tvHome?.isSelected = isHomeSelected
        binding?.tvMine?.isSelected = !isHomeSelected
        val homeTintColor = if (isHomeSelected){
            R.color.c_0A8BD6
        }else{
            R.color.c_222
        }
        val mineTintColor = if (isHomeSelected){
            R.color.c_222
        }else{
            R.color.c_0A8BD6
        }
        binding?.ivLeft?.setImageDrawable(UIUtils.setTintDrawable(this, R.drawable.main_tab_home, homeTintColor))
        binding?.ivRight?.setImageDrawable(UIUtils.setTintDrawable(this, R.drawable.main_tab_mine, mineTintColor))

    }
}