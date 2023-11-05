package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ActivityGroupEducationDetailBinding
import com.shuangning.safeconstruction.ui.adapter.GroupEducationDetailAdapter

/**
 * Created by Chenwei on 2023/11/5.
 */
class GroupEducationDetailActivity: BaseActivity<ActivityGroupEducationDetailBinding>() {
    private var detailAdapter: GroupEducationDetailAdapter?= null
    private var data: MutableList<ItemViewType> = mutableListOf()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityGroupEducationDetailBinding? {
        return ActivityGroupEducationDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        detailAdapter = GroupEducationDetailAdapter(data)
        binding?.rv?.apply {
            layoutManager = LinearLayoutManager(this@GroupEducationDetailActivity)
            adapter = detailAdapter
        }
    }

    override fun initData() {
        data.add(ItemViewType(HEADER))
        data.add(ItemViewType())
        data.add(ItemViewType())
        data.add(ItemViewType())

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