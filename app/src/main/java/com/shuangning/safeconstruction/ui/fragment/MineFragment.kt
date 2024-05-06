package com.shuangning.safeconstruction.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.azhon.appupdate.manager.DownloadManager
import com.lxj.xpopup.XPopup
import com.shuangning.safeconstruction.BuildConfig
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseFragment
import com.shuangning.safeconstruction.data.mmkv.MMKVResp
import com.shuangning.safeconstruction.databinding.FragmentMineBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.ui.viewmodel.MineViewModel
import com.shuangning.safeconstruction.utils.APPUtils
import com.shuangning.safeconstruction.utils.ToastUtil


/**
 * Created by Chenwei on 2023/10/7.
 */
class MineFragment : BaseFragment<FragmentMineBinding>() {
    private val name: String by lazy {
        arguments?.getString(NAME) ?: ""
    }

    private val companyName: String by lazy {
        arguments?.getString(COMPANY_NAME) ?: ""
    }
    private val mineViewModel by viewModels<MineViewModel>()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMineBinding? {
        return FragmentMineBinding.inflate(inflater, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.tvVersion?.text = APPUtils.getVersionName()
        binding?.tvName?.text = name
        binding?.tvDepartment?.text = companyName
    }

    override fun initData() {

    }

    override fun initListener() {
//        binding?.viewModifyPwd?.setOnClickListener {
//            activity?.apply {
//                StartActivityManager.startToModifyPwdList(this)
//            }
//        }
        binding?.viewUpdateVersion?.setOnClickListener {
            mineViewModel.getVersion()
        }
        binding?.viewClearCache?.setOnClickListener {
            XPopup.Builder(context).asConfirm("是否确认清除缓存?", "") {
//                clearCache()
                ToastUtil.showLong("清除缓存成功")
            }.show()
        }

        binding?.btnExitLogin?.setOnClickListener {
            activity?.apply {
                UserInfoManager.setLogin(false)
                StartActivityManager.startToLogin(this)
                finish()
            }
        }


    }

    private fun clearCache() {
        UserInfoManager.clear()
        MMKVResp.resp.clear()
    }

    override fun observeViewModel() {
        mineViewModel.version.observe(this) {
            it?.let {
                if (it.url.isEmpty()) {
                    return@let
                }
                activity?.let { it1 ->
                    DownloadManager.Builder(it1).run {
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
            }?:let {
                ToastUtil.showShort("请求失败")
            }
        }
    }

    companion object {
        const val NAME = "name"
        const val COMPANY_NAME = "companyName"
        fun newInstance(name: String, companyName: String): MineFragment {
            val bundle = Bundle()
            bundle.putString(NAME, name)
            bundle.putString(COMPANY_NAME, companyName)
            val fragment = MineFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}