package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.bean.other.SelectCauseBean
import com.shuangning.safeconstruction.databinding.ActivitySelectCauseBinding
import com.shuangning.safeconstruction.ui.adapter.SelectCauseAdapter

/**
 * Created by Chenwei on 2023/10/18.
 */
class SelectCauseActivity: BaseActivity<ActivitySelectCauseBinding>() {
    private var selectCauseAdapter: SelectCauseAdapter?= null
    private var data: MutableList<SelectCauseBean> = mutableListOf()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivitySelectCauseBinding? {
        return ActivitySelectCauseBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("原因选择")
        selectCauseAdapter = SelectCauseAdapter(data)
        binding?.rv?.apply {
            layoutManager = LinearLayoutManager(this@SelectCauseActivity, LinearLayoutManager.VERTICAL, false)
            adapter = selectCauseAdapter
        }
    }

    override fun initData() {
        data.add(SelectCauseBean("管理制度"))
        data.add(SelectCauseBean("岗位职责"))
        data.add(SelectCauseBean("专项方案"))
        data.add(SelectCauseBean("安全措施"))
        data.add(SelectCauseBean("思想行为"))
        data.add(SelectCauseBean("经费保障"))
        data.add(SelectCauseBean("其他"))
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.llConfirm?.setOnClickListener {
            finish()
        }
    }

    override fun observeViewModel() {
    }

}