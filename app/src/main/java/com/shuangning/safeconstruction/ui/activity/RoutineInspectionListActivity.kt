package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.other.ContentSelectTypeBean
import com.shuangning.safeconstruction.bean.other.HeaderSelectTypeBean
import com.shuangning.safeconstruction.databinding.ActivityRoutineInspectionBinding
import com.shuangning.safeconstruction.ui.adapter.RoutineInspectionMultiAdapter
import com.shuangning.safeconstruction.manager.XPopCreateUtils

/**
 * Created by Chenwei on 2023/10/14.
 */
class RoutineInspectionListActivity: BaseActivity<ActivityRoutineInspectionBinding>() {
    private var routineInspectionAdapter: RoutineInspectionMultiAdapter?= null
    private val data: MutableList<ItemViewType> = mutableListOf()
    private val selectTypeData: MutableList<IItemViewType> = mutableListOf()
    private var rightText: String?= null
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRoutineInspectionBinding? {
        return ActivityRoutineInspectionBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("巡查记录")
        rightText?.let {
            binding?.viewTitle?.setRightText(it)
        }
        routineInspectionAdapter = RoutineInspectionMultiAdapter(data)
        binding?.rv?.apply {
            adapter = routineInspectionAdapter
            layoutManager = LinearLayoutManager(this@RoutineInspectionListActivity)
        }
        routineInspectionAdapter?.notifyDataSetChanged()
    }

    override fun initData() {
        data.add(ItemViewType(HEADER))
        data.add(ItemViewType())
        data.add(ItemViewType())
        data.add(ItemViewType())
        data.add(ItemViewType(HEADER))
        data.add(ItemViewType())

        selectTypeData.add(HeaderSelectTypeBean())
        selectTypeData.add(ContentSelectTypeBean("全部", true))
        selectTypeData.add(ContentSelectTypeBean( "指挥部"))
        selectTypeData.add(ContentSelectTypeBean("GX-JL-1标"))
        selectTypeData.add(ContentSelectTypeBean( "GX-1标"))
        selectTypeData.add(ContentSelectTypeBean( "GX-2标"))
        rightText = "全部"
    }


    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewTitle?.setRightTextListener {
            XPopCreateUtils.showSelectTypeView(this@RoutineInspectionListActivity,
                binding?.viewTitle!!, selectTypeData
            ){
                val item = selectTypeData[it]
                if (item is ContentSelectTypeBean) {
                    rightText = item.title
                    binding?.viewTitle?.setRightText(item.title)
                }
            }

        }
    }

    override fun observeViewModel() {
    }
}