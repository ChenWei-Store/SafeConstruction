package com.shuangning.safeconstruction.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shuangning.safeconstruction.base.adapter.CommonBaseAdapter
import com.shuangning.safeconstruction.base.adapter.CommonBaseMultiAdapter
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.response.FinesListItem
import com.shuangning.safeconstruction.databinding.ItemFineBinding
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils2.ImageLoader
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/10/12.
 */
class FinesMultiAdapter(data: MutableList<FinesListItem>):
    CommonBaseAdapter<FinesListItem, ItemFineBinding>(data)  {
    override fun onBindViewHolder(
        binding: ItemFineBinding,
        item: FinesListItem,
        position: Int,
        ctx: Context
    ) {
        binding.tvTitle.text = "[${item.beichufadanwei}] ${item.wentibianhao}"
        binding.tvContent1.text = item.jianchadanwei
        binding.tvContent2.text = getJianChaRen(item.jiancharen)
        binding.tvPrice.text = "共${item.leijijine}元"
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemFineBinding {
        return ItemFineBinding.inflate(inflater)
    }

    fun getJianChaRen(str: String): String{
        if (str.isEmpty()){
            return ""
        }
        try {
            val jsonObject = JSONObject(str)
            val ja = jsonObject.optJSONArray("user")
            val result = ja.optJSONObject(0)
            val data = result.optString("fullName")
            return data
        }catch (e: Exception){
            return ""
        }
    }
}