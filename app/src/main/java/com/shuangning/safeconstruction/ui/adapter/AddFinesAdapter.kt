package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ItemAddFineBinding
import com.shuangning.safeconstruction.databinding.ItemFineBottomBinding
import com.shuangning.safeconstruction.databinding.ItemFineDetailBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.activity.AddFineItemActivity

/**
 * Created by Chenwei on 2023/12/5.
 */
const val ADD_FINE = 3
const val FINE_DETAIL = 1
const val FINE_BOTTOM = 2

class AddFinesAdapter(data: MutableList<ItemViewType>) :
    CommonBaseMultiAdapter<ItemViewType, ViewBinding>(data) {
    private var fineUnit = ""
    private var vettingPerson = ""
    private var vettingUnit = ""
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: ItemViewType,
        position: Int,
        ctx: Context
    ) {
        when (binding) {
            is ItemFineDetailBinding -> {

            }

            is ItemFineBottomBinding -> {
                vettingPerson = "GX-2标"
                vettingUnit = "指挥部"
                fineUnit = "GX-2标"
                binding.tvVettingPerson.text = vettingPerson
                binding.tvVettingUnit.text = vettingUnit
                binding.tvFinesUnit.text = fineUnit

                binding.viewAddFineItem.setOnClickListener {
                    AddFineItemActivity.startForResult(binding.root.context, 1)
                }

                binding.viewFinesUnit.setOnClickListener {
                    XPopCreateUtils.showListCenterDialog(
                        ctx,
                        arrayOf("GX-2标", "GX-1标")
                    ) { position, text ->
                        fineUnit = text
                        binding.tvFinesUnit.text = text
                    }
                }
                binding.viewVettingPerson.setOnClickListener {
                    XPopCreateUtils.showListCenterDialog(
                        ctx,
                        arrayOf("指挥部", "xxx部")
                    ) { position, text ->
                        vettingPerson = text
                        binding.tvVettingPerson.text = text
                    }
                }
            }

        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return when (viewType) {
            ADD_FINE -> {
                ItemAddFineBinding.inflate(inflater, parent, false)
            }
            FINE_DETAIL->{
                ItemFineDetailBinding.inflate(inflater, parent, false)
            }
            FINE_BOTTOM->{
                ItemFineBottomBinding.inflate(inflater, parent, false)
            }
            else -> {
                throw Exception()
            }
        }
    }
}