package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.bean.other.SelectTypeBean
import com.shuangning.safeconstruction.databinding.ItemSelctTypeContentBinding
import com.shuangning.safeconstruction.databinding.ItemSelectTypeHeaderBinding

/**
 * Created by Chenwei on 2023/10/14.
 */
class SelectTypeAdapter(data: MutableList<SelectTypeBean>): CommonBaseAdapter<SelectTypeBean, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: SelectTypeBean,
        position: Int,
        ctx: Context
    ) {
       when(binding){
           is ItemSelectTypeHeaderBinding->{

           }
           is ItemSelctTypeContentBinding->{
               binding.btn.text = item.title
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