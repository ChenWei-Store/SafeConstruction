package com.shuangning.safeconstruction.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shuangning.safeconstruction.base.adapter.BaseViewHolder
import com.shuangning.safeconstruction.base.adapter.HeaderViewHolder
import com.shuangning.safeconstruction.bean.base.IItemViewType
import com.shuangning.safeconstruction.bean.other.HomeContentBean
import com.shuangning.safeconstruction.constants.base.ItemType
import com.shuangning.safeconstruction.databinding.ItemHomeBinding
import com.shuangning.safeconstruction.databinding.ItemHomeHeaderBinding
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/8.
 */
class HomeAdapter(val data: MutableList<IItemViewType>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ItemType.TITLE){
            val binding = ItemHomeHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderViewHolder(binding)
        }else {
            val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BaseViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (item.getType() == ItemType.CONTENT){
            val contentBean = item as? HomeContentBean
            val baseViewHolder = (holder as? BaseViewHolder<ItemHomeBinding>)
            contentBean?.let {
                baseViewHolder?.binding?.tvText?.text = UIUtils.getString(it.titleId)
                baseViewHolder?.binding?.ivIcon?.setImageResource(it.imageId)
            }
        }else{

        }

    }

    override fun getItemViewType(position: Int): Int {
        return data[position].getType()
    }
}