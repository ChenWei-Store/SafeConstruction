package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.bean.response.CameraListResp
import com.shuangning.safeconstruction.databinding.ItemCameraListBinding

class CameraListAdapter(data: MutableList<CameraListResp>):
    CommonBaseAdapter<CameraListResp, ItemCameraListBinding>(data) {
    override fun onBindViewHolder(
        binding: ItemCameraListBinding,
        item: CameraListResp,
        position: Int,
        ctx: Context
    ) {
        binding.tvName.text = item.deviceName
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemCameraListBinding {
        return ItemCameraListBinding.inflate(inflater, parent, false)
    }
}