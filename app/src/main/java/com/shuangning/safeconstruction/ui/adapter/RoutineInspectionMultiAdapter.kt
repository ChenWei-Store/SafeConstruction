package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.response.RoutineInspectionItem
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
import com.shuangning.safeconstruction.utils2.MyLog
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/10/14.
 */
class RoutineInspectionMultiAdapter(data: MutableList<IItemViewType>): CommonBaseMultiAdapter<IItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: IItemViewType,
        position: Int,
        ctx: Context
    ) {
        MyLog.d("onBindViewHolder")
        when(binding){
            is ItemRoutineInspectionHeaderBinding ->{
//                binding.tvTime.text = "2022-12-28"
            }
            is ItemRectificationAndReplyBinding->{
                val data = item as? RoutineInspectionItem
                data?.let {
                    val urls = parseImageUrl(it.xunchatupian)
                    binding.tvTitle.text = "[${it.biaoduan}标] ${it.jianchaxiang}"
                    if (urls.size > 0){
                        ImageLoader.loadUrlWithRound(ctx, urls[0], binding.ivIcon, ScreenUtil.dp2px(16f))
                        binding.ivIcon.visibility = View.VISIBLE
                    }else{
                        binding.ivIcon.visibility = View.GONE
                    }

                    binding.tvContent1.text = it.jianchaxiang
                    binding.tvContent2.text = it.xianchangmiaoshu0
                    val selectedTab = it.status.toInt()
                    when (it.status) {
                        "0" -> {
                            binding.groupBottom.visibility = View.GONE
                            binding.tvStatus.background = UIUtils.getDrawable(R.drawable.common_16_c8ebff_bg)
                            binding.tvStatus.setTextColor(UIUtils.getColor(R.color.c_0A8DE5))
                            binding.tvStatus.text = "已完成"
                        }
                        "1" -> {
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
                    binding.content.setOnClickListener {
                        it2->
                        QuestionOperatorActivity.startTo(ctx, selectedTab, it.id.toString(), QuestionOperatorActivity.FROM_ROUTINE_INSPECTION)
                    }
                }


            }
        }
    }

    private fun parseImageUrl(json: String): MutableList<String> {
        if (json.isEmpty()){
            return mutableListOf()
        }
        val jsonObj = JSONObject(json)
        val jsonArray = jsonObj.optJSONArray("attach")
        val urls = mutableListOf<String>()
        for (idx in 0 until jsonArray.length()) {
            val result = jsonArray.optJSONObject(idx)
            val url = result.optString("url")
            urls.add(url)
        }
        return urls
    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        MyLog.d("getViewBinding")
        return if(viewType == HEADER){
            ItemRoutineInspectionHeaderBinding.inflate(inflater, parent, false)
        }else{
            ItemRectificationAndReplyBinding.inflate(inflater, parent, false)
        }
    }
}