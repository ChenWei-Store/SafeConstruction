package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.bean.other.ContentSelectTypeBean
import com.shuangning.safeconstruction.databinding.ItemSelctTypeContentBinding
import com.shuangning.safeconstruction.databinding.ItemSelectTypeHeaderBinding

/**
 * Created by Chenwei on 2023/10/14.
 */
class SelectTypeMultiAdapter(data: MutableList<IItemViewType>): CommonBaseMultiAdapter<IItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: IItemViewType,
        position: Int,
        ctx: Context
    ) {
       when(binding){
           is ItemSelectTypeHeaderBinding->{

           }
           is ItemSelctTypeContentBinding->{
               val data = item as? ContentSelectTypeBean
               binding.btn.text = data?.title
           }
       }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return if (viewType == HEADER){
            ItemSelectTypeHeaderBinding.inflate(inflater, parent, false)
        }else{
            ItemSelctTypeContentBinding.inflate(inflater, parent, false)

        }
    }
}