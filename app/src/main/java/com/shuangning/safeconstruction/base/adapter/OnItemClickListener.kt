package com.shuangning.safeconstruction.base.adapter

/**
 * Created by Chenwei on 2023/10/10.
 */
interface OnItemClickListener<T> {
    fun onItemClick(data: T, position: Int)
}