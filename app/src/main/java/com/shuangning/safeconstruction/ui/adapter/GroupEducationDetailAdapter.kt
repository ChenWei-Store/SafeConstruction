package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.response.GroupEducationDetailResp
import com.shuangning.safeconstruction.bean.response.Participant
import com.shuangning.safeconstruction.bean.response.UploadVideoItem
import com.shuangning.safeconstruction.databinding.ItemGroupEducationDetailContentBinding
import com.shuangning.safeconstruction.databinding.ItemGroupEducationDetailHeaderBinding
import com.shuangning.safeconstruction.utils2.ImageLoader
import com.shuangning.safeconstruction.utils2.JsonUtils
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/11/5.
 */
class GroupEducationDetailAdapter(data:  MutableList<IItemViewType>): CommonBaseMultiAdapter<IItemViewType, ViewBinding>(data) {
    override fun onBindViewHolder(
        binding: ViewBinding,
        item: IItemViewType,
        position: Int,
        ctx: Context
    ) {
        when(binding){
            is ItemGroupEducationDetailHeaderBinding->{
                if (item is GroupEducationDetailResp){
                    var str = ""
                   item.participant.user.forEach {
                        str += it.fullName
                        str += ","
                    }
                    binding.tvClassName.text = item.teamGroup
                    binding.tvNumber.text = item.trainTopic
                    binding.tvClassLeader.text = item.squadLeader
                    binding.tvPerson.text= str
                    binding.tvTime.text = item.educationTime
                    binding.tvStatus.text=item.buildStatus
                    binding.tvCreatePerson.text="${item.createBy} ${item.createTime}"
                    if (item.attachment.isNotEmpty()){
                        val url = parseVideoUrl(item.attachment)
                        binding.player.visibility = View.VISIBLE
                        binding.player.setUp(url, true, "")
                    }else{
                        binding.player.visibility = View.GONE
                    }
                }
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

    private fun parseVideoUrl(json: String): String{
        if (json.isEmpty()){
            return ""
        }
        val jsonObj = JSONObject(json)
        val jsonArray = jsonObj.optJSONArray("attach")
        val result = jsonArray.optJSONObject(0)
        return result.optString("url")
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