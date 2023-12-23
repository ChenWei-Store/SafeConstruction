package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.enums.PopupPosition
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.CONTENT
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.event.GroupEducationOption
import com.shuangning.safeconstruction.bean.response.GroupEducationItem
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.data.mmkv.MMKVResp
import com.shuangning.safeconstruction.databinding.ActivityGroupEducationListBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.GroupEducationMulAdapter
import com.shuangning.safeconstruction.ui.dialog.GroupEducationTipDialog
import com.shuangning.safeconstruction.ui.dialog.SelectGroupEducationOptionDialog
import com.shuangning.safeconstruction.ui.viewmodel.GroupEducationListViewModel
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.TimeUtils.yyyy_MM_dd
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/11/4.
 */
class GroupEducationListActivity: BaseActivity<ActivityGroupEducationListBinding>() {
    private var groupEducationAdapter:GroupEducationMulAdapter?= null
    private var data: MutableList<GroupEducationItem> = mutableListOf()
    private var isRefresh = false
    private var isLoadMore = false
    private val selection: String by lazy{
        intent?.getStringExtra(SELECTION)?:""
    }
    private var pageNo = 0
    private val viewModel by viewModels<GroupEducationListViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityGroupEducationListBinding? {
        return ActivityGroupEducationListBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("班组教育")
        groupEducationAdapter = GroupEducationMulAdapter(data)
        binding?.rv?.apply {
            layoutManager = LinearLayoutManager(this@GroupEducationListActivity)
            adapter = groupEducationAdapter
        }

        binding?.refreshLayout?.setRefreshHeader(ClassicsHeader(this))
        binding?.refreshLayout?.setRefreshFooter(ClassicsFooter(this))

    }

    override fun initData() {
        getData()
    }

    private fun getData(){
        LoadingManager.startLoading(this)
        viewModel.getData(selection, pageNo)
    }
    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewAdd?.setOnClickListener {
            onButtonClick()
        }
        groupEducationAdapter?.setOnItemClickListener(object: OnItemClickListener<GroupEducationItem>{
            override fun onItemClick(data: GroupEducationItem, position: Int) {
                if (data.type == CONTENT){
//                    ActivityUtils.start(this@GroupEducationListActivity,
//                        GroupEducationDetailActivity::class.java)
                    GroupEducationDetailActivity.startTo(this@GroupEducationListActivity, data.trainTopic)
                }
            }
        })
        binding?.refreshLayout?.setOnRefreshListener {
            isRefresh = true
            pageNo = 0
            getData()
        }

        binding?.refreshLayout?.setOnLoadMoreListener {
            isLoadMore = true
            pageNo++
            getData()
        }
    }

    private fun onButtonClick(){
        val isHide = MMKVResp.resp.getGroupEducationTipStatus()
        if (!isHide){
            XPopCreateUtils.commonShow(this, GroupEducationTipDialog(this))
        }else{
            AddGroupEducationActivity.startTo(this, selection)
        }
    }

    override fun observeViewModel() {
        viewModel.result.observe(this){
            if (data.size == 0 || isRefresh){
                data.clear()
                data.add(GroupEducationItem(type = HEADER))
            }

            it?.result?.apply {
                data.addAll(this)
            }
            groupEducationAdapter?.notifyDataSetChanged()
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

    override fun isRegisterEventbus(): Boolean {
        return true
    }

    override fun receiveEvent(code: Int, obj: Any?) {
        super.receiveEvent(code, obj)
        when (code) {
            EventCode.GROUP_EDUCATION_SEARCH -> {
                if (obj is String){
                    val trainTopic = obj
                    isRefresh = true
                    LoadingManager.startLoading(this)
                    pageNo = 0
                    viewModel.getData(selection, pageNo, trainTopic)
                }

            }
            EventCode.GROUP_EDUCATION_SCREENING -> {
                showScreeningDialog()
            }
            EventCode.GROUP_EDUCATION_STATUS -> {
                val data = obj as? GroupEducationOption
                data?.let {
                    isRefresh = true
                    LoadingManager.startLoading(this)
                    pageNo = 0
                    val beginTime = if (it.startDate == null){
                        ""
                    }else{
                        TimeUtils.parseTime(it.startDate, yyyy_MM_dd)
                    }
                    val endTime = if (it.endDate == null){
                        ""
                    }else{
                        TimeUtils.parseTime(it.endDate, yyyy_MM_dd)
                    }
                    val status = if (it.status == 0){
                        ""
                    }else if (it.status == 1){
                        "已施工"
                    }else{
                        "未施工"
                    }
                    viewModel.getData(selection, pageNo, "", beginTime, endTime, status)
                }
            }
        }
    }

    private fun showScreeningDialog() {
        binding ?: return
        binding?.viewTitlePlaceholder ?:return
        binding?.viewTitlePlaceholder?.let {
            val dialog = SelectGroupEducationOptionDialog(this@GroupEducationListActivity)
            XPopCreateUtils.commonShowAttach(this, it, PopupPosition.Bottom, dialog)
        }
    }

    companion object{
        const val SELECTION = "selection"
        fun startTo(ctx: Context, selection: String){
            ActivityUtils.start(ctx, GroupEducationListActivity::class.java){
                putExtra(SELECTION, selection)
            }
        }
    }
}