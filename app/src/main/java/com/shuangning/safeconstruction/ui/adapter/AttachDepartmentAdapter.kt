package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.bean.other.DepartmentBean
import com.shuangning.safeconstruction.databinding.ItemDialogDepartmentBinding

/**
 * Created by Chenwei on 2023/10/29.
 */
class AttachDepartmentAdapter(data: MutableList<DepartmentBean>): CommonBaseAdapter<DepartmentBean, ItemDialogDepartmentBinding>(data) {
    override fun onBindViewHolder(
        binding: ItemDialogDepartmentBinding,
        item: DepartmentBean,
        position: Int,
        ctx: Context
    ) {
        binding.tvContent.text = item.name
        binding.iv.visibility = if (item.isSelect){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemDialogDepartmentBinding {
        return ItemDialogDepartmentBinding.inflate(inflater, parent, false)
    }
}