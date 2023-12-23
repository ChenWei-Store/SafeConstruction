package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.bean.response.DeviceSensingItem
import com.shuangning.safeconstruction.databinding.ItemSensingBinding
import com.shuangning.safeconstruction.utils2.ImageLoader

/**
 * Created by Chenwei on 2023/12/23.
 */
class SensingAdapter(data: MutableList<DeviceSensingItem>): CommonBaseAdapter<DeviceSensingItem, ItemSensingBinding>(data) {
    override fun onBindViewHolder(
        binding: ItemSensingBinding,
        item: DeviceSensingItem,
        position: Int,
        ctx: Context
    ) {
        ImageLoader.loadUrlWithCircle(ctx, item.shebeizhaopian, binding.iv)
        binding.tvName.text = item.shebeimingcheng
        binding.tvTime.text = item.jianyanshijian
        binding.tvResult.text = item.jianyanjieguo
        binding.tvNextTime.text = "下次检测:${item.xiacijianyanshijian}"

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemSensingBinding {
       return ItemSensingBinding.inflate(inflater, parent, false)
    }
}