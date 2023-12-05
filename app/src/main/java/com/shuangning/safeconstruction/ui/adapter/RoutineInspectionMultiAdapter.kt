package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ItemRectificationAndReplyBinding
import com.shuangning.safeconstruction.databinding.ItemRoutineInspectionHeaderBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.activity.COMPLETED
import com.shuangning.safeconstruction.ui.activity.QuestionOperatorActivity
import com.shuangning.safeconstruction.ui.activity.TO_BE_EXAMINE
import com.shuangning.safeconstruction.ui.activity.TO_BE_RECTIFIED
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ImageLoader

/**
 * Created by Chenwei on 2023/10/14.
 */
class RoutineInspectionMultiAdapter(data: MutableList<ItemViewType>): CommonBaseMultiAdapter<ItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: ItemViewType,
        position: Int,
        ctx: Context
    ) {
        when(binding){
            is ItemRoutineInspectionHeaderBinding ->{
                binding.tvTime.text = "2022-12-28"
            }
            is ItemRectificationAndReplyBinding->{
                val selectedTab = TO_BE_RECTIFIED
                when (selectedTab) {
                    COMPLETED -> {
                        binding.groupBottom.visibility = View.GONE
                        binding.tvStatus.background = UIUtils.getDrawable(R.drawable.common_16_c8ebff_bg)
                        binding.tvStatus.setTextColor(UIUtils.getColor(R.color.c_0A8DE5))
                        binding.tvStatus.text = "已完成"
                    }
                    TO_BE_EXAMINE -> {
                        binding.groupBottom.visibility = View.GONE
                        binding.tvStatus.background = UIUtils.getDrawable(R.drawable.common_16_67e667_bg)
                        binding.tvStatus.setTextColor(UIUtils.getColor(R.color.c_008500))
                        binding.tvStatus.text = "待审核"
                    }
                    else -> {
                        binding.groupBottom.visibility = View.VISIBLE
                        binding.tvStatus.background = UIUtils.getDrawable(R.drawable.common_16_ffe7e3_bg)
                        binding.tvStatus.setTextColor(UIUtils.getColor(R.color.c_ff3e3d))
                        binding.tvStatus.text = "待整改"
                    }
                }

                binding.tvTitle.text = "[gx-2标] gx-2_221228001"
                ImageLoader.loadUrlWithRound(ctx, "", binding.ivIcon, ScreenUtil.dp2px(16f))
                binding.tvContent1.text = "工地形象"
                binding.tvContent2.text = "测试测试测试测试测试"
                binding.content.setOnClickListener {
                    QuestionOperatorActivity.startTo(ctx, selectedTab)
                }
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return if(viewType == HEADER){
            ItemRoutineInspectionHeaderBinding.inflate(inflater, parent, false)
        }else{
            ItemRectificationAndReplyBinding.inflate(inflater, parent, false)
        }
    }
}