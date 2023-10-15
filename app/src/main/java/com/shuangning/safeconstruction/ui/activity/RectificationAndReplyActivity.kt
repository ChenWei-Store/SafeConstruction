package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.CONTENT
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.SelectTypeBean
import com.shuangning.safeconstruction.databinding.ActivityRectificationAndReplyBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.RectificationAndReplyAdapter
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.manager.XPopCreateUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
class RectificationAndReplyActivity: BaseActivity<ActivityRectificationAndReplyBinding>() {
    private var replyAdapter: RectificationAndReplyAdapter? = null
    private val data: MutableList<ItemViewType> = mutableListOf()
    private val selectTypeData: MutableList<SelectTypeBean> = mutableListOf()

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRectificationAndReplyBinding? {
        return ActivityRectificationAndReplyBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.rectification_and_reply))
        binding?.viewTitle?.setRightText {
            it?.visibility = View.VISIBLE
            it?.setOnClickListener {
                XPopCreateUtils.showSelectTypeView(this@RectificationAndReplyActivity,
                    binding?.viewTitle!!, selectTypeData
                )
            }
        }
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
//        data.add(ItemViewType())

        selectTypeData.add(SelectTypeBean(CONTENT, "全部"))
        selectTypeData.add(SelectTypeBean(CONTENT, "指挥部"))
        selectTypeData.add(SelectTypeBean(CONTENT, "GX-JL-1标"))
        selectTypeData.add(SelectTypeBean(CONTENT, "GX-1标"))
        selectTypeData.add(SelectTypeBean(CONTENT, "GX-2标"))
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        replyAdapter?.setOnItemClickListener(object: OnItemClickListener<ItemViewType>{
            override fun onItemClick(data: ItemViewType, position: Int) {
                StartActivityManager.startToQuestionOperator(this@RectificationAndReplyActivity)
            }
        })

    }

    override fun observeViewModel() {
    }
}