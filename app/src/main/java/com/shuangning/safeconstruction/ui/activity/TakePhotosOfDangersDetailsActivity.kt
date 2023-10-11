package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityTakePhotosOfDangersDetailBinding
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
class TakePhotosOfDangersDetailsActivity: BaseActivity<ActivityTakePhotosOfDangersDetailBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityTakePhotosOfDangersDetailBinding? {
        return ActivityTakePhotosOfDangersDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.take_photos_of_dangers))
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