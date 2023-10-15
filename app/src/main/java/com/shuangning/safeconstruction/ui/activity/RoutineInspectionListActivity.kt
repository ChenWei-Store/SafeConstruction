package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.CONTENT
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.other.SelectTypeBean
import com.shuangning.safeconstruction.databinding.ActivityRoutineInspectionBinding
import com.shuangning.safeconstruction.ui.adapter.RoutineInspectionAdapter
import com.shuangning.safeconstruction.ui.dialog.SelectTypeDialog
import com.shuangning.safeconstruction.utils2.XPopCreateUtils

/**
 * Created by Chenwei on 2023/10/14.
 */
class RoutineInspectionListActivity: BaseActivity<ActivityRoutineInspectionBinding>() {
    private var routineInspectionAdapter: RoutineInspectionAdapter?= null
    private val data: MutableList<ItemViewType> = mutableListOf()
    private val selectTypeData: MutableList<SelectTypeBean> = mutableListOf()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRoutineInspectionBinding? {
        return ActivityRoutineInspectionBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("巡查记录")
        binding?.viewTitle?.setRightText {
            it?.visibility = View.VISIBLE
            it?.setOnClickListener {
                XPopCreateUtils.showSelectTypeView(this@RoutineInspectionListActivity,
                    binding?.viewTitle!!, selectTypeData
                )
            }
        }
        routineInspectionAdapter = RoutineInspectionAdapter(data)
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

        selectTypeData.add(SelectTypeBean(HEADER,))
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
    }

    override fun observeViewModel() {
    }
}