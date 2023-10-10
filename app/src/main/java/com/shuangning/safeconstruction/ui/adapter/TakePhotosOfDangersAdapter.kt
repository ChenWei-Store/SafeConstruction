package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.bean.other.TakePhotosOfDangersTitle
import com.shuangning.safeconstruction.databinding.ItemTakePhotosOfDangerBinding

/**
 * Created by Chenwei on 2023/10/10.
 */
class TakePhotosOfDangersAdapter(data: MutableList<TakePhotosOfDangersTitle>):
    CommonBaseAdapter<TakePhotosOfDangersTitle, ItemTakePhotosOfDangerBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: TakePhotosOfDangersTitle,
        position: Int,
        ctx: Context
    ) {
        val realBinding = (binding as? ItemTakePhotosOfDangerBinding)
        realBinding?.let {
            it.tvTitle.text = item.title
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemTakePhotosOfDangerBinding {
        return ItemTakePhotosOfDangerBinding.inflate(inflater, parent, false)
    }
}