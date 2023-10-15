package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.bean.other.HomeBean
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.databinding.ItemHomeBinding
import com.shuangning.safeconstruction.databinding.ItemHomeHeaderBinding
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.EventbusUtils

/**
 * Created by Chenwei on 2023/10/8.
 */
class HomeAdapter(data: MutableList<HomeBean>): CommonBaseAdapter<HomeBean, ViewBinding>(data) {
    override fun onBindViewHolder(binding: ViewBinding, item: HomeBean, position: Int, ctx: Context) {
        when(binding){
            is  ItemHomeBinding ->{
                val contentBean = item as? HomeBean
                contentBean?.let {
                    binding.tvText.text = UIUtils.getString(it.titleId)
                    binding.ivIcon.setImageResource(it.imageId)
                }

            }
            is ItemHomeHeaderBinding ->{
                binding.viewLeft.setOnClickListener {
                    EventbusUtils.post(EventCode.START_SCAN_QRCODE, null)
                }
                binding.viewRight.setOnClickListener {
                    EventbusUtils.post(EventCode.START_CLOCK_IN_OUT, null)
                }
            }
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): ViewBinding {
        return if(viewType == HEADER){
            ItemHomeHeaderBinding.inflate(inflater, parent, false)
        }else {
            ItemHomeBinding.inflate(inflater, parent, false)
        }
    }
}