package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.response.FinesListItem
import com.shuangning.safeconstruction.databinding.ItemFineBinding
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils2.ImageLoader

/**
 * Created by Chenwei on 2023/10/12.
 */
class FinesMultiAdapter(data: MutableList<FinesListItem>):
    CommonBaseAdapter<FinesListItem, ItemFineBinding>(data)  {
    override fun onBindViewHolder(
        binding: ItemFineBinding,
        item: FinesListItem,
        position: Int,
        ctx: Context
    ) {
        ImageLoader.loadUrlWithRound(ctx, "", binding.ivIcon, ScreenUtil.dp2px(16f))
        binding.tvTitle.text = "[${item.biaoduan}] ${item.wentibianhao}"
        binding.tvContent1.text = "工地形象"
        binding.tvContent2.text = "测试测试测试测试测试"
        binding.tvPrice.text = "共100.0元"
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemFineBinding {
        return ItemFineBinding.inflate(inflater)
    }
}