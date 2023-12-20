package com.shuangning.safeconstruction.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.RectificationAndReplyItem
import com.shuangning.safeconstruction.databinding.ActivityRectificationAndReplyBinding
import com.shuangning.safeconstruction.extension.newTab
import com.shuangning.safeconstruction.ui.adapter.RectificationAndReplyAdapter
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.ui.viewmodel.RectificationAndReplyViewModel

/**
 * Created by Chenwei on 2023/10/11.
 */
const val ERROR = -1
const val TO_BE_RECTIFIED = 0
const val TO_BE_EXAMINE = 1
const val COMPLETED = 2

class RectificationAndReplyActivity : BaseActivity<ActivityRectificationAndReplyBinding>() {
    private var replyAdapter: RectificationAndReplyAdapter? = null
    private val toBeRectifiedData: MutableList<RectificationAndReplyItem> = mutableListOf()
    private val toBeExamineData: MutableList<RectificationAndReplyItem> = mutableListOf()
    private val completedData: MutableList<RectificationAndReplyItem> = mutableListOf()
    private val selectTypeData: MutableList<IItemViewType> = mutableListOf()
    private var selectedTab = TO_BE_RECTIFIED
    private val viewModel by viewModels<RectificationAndReplyViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRectificationAndReplyBinding? {
        return ActivityRectificationAndReplyBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.rectification_and_reply))
        replyAdapter = RectificationAndReplyAdapter(toBeRectifiedData)
        binding?.rv?.apply {
            adapter = replyAdapter
            layoutManager = LinearLayoutManager(this@RectificationAndReplyActivity)
        }
    }

    private fun initTab() {
        binding?.tabLayout?.removeAllTabs()
        binding?.tabLayout?.apply {
            addTab(newTab(R.string.to_be_rectified, toBeRectifiedData.size), true)
            addTab(newTab(R.string.to_be_examine, toBeExamineData.size))
//            addTab(newTab(R.string.completed, -1))
        }
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        LoadingManager.startLoading(this)
        viewModel.getData()
    }
    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        replyAdapter?.setOnItemClickListener(object :
            OnItemClickListener<RectificationAndReplyItem> {
            override fun onItemClick(data: RectificationAndReplyItem, position: Int) {
                RectifucationAndReplyDetailActivity.startTo(
                    this@RectificationAndReplyActivity, data.taskStatus,
                    data.id.toString(),data.flowInstanceId, data.taskInstanceId
                )
            }
        })

        binding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedTab = tab?.position ?: ERROR
                if (selectedTab > ERROR) {
                    replyAdapter?.setSelectedTab(selectedTab)
                    if (selectedTab == TO_BE_RECTIFIED) {
                        replyAdapter?.refreshData(toBeRectifiedData)
                    } else if (selectedTab == TO_BE_EXAMINE) {
                        replyAdapter?.refreshData(toBeExamineData)
                    } else {
                        replyAdapter?.refreshData(completedData)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeViewModel() {
        viewModel.result.observe(this) { it ->
            toBeExamineData.clear()
            toBeRectifiedData.clear()
            completedData.clear()
            it.forEach {
                when (it.taskStatus) {
                    "DAILY_CHECK_AUDIT","DAILY_CHECK_REJECT","DAILY_CHECK_APPROVAL" -> {
                        toBeExamineData.add(it)
                    }
                    "DAILY_CHECK_CORRECT" -> {
                        toBeRectifiedData.add(it)
                    }
                    else -> {
                        completedData.add(it)
                    }
                }
            }
            initTab()
            replyAdapter?.apply {
                refreshData(toBeRectifiedData)
                notifyDataSetChanged()
            }
            LoadingManager.stopLoading()
        }
    }
}