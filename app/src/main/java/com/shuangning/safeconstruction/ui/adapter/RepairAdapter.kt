package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.bean.response.DeviceRepairItem
import com.shuangning.safeconstruction.databinding.ItemRepairBinding
import com.shuangning.safeconstruction.databinding.ItemSensingBinding
import com.shuangning.safeconstruction.utils2.ImageLoader

/**
 * Created by Chenwei on 2023/12/23.
 */
class RepairAdapter(data: MutableList<DeviceRepairItem>): CommonBaseAdapter<DeviceRepairItem, ItemRepairBinding>(data) {
    override fun onBindViewHolder(
        binding: ItemRepairBinding,
        item: DeviceRepairItem,
        position: Int,
        ctx: Context
    ) {
        ImageLoader.loadUrlWithCircle(ctx, item.shebeizhaopian, binding.iv)
        binding.tvName.text = item.shebeimingcheng
        binding.tvTime.text = item.weibaoshijian
        binding.tvDesc.text = item.weibaoneirong
        binding.tvNextTime.text = "下次维保:${item.xiaciweibaoshijian}"
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemRepairBinding {
       return ItemRepairBinding.inflate(inflater, parent, false)
    }
}