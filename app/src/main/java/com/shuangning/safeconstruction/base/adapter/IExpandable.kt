package com.shuangning.safeconstruction.base.adapter

/**
 * Created by Chenwei on 2023/10/19.
 */
interface IExpandable<T> {
    fun isExpand(): Boolean = false
    fun setExpandStatus(expand: Boolean)
    fun getSubItems():MutableList<T>

    fun getLevel(): Int
}