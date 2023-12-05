package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ActivityAddFinesBinding
import com.shuangning.safeconstruction.ui.adapter.AddFinesAdapter
import com.shuangning.safeconstruction.ui.adapter.FINE_BOTTOM

/**
 * Created by Chenwei on 2023/10/14.
 */
class AddFinesActivity: BaseActivity<ActivityAddFinesBinding>() {
    private val data: MutableList<ItemViewType> = mutableListOf()
    private var addFinesAdapter: AddFinesAdapter?= null

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAddFinesBinding? {
        return ActivityAddFinesBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("新增罚款")
        addFinesAdapter = AddFinesAdapter(data)
        binding?.rv?.apply {
            adapter = addFinesAdapter
            layoutManager = LinearLayoutManager(this@AddFinesActivity)
        }

    }

    override fun initData() {
        data.add(ItemViewType(FINE_BOTTOM))
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewBottom?.setOnClickListener {

        }

    }

    override fun observeViewModel() {
    }
}