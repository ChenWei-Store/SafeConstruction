package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.bean.other.TakePhotosOfDangersStatusContent
import com.shuangning.safeconstruction.databinding.ItemTakePhotosOfDangersStatusBinding
import com.shuangning.safeconstruction.ui.activity.PRECESSED
import com.shuangning.safeconstruction.ui.activity.TO_BE_PROCESSED
import com.shuangning.safeconstruction.ui.activity.TO_BE_RECEIVED

/**
 * Created by Chenwei on 2023/10/10.
 */
class TakePhotosOfDangersStatusMultiAdapter(data: MutableList<TakePhotosOfDangersStatusContent>):
    CommonBaseMultiAdapter<TakePhotosOfDangersStatusContent, ItemTakePhotosOfDangersStatusBinding>(data) {
    override fun onBindViewHolder(
        binding: ItemTakePhotosOfDangersStatusBinding,
        item: TakePhotosOfDangersStatusContent,
        position: Int,
        ctx: Context
    ) {
        binding.tvTime.text = item.time
        binding.tvStatus.text = when(item.status){
            TO_BE_RECEIVED-> "待接收"
            TO_BE_PROCESSED ->"待处理"
            PRECESSED ->"已处理"
            else -> {
                ""
            }
        }
        binding.tvDesc.text = ctx.getString(R.string.problem_description, item.desc)
        binding.tvCommitName.text = ctx.getString(R.string.commit_name, item.commitName)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemTakePhotosOfDangersStatusBinding {
        return ItemTakePhotosOfDangersStatusBinding.inflate(inflater, parent, false)
    }
}