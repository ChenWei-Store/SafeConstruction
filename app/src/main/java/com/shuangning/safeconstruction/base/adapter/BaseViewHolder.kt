package com.shuangning.safeconstruction.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by Chenwei on 2023/9/13.
 */
class BaseViewHolder<V: ViewBinding>(val binding: V): RecyclerView.ViewHolder(binding.root){

}