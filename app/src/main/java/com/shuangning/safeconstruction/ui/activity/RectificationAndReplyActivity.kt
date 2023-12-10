package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.ContentSelectTypeBean
import com.shuangning.safeconstruction.bean.other.RectificationAndReplyBean
import com.shuangning.safeconstruction.databinding.ActivityRectificationAndReplyBinding
import com.shuangning.safeconstruction.extension.newTab
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.RectificationAndReplyAdapter
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
const val ERROR = -1
const val TO_BE_RECTIFIED = 2
const val TO_BE_EXAMINE = 1
const val COMPLETED = 0
class RectificationAndReplyActivity: BaseActivity<ActivityRectificationAndReplyBinding>() {
    private var replyAdapter: RectificationAndReplyAdapter? = null
    private val toBeRectifiedData: MutableList<RectificationAndReplyBean> = mutableListOf()
    private val toBeExamineData: MutableList<RectificationAndReplyBean> = mutableListOf()
    private val completedData: MutableList<RectificationAndReplyBean> = mutableListOf()
    private val selectTypeData: MutableList<IItemViewType> = mutableListOf()
    private var rightText: String?= null
    private var selectedTab = TO_BE_RECTIFIED
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRectificationAndReplyBinding? {
        return ActivityRectificationAndReplyBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.rectification_and_reply))
        rightText?.let {
            binding?.viewTitle?.setRightText(it)
        }
        replyAdapter = RectificationAndReplyAdapter(toBeRectifiedData)
        binding?.rv?.apply {
            adapter = replyAdapter
            layoutManager = LinearLayoutManager(this@RectificationAndReplyActivity)
        }
        replyAdapter?.notifyDataSetChanged()

        binding?.tabLayout?.apply {
            addTab(newTab( R.string.to_be_rectified, toBeRectifiedData.size), true)
            addTab(newTab( R.string.to_be_examine, toBeExamineData.size))
            addTab(newTab( R.string.completed, -1))
        }
    }

    override fun initData() {
        toBeRectifiedData.add(RectificationAndReplyBean())
        toBeRectifiedData.add(RectificationAndReplyBean())
//        data.add(ItemViewType())
        completedData.add(RectificationAndReplyBean())
        completedData.add(RectificationAndReplyBean())
        completedData.add(RectificationAndReplyBean())
        completedData.add(RectificationAndReplyBean())

        toBeExamineData.add(RectificationAndReplyBean())
        toBeExamineData.add(RectificationAndReplyBean())

        selectTypeData.add(ContentSelectTypeBean("全部", true))
        selectTypeData.add(ContentSelectTypeBean("指挥部"))
        selectTypeData.add(ContentSelectTypeBean( "GX-JL-1标"))
        selectTypeData.add(ContentSelectTypeBean("GX-1标"))
        selectTypeData.add(ContentSelectTypeBean("GX-2标"))
        rightText = "全部"
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        replyAdapter?.setOnItemClickListener(object: OnItemClickListener<RectificationAndReplyBean>{
            override fun onItemClick(data: RectificationAndReplyBean, position: Int) {
                QuestionOperatorActivity.startTo(this@RectificationAndReplyActivity, selectedTab,
                    "", QuestionOperatorActivity.FROM_RECTIFICATION_PEPLY)
            }
        })

        binding?.viewTitle?.setRightTextListener {
            XPopCreateUtils.showSelectTypeView(this@RectificationAndReplyActivity,
                binding?.viewTitle!!, selectTypeData
            ){
                val item = selectTypeData[it]
                if (item is ContentSelectTypeBean) {
                    rightText = item.title
                    binding?.viewTitle?.setRightText(item.title)
                }
            }
        }

        binding?.tabLayout?.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedTab = tab?.position?:ERROR
                if (selectedTab > ERROR){
                    replyAdapter?.setSelectedTab(selectedTab)
                    if (selectedTab == TO_BE_RECTIFIED){
                        replyAdapter?.refreshData(toBeRectifiedData)
                    }else if (selectedTab == TO_BE_EXAMINE){
                        replyAdapter?.refreshData(toBeExamineData)
                    }else{
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

    override fun observeViewModel() {
    }
}