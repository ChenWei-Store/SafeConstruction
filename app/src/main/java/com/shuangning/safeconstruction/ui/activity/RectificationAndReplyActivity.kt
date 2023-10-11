package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ActivityRectificationAndReplyBinding
import com.shuangning.safeconstruction.ui.adapter.RectificationAndReplyAdapter
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
class RectificationAndReplyActivity: BaseActivity<ActivityRectificationAndReplyBinding>() {
    private var replyAdapter: RectificationAndReplyAdapter? = null
    private val data: MutableList<ItemViewType> = mutableListOf()

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRectificationAndReplyBinding? {
        return ActivityRectificationAndReplyBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.rectification_and_reply))
        replyAdapter = RectificationAndReplyAdapter(data)
        binding?.rv?.apply {
            adapter = replyAdapter
            layoutManager = LinearLayoutManager(this@RectificationAndReplyActivity)
        }
        replyAdapter?.notifyDataSetChanged()
    }

    override fun initData() {
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