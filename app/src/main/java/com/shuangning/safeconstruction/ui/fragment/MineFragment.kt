package com.shuangning.safeconstruction.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lxj.xpopup.XPopup
import com.shuangning.safeconstruction.base.BaseFragment
import com.shuangning.safeconstruction.databinding.FragmentMineBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.utils.ToastUtil


/**
 * Created by Chenwei on 2023/10/7.
 */
class MineFragment: BaseFragment<FragmentMineBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMineBinding? {
        return FragmentMineBinding.inflate(inflater, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }

    override fun initListener() {
        binding?.viewModifyPwd?.setOnClickListener {
            activity?.apply {
                StartActivityManager.startToModifyPwdList(this)
            }
        }
        binding?.viewUpdateVersion?.setOnClickListener {
            ToastUtil.showLong("已经是最新版本")
        }
        binding?.viewClearCache?.setOnClickListener {
            XPopup.Builder(context).asConfirm("是否确认清除缓存?", "") {
                ToastUtil.showLong("清除缓存成功")
            }.show()
        }

        binding?.btnExitLogin?.setOnClickListener {

        }
    }

    override fun observeViewModel() {
    }
}