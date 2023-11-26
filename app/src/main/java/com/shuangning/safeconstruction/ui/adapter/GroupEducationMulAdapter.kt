package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.bean.response.GroupEducationItem
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.databinding.ItemGroupEducationContentBinding
import com.shuangning.safeconstruction.databinding.ItemSearchBinding
import com.shuangning.safeconstruction.utils2.EventbusUtils

/**
 * Created by Chenwei on 2023/11/4.
 */
class GroupEducationMulAdapter(data: MutableList<GroupEducationItem>): CommonBaseMultiAdapter<GroupEducationItem, ViewBinding>(data) {
    private var input: String = ""
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: GroupEducationItem,
        position: Int,
        ctx: Context
    ) {
        when(binding){
            is ItemSearchBinding->{
                binding.tv.setOnClickListener {
                    EventbusUtils.post(EventCode.GROUP_EDUCATION_SCREENING, null)

                }
                binding.et.doAfterTextChanged {
                    input = it.toString()
                }
                binding.et.setOnEditorActionListener {
                        v, actionId, event ->
                    EventbusUtils.post(EventCode.GROUP_EDUCATION_SEARCH, input)
                    true
                }
            }

            is ItemGroupEducationContentBinding->{
                binding.tvTitle.text = item.trainTopic
                binding.tvGroupName.text = "班组：${item.teamGroup}"
                binding.tvGroupPerson.text = "班组长：${item.squadLeader}"
                binding.tvCreatePerson.text = "创建人：${item.createBy}"
                binding.tvTime.text = item.createTime
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
       return if (viewType == HEADER){
            ItemSearchBinding.inflate(inflater, parent, false)
        }else{
            ItemGroupEducationContentBinding.inflate(inflater, parent, false)
        }
    }
}