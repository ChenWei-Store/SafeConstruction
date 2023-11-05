package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ItemGroupEducationDetailContentBinding
import com.shuangning.safeconstruction.databinding.ItemGroupEducationDetailHeaderBinding
import com.shuangning.safeconstruction.utils2.ImageLoader

/**
 * Created by Chenwei on 2023/11/5.
 */
class GroupEducationDetailAdapter(data:  MutableList<ItemViewType>): CommonBaseMultiAdapter<ItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: ItemViewType,
        position: Int,
        ctx: Context
    ) {
        when(binding){
            is ItemGroupEducationDetailHeaderBinding->{
                binding.tvClassName.text = "桥梁下部结构班组"
                binding.tvNumber.text = "桥梁下部结构班组_202112141646"
                binding.tvClassLeader.text = "aaa"
                binding.tvPerson.text="小米小明小方冬冬"
                binding.tvTime.text = "班前"
                binding.tvStatus.text="施工"
                binding.tvContent.text="1111111111111111111"
                binding.tvLocation.text="aaa省bbb市ccc区"
                binding.tvCreatePerson.text="张志月 2021-14-16 17：33"
                binding.tvLikeNumber.text="共0个"
            }

            is ItemGroupEducationDetailContentBinding->{
                ImageLoader.loadUrlWithCircle(ctx,
                    "https://img2.baidu.com/it/u=4189974654,1527455668&fm=253&fmt=auto&app=138&f=JPEG?w=502&h=500",
                    binding.ivHeader)
                binding.tvName.text="百盛检查"
                binding.tvTime.text="2023-11-05 21：14"
                binding.tvContent.text = "测试数据"
            }
        }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return if (HEADER == viewType){
            ItemGroupEducationDetailHeaderBinding.inflate(inflater, parent, false)
        }else{
            ItemGroupEducationDetailContentBinding.inflate(inflater, parent, false)
        }
    }
}