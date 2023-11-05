package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CONTENT
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.other.MultiSelectBean
import com.shuangning.safeconstruction.databinding.ItemSearchBinding
import com.shuangning.safeconstruction.databinding.ItemSelectReasonBinding
import com.shuangning.safeconstruction.extension.setHintTextSizecColor

/**
 * Created by Chenwei on 2023/10/18.
 */
class MultiSelectMultiAdapter(data: MutableList<ItemViewType>): CommonBaseMultiAdapter<ItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: ItemViewType,
        position: Int,
        ctx: Context
    ) {
        when(binding){
            is ItemSelectReasonBinding->{
                val bean = item as? MultiSelectBean
                bean?.let {
                    binding.checkbox.text = it.reason
                    binding.checkbox.setOnCheckedChangeListener {
                            buttonView, isChecked ->
                        it.isSelect = isChecked
                    }
                }
            }

            is ItemSearchBinding->{
                binding.et.setHintTextSizecColor( "请输入人员姓名", 14)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        if (viewType == CONTENT) {
            return ItemSelectReasonBinding.inflate(inflater, parent, false)
        }else{
            return ItemSearchBinding.inflate(inflater, parent, false)
        }
    }
}