package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.azhon.appupdate.manager.DownloadManager
import com.shuangning.safeconstruction.BuildConfig
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.databinding.ActivityMainBinding
import com.shuangning.safeconstruction.manager.PermissionManager
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.fragment.HomeFragment
import com.shuangning.safeconstruction.ui.fragment.MineFragment
import com.shuangning.safeconstruction.ui.viewmodel.MineViewModel
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/7.
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var mineFragment: MineFragment ?= null
    private val homeFragment = HomeFragment()
    private val mineViewModel by viewModels<MineViewModel>()
    private var name = ""
    private var companyName = ""
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding? {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        switchFragment(homeFragment, R.id.fl_container)
        setSelected(true)
        PermissionManager.reqFile(this)
    }

    override fun initData() {
        LoadingManager.startLoading(this)
        mineViewModel.getUserInfo()
        mineViewModel.getVersion()
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
            if (mineFragment == null){
                mineFragment = MineFragment.newInstance(name, companyName)
            }
            mineFragment?.let {
                switchFragment(it, R.id.fl_container)
            }
            setSelected(false)
        }

        binding?.ivCenter?.setOnClickListener {
            StartActivityManager.startToProblemReport(this)
        }
    }

    override fun observeViewModel() {
        mineViewModel.userInfo.observe(this) {
            it?.let {
                name = it.extend.name
                companyName = it.extend.company.realCompany.companyName
            }
            LoadingManager.stopLoading()
        }
        mineViewModel.version.observe(this){
            it?.let {
                if (it.url.isEmpty()){
                    return@let
                }
                DownloadManager.Builder(this).run {
                    apkUrl(it.url)
                    apkName("双宁安全管控.apk")
                    smallIcon(R.mipmap.ic_launcher)
                    apkVersionCode(it.versionCode.toInt())
                    apkVersionName(it.versionName)
                    apkSize(it.capacity.toString())
                    apkDescription(it.content)
                    enableLog(BuildConfig.DEBUG)
                    showNewerToast(true)
                    forcedUpgrade(true)
                    showNotification(false)
                    //省略一些非必须参数...
                    build()
                }.download()
            }
        }
    }

    fun setSelected(isHomeSelected: Boolean) {
        binding?.tvHome?.isSelected = isHomeSelected
        binding?.tvMine?.isSelected = !isHomeSelected
        val homeTintColor = if (isHomeSelected) {
            R.color.c_0A8BD6
        } else {
            R.color.c_222
        }
        val mineTintColor = if (isHomeSelected) {
            R.color.c_222
        } else {
            R.color.c_0A8BD6
        }
        binding?.ivLeft?.setImageDrawable(
            UIUtils.setTintDrawable(
                this,
                R.drawable.main_tab_home,
                homeTintColor
            )
        )
        binding?.ivRight?.setImageDrawable(
            UIUtils.setTintDrawable(
                this,
                R.drawable.main_tab_mine,
                mineTintColor
            )
        )

    }
}