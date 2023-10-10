package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.bean.other.TakePhotosOfDangersStatusContent
import com.shuangning.safeconstruction.databinding.ItemTakePhotosOfDangersStatusBinding

/**
 * Created by Chenwei on 2023/10/10.
 */
class TakePhotosOfDangersStatusAdapter(data: MutableList<TakePhotosOfDangersStatusContent>):
    CommonBaseAdapter<TakePhotosOfDangersStatusContent, ItemTakePhotosOfDangersStatusBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: TakePhotosOfDangersStatusContent,
        position: Int,
        ctx: Context
    ) {
        val realBinding = binding as? ItemTakePhotosOfDangersStatusBinding
        realBinding?.tvTime?.text = item.time
        realBinding?.tvStatus?.text = when(item.status){
            1-> "待接收"
            2->"待处理"
            3->"已处理"
            else -> {
                ""
            }
        }
        realBinding?.tvDesc?.text = ctx.getString(R.string.problem_description, item.desc)
        realBinding?.tvCommitName?.text = ctx.getString(R.string.commit_name, item.commitName)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemTakePhotosOfDangersStatusBinding {
        return ItemTakePhotosOfDangersStatusBinding.inflate(inflater, parent, false)
    }
}