package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityPlaySurveillanceVideoBinding

/**
 * Created by Chenwei on 2023/12/7.
 */
class PlaySurveillanceVideoActivity: BaseActivity<ActivityPlaySurveillanceVideoBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityPlaySurveillanceVideoBinding? {
        return ActivityPlaySurveillanceVideoBinding.inflate(layoutInflater)
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