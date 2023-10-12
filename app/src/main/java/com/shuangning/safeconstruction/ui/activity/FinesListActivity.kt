package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.databinding.ActivityFinesBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.FinesAdapter
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/12.
 */
class FinesListActivity: BaseActivity<ActivityFinesBinding>() {
    private var finesAdapter: FinesAdapter?= null
    private val data: MutableList<ItemViewType> = mutableListOf()

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityFinesBinding? {
        return ActivityFinesBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.fines_list))

        finesAdapter = FinesAdapter(data)
        binding?.rv?.apply {
            adapter = finesAdapter
            layoutManager = LinearLayoutManager(this@FinesListActivity)
        }
    }

    override fun initData() {
        data.add(ItemViewType())
        data.add(ItemViewType())
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        finesAdapter?.setOnItemClickListener(object: OnItemClickListener<ItemViewType>{
            override fun onItemClick(data: ItemViewType, position: Int) {
                StartActivityManager.startToFinesDetail(this@FinesListActivity)
            }

        })

        binding?.llBottom?.setOnClickListener {

        }
    }

    override fun observeViewModel() {
    }
}