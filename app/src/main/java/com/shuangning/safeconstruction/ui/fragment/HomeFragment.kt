package com.shuangning.safeconstruction.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.BaseFragment
import com.shuangning.safeconstruction.databinding.FragmentHomeBinding

/**
 * Created by Chenwei on 2023/10/7.
 */
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding? {
        return FragmentHomeBinding.inflate(inflater, container, false)
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