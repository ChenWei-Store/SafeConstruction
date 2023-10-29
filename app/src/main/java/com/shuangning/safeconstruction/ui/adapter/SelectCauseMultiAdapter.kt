package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.bean.other.SelectCauseBean
import com.shuangning.safeconstruction.databinding.ItemSelectReasonBinding

/**
 * Created by Chenwei on 2023/10/18.
 */
class SelectCauseMultiAdapter(data: MutableList<SelectCauseBean>): CommonBaseMultiAdapter<SelectCauseBean, ItemSelectReasonBinding>(data) {
    override fun onBindViewHolder(
        binding: ItemSelectReasonBinding,
        item: SelectCauseBean,
        position: Int,
        ctx: Context
    ) {
        binding.checkbox.text = item.reason
        binding.checkbox.setOnCheckedChangeListener {
                buttonView, isChecked ->
                item.isSelect = isChecked
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemSelectReasonBinding {
        return ItemSelectReasonBinding.inflate(inflater)
    }
}