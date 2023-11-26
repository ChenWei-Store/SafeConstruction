package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.databinding.ItemTakePhotosOfDangerBinding

/**
 * Created by Chenwei on 2023/10/10.
 */
class TakePhotosOfDangersAdapter(data: MutableList<String>):
    CommonBaseAdapter<String, ItemTakePhotosOfDangerBinding>(data) {
    override fun onBindViewHolder(
        binding: ItemTakePhotosOfDangerBinding,
        item: String,
        position: Int,
        ctx: Context
    ) {
        binding.tvTitle.text = item
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemTakePhotosOfDangerBinding {
        return ItemTakePhotosOfDangerBinding.inflate(inflater, parent, false)
    }
}