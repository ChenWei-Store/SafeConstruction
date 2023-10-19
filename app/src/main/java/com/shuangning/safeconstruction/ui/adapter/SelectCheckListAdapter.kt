package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_ONE
import com.shuangning.safeconstruction.base.adapter.LevelType
import com.shuangning.safeconstruction.bean.other.SelectCheckHeader
import com.shuangning.safeconstruction.bean.other.SelectCheckLevelOne
import com.shuangning.safeconstruction.bean.other.SelectCheckLevelTwo
import com.shuangning.safeconstruction.databinding.ItemSelectCheckListHeaderBinding
import com.shuangning.safeconstruction.databinding.ItemSelectCheckListLevelOneBinding
import com.shuangning.safeconstruction.databinding.ItemSelectCheckListLevelTwoBinding

/**
 * Created by Chenwei on 2023/10/18.
 */

class SelectCheckListAdapter(data: MutableList<IItemViewType>):
    CommonBaseAdapter<IItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: IItemViewType,
        position: Int,
        ctx: Context
    ) {
        when(binding){
            is ItemSelectCheckListHeaderBinding->{
                binding.tvTitle.text = (item as? SelectCheckHeader)?.title?:""
            }

            is ItemSelectCheckListLevelOneBinding->{
                binding.tv.text = (item as? SelectCheckLevelOne)?.title?:""
                val resId = if((item as? SelectCheckLevelOne)?.isExpand() == true){
                    R.drawable.bottom_triangles
                }else{
                    R.drawable.right_triangles
                }
                binding.iv.setImageResource(resId)
            }

            is ItemSelectCheckListLevelTwoBinding->{
                binding.tv.text = (item as? SelectCheckLevelTwo)?.title?:""
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return when (viewType) {
            HEADER -> {
                ItemSelectCheckListHeaderBinding.inflate(inflater, parent, false)
            }
            LEVEL_ONE -> {
                ItemSelectCheckListLevelOneBinding.inflate(inflater, parent, false)
            }
            else -> {
                ItemSelectCheckListLevelTwoBinding.inflate(inflater, parent, false)
            }
        }
    }


}