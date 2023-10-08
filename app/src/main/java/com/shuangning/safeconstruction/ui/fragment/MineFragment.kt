package com.shuangning.safeconstruction.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.BaseFragment
import com.shuangning.safeconstruction.databinding.FragmentHomeBinding
import com.shuangning.safeconstruction.databinding.FragmentMineBinding

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
    }

    override fun observeViewModel() {
    }
}