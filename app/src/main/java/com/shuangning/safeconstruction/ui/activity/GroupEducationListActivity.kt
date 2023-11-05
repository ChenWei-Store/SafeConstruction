package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.enums.PopupPosition
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.CONTENT
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.event.GroupEducationOption
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.data.mmkv.MMKVResp
import com.shuangning.safeconstruction.databinding.ActivityGroupEducationListBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.GroupEducationDetailAdapter
import com.shuangning.safeconstruction.ui.adapter.GroupEducationMulAdapter
import com.shuangning.safeconstruction.ui.dialog.GroupEducationTipDialog
import com.shuangning.safeconstruction.ui.dialog.SelectGroupEducationOptionDialog
import com.shuangning.safeconstruction.utils.APPUtils
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/11/4.
 */
class GroupEducationListActivity: BaseActivity<ActivityGroupEducationListBinding>() {
    private var groupEducationAdapter:GroupEducationMulAdapter?= null
    private var data: MutableList<ItemViewType> = mutableListOf()
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
    }

    override fun initData() {
        data.add(ItemViewType(HEADER))
        data.add(ItemViewType(CONTENT))
        data.add(ItemViewType(CONTENT))
        data.add(ItemViewType(CONTENT))
        data.add(ItemViewType(CONTENT))
        data.add(ItemViewType(CONTENT))
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewAdd?.setOnClickListener {
            onButtonClick()
        }

        groupEducationAdapter?.setOnItemClickListener(object: OnItemClickListener<ItemViewType>{
            override fun onItemClick(data: ItemViewType, position: Int) {
                if (data.type == CONTENT){
                    ActivityUtils.start(this@GroupEducationListActivity,
                        GroupEducationDetailActivity::class.java)
                }
            }

        })
    }

    private fun onButtonClick(){
        val isHide = MMKVResp.resp.getGroupEducationTipStatus()
        if (!isHide){
            XPopCreateUtils.commonShow(this, GroupEducationTipDialog(this))
        }else{
            StartActivityManager.startAddGroupEducation(this)
        }
    }

    override fun observeViewModel() {
    }

    override fun isRegisterEventbus(): Boolean {
        return true
    }

    override fun receiveEvent(code: Int, obj: Any?) {
        super.receiveEvent(code, obj)
        when (code) {
            EventCode.GROUP_EDUCATION_SEARCH -> {

            }
            EventCode.GROUP_EDUCATION_SCREENING -> {
                showScreeningDialog()
            }
            EventCode.GROUP_EDUCATION_STATUS -> {
                val data = obj as? GroupEducationOption
                data?.let {

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
}