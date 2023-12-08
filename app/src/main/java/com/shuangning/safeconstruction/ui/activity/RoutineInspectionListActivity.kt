package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.other.ContentSelectTypeBean
import com.shuangning.safeconstruction.bean.other.HeaderSelectTypeBean
import com.shuangning.safeconstruction.bean.response.RoutineInspectionItem
import com.shuangning.safeconstruction.databinding.ActivityRoutineInspectionBinding
import com.shuangning.safeconstruction.ui.adapter.RoutineInspectionMultiAdapter
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.viewmodel.RoutineInspectionListViewModel
import com.shuangning.safeconstruction.utils2.MyLog

/**
 * Created by Chenwei on 2023/10/14.
 */
class RoutineInspectionListActivity : BaseActivity<ActivityRoutineInspectionBinding>() {
    private var routineInspectionAdapter: RoutineInspectionMultiAdapter? = null
    private val data: MutableList<IItemViewType> = mutableListOf()
    private val selectTypeData: MutableList<IItemViewType> = mutableListOf()
    private var rightText: String = "全部"
    private val viewModel by viewModels<RoutineInspectionListViewModel>()
    private val pageSize = 10
    private var pageNo = 0
    private var isRefresh = false
    private var isLoadMore = false
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRoutineInspectionBinding? {
        return ActivityRoutineInspectionBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        MyLog.d("initView")
        binding?.viewTitle?.setTitle("巡查记录")
        binding?.viewTitle?.setRightText(rightText)
        routineInspectionAdapter = RoutineInspectionMultiAdapter(data)
        binding?.rv?.apply {
            adapter = routineInspectionAdapter
            layoutManager = LinearLayoutManager(this@RoutineInspectionListActivity)
        }
        binding?.refreshLayout?.setRefreshHeader(ClassicsHeader(this))
        binding?.refreshLayout?.setRefreshFooter(ClassicsFooter(this))
    }

    override fun initData() {
        LoadingManager.startLoading(this)
        val biaoduan = getBiaoDuan()
        viewModel.getData(biaoduan, pageNo, pageSize)
    }

    private fun getBiaoDuan(): String {
        val biaoduan = if (rightText == "全部") {
            ""
        } else {
            rightText
        }
        return biaoduan
    }


    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewTitle?.setRightTextListener {
            XPopCreateUtils.showSelectTypeView(
                this@RoutineInspectionListActivity,
                binding?.viewTitle!!, selectTypeData
            ) {
                val item = selectTypeData[it]
                if (item is ContentSelectTypeBean) {
                    rightText = item.title
                    binding?.viewTitle?.setRightText(item.title)
                    isRefresh = true
                    pageNo = 0
                    getList()
                }
            }
        }
        binding?.refreshLayout?.setOnRefreshListener {
            isRefresh = true
            pageNo = 0
            getList()
        }

        binding?.refreshLayout?.setOnLoadMoreListener {
            isLoadMore = true
            pageNo++
            getList()
        }
    }

    private fun getList() {
        LoadingManager.startLoading(this)
        viewModel.onlyGetList(getBiaoDuan(), pageNo, pageSize)
    }

    override fun observeViewModel() {
        viewModel.result.observe(this){
            it?.let {
                initSelectType(it.sections)
                initList(it.routineInspectionList?.result)
            }
            MyLog.d("notifyDataSetChanged")
            routineInspectionAdapter?.notifyDataSetChanged()
            LoadingManager.stopLoading()
        }

        viewModel.data.observe(this){
            if (isRefresh){
                data.clear()
            }
            it?.result?.apply {
                data.addAll(this)
            }
            routineInspectionAdapter?.notifyDataSetChanged()
            LoadingManager.stopLoading()
            if (isRefresh){
                binding?.refreshLayout?.finishRefresh()
                isRefresh = false
            }
            if (isLoadMore){
                binding?.refreshLayout?.finishLoadMore()
                isRefresh = false
            }
        }
    }

    private fun initSelectType(sections: MutableList<String>?) {
        selectTypeData.add(HeaderSelectTypeBean())
        selectTypeData.add(ContentSelectTypeBean("全部", true))
        sections?.forEach {
            selectTypeData.add(ContentSelectTypeBean(it))
        }
    }

    private fun initList(routineInspectionList: MutableList<RoutineInspectionItem>?) {
        routineInspectionList?.let {
            data.addAll(it)
        }
    }
}