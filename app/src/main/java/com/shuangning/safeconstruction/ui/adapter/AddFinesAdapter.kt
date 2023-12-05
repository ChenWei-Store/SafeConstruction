package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ItemFineBottomBinding
import com.shuangning.safeconstruction.databinding.ItemFineDetailBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/12/5.
 */
const val FINE_DETAIL = 1
const val FINE_BOTTOM = 2

class AddFinesAdapter(data: MutableList<ItemViewType>) :
    CommonBaseAdapter<ItemViewType, ViewBinding>(data) {
    private var isVetting = true
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
//                    StartActivityManager.startToAddFineItem(this@AddFinesActivity)
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
                binding.tvVettingYes.setOnClickListener {
                    val selectedText = binding.tvVettingYes
                    val unSelectText = binding.tvVettingNo
                    UIUtils.setTextLeftDrawable(selectedText, R.drawable.selected)
                    UIUtils.setTextLeftDrawable(unSelectText, R.drawable.not_select)
                    isVetting = true
                    binding.group.visibility = View.VISIBLE
                }

                binding.tvVettingNo.setOnClickListener {
                    val selectedText = binding.tvVettingNo
                    val unSelectText = binding.tvVettingYes
                    UIUtils.setTextLeftDrawable(selectedText, R.drawable.selected)
                    UIUtils.setTextLeftDrawable(unSelectText, R.drawable.not_select)
                    isVetting = false
                    binding.group.visibility = View.GONE
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
            FINE_DETAIL -> {
                ItemFineDetailBinding.inflate(inflater, parent, false)
            }

            else -> {
                ItemFineBottomBinding.inflate(inflater, parent, false)
            }
        }
    }
}