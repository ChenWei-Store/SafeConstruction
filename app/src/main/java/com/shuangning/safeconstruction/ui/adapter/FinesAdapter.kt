package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ItemFineBinding

/**
 * Created by Chenwei on 2023/10/12.
 */
class FinesAdapter(data: MutableList<ItemViewType>):
    CommonBaseAdapter<ItemViewType, ItemFineBinding>(data)  {
    override fun onBindViewHolder(
        binding: ItemFineBinding,
        item: ItemViewType,
        position: Int,
        ctx: Context
    ) {
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemFineBinding {
        return ItemFineBinding.inflate(inflater)
    }
}