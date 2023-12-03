package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.other.HomeContentBean
import com.shuangning.safeconstruction.bean.other.HomeHeaderBean
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.databinding.ItemHomeBinding
import com.shuangning.safeconstruction.databinding.ItemHomeHeaderBinding
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.EventbusUtils
import com.shuangning.safeconstruction.utils2.ImageLoader
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

/**
 * Created by Chenwei on 2023/10/8.
 */
class HomeMultiAdapter(data: MutableList<ItemViewType>): CommonBaseMultiAdapter<ItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(binding: ViewBinding, item: ItemViewType, position: Int, ctx: Context) {
        when(binding){
            is  ItemHomeBinding ->{
                val contentBean = item as? HomeContentBean
                contentBean?.let {
                    binding.tvText.text = UIUtils.getString(it.titleId)
                    binding.ivIcon.setImageResource(it.imageId)
                }

            }
            is ItemHomeHeaderBinding ->{
                val headerBean = item as? HomeHeaderBean
                headerBean?.let {
                    binding.tvProject.text = it.projectName
                    if (it.bannerUrls.size > 0){
                        binding.banner.visibility = View.VISIBLE
                        binding.banner.setAdapter(object: BannerImageAdapter<String>(it.bannerUrls) {
                            override fun onBindView(holder: BannerImageHolder?, data: String?,
                                                    position: Int, size: Int) {
                                data ?: return
                                holder ?: return
                                ImageLoader.loadUrl(ctx, data, holder.imageView)
                            }
                        }).setIndicator(CircleIndicator(ctx))
                    }else{
                        binding.banner.visibility = View.GONE
                    }
                }
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