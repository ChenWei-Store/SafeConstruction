package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.other.FineBottom
import com.shuangning.safeconstruction.bean.other.FineItem
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
    private var section: Array<String>? = null

    fun setSelection(section: Array<String>?){
        this.section = section
    }
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: ItemViewType,
        position: Int,
        ctx: Context
    ) {
        when (binding) {
            is ItemFineDetailBinding -> {
                val data = item as FineItem
                binding.tvType.text = data.dealType
                binding.tvPrice.text = data.money.toString() + "元"
                binding.tvDesc.text = data.desc
            }

            is ItemFineBottomBinding -> {
                val data = item as FineBottom
                binding.tvVettingUnit.text = data.jianchadanwei
                binding.tvFinesUnit.text = data.beifakuandanwei
                binding.tvPrice.text = data.totalMoney.toString() + "元"
                binding.viewAddFineItem.setOnClickListener {
                    AddFineItemActivity.startForResult(binding.root.context, 1)
                }

                binding.viewFinesUnit.setOnClickListener {
                    section?.let {
                        XPopCreateUtils.showListCenterDialog(
                            ctx,
                           it
                        ) { position, text ->
                            val text = it[position]
                            val item = getItem(itemCount - 1)
                            if (item is FineBottom){
                                item.beifakuandanwei = text
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
                binding.viewVettingUnit.setOnClickListener {
                    section?.let {
                        XPopCreateUtils.showListCenterDialog(
                            ctx,
                            it
                        ) { position, text ->
                            val text = it[position]
                            val item = getItem(itemCount - 1)
                            if (item is FineBottom){
                                item.jianchadanwei = text
                                notifyDataSetChanged()
                            }
                        }
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

            FINE_DETAIL -> {
                ItemFineDetailBinding.inflate(inflater, parent, false)
            }

            FINE_BOTTOM -> {
                ItemFineBottomBinding.inflate(inflater, parent, false)
            }

            else -> {
                throw Exception()
            }
        }
    }
}