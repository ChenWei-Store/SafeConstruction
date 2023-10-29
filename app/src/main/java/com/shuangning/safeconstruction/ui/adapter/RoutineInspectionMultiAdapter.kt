package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ItemRectificationAndReplyBinding
import com.shuangning.safeconstruction.databinding.ItemRoutineInspectionHeaderBinding
import com.shuangning.safeconstruction.manager.StartActivityManager

/**
 * Created by Chenwei on 2023/10/14.
 */
class RoutineInspectionMultiAdapter(data: MutableList<ItemViewType>): CommonBaseMultiAdapter<ItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: ItemViewType,
        position: Int,
        ctx: Context
    ) {
        when(binding){
            is ItemRoutineInspectionHeaderBinding ->{

            }
            is ItemRectificationAndReplyBinding->{
                binding.content.setOnClickListener {
                    StartActivityManager.startToQuestionOperator(ctx)
                }
            }
        }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return if(viewType == HEADER){
            ItemRoutineInspectionHeaderBinding.inflate(inflater, parent, false)
        }else{
            ItemRectificationAndReplyBinding.inflate(inflater, parent, false)
        }
    }
}