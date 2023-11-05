package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.databinding.ItemGroupEducationContentBinding
import com.shuangning.safeconstruction.databinding.ItemSearchBinding
import com.shuangning.safeconstruction.utils2.EventbusUtils

/**
 * Created by Chenwei on 2023/11/4.
 */
class GroupEducationMulAdapter(data: MutableList<ItemViewType>): CommonBaseMultiAdapter<ItemViewType, ViewBinding>(data) {
    private var input: String = ""
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: ItemViewType,
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
                binding.tvTitle.text = "桥梁下部结构班组-202112141646"
                binding.tvGroupName.text = "班组：桥梁下部结构组"
                binding.tvGroupPerson.text = "班组长："
                binding.tvCreatePerson.text = "创建人：张志月"
                binding.tvTime.text = "2021-12-12"
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