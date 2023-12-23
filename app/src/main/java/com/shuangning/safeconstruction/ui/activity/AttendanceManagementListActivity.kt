package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityAttendanceManagementBinding

/**
 * Created by Chenwei on 2023/10/15.
 */
class AttendanceManagementListActivity: BaseActivity<ActivityAttendanceManagementBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAttendanceManagementBinding? {
        return ActivityAttendanceManagementBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("考勤管理")
    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.view1?.setOnClickListener {
            AttendanceManagementDetailActivity.startTo(this@AttendanceManagementListActivity, 1)
        }
        binding?.view2?.setOnClickListener {
            AttendanceManagementDetailActivity.startTo(this@AttendanceManagementListActivity, 2)

        }
    }

    override fun observeViewModel() {
    }
}