package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ItemRectificationAndReplyBinding

/**
 * Created by Chenwei on 2023/10/11.
 */
class RectificationAndReplyAdapter(data: MutableList<ItemViewType>):
    CommonBaseAdapter<ItemViewType, ItemRectificationAndReplyBinding>(data) {
    override fun onBindViewHolder(
        binding: ItemRectificationAndReplyBinding,
        item: ItemViewType,
        position: Int,
        ctx: Context
    ) {
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemRectificationAndReplyBinding {
        return ItemRectificationAndReplyBinding.inflate(inflater, parent, false)
    }
}