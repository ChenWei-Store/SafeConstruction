package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityClockInOutBinding

/**
 * Created by Chenwei on 2023/10/18.
 */
class ClockInOrOutActivity: BaseActivity<ActivityClockInOutBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityClockInOutBinding? {
        return ActivityClockInOutBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("考勤打卡")
        showUI()
    }

    private fun showUI(){
        val isInScope = true
        if (isInScope){
            binding?.iv?.setBackgroundResource(R.drawable.circle_13ce7f)
            binding?.tvClockInOut?.text = "外勤打卡"
        }else{
            binding?.iv?.setBackgroundResource(R.drawable.circle_main_color)
            binding?.tvClockInOut?.text = "考勤打卡"
        }
        binding?.tvLocation?.text = "江苏省南京市情怀区朝天宫街道汉中路汉中门广场江苏省南京市情怀区朝"
        binding?.tvTime?.text = "18:43:41"
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