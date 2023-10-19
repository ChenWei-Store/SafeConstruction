package com.shuangning.safeconstruction.base.adapter

/**
 * Created by Chenwei on 2023/10/18.
 */
const val LEVEL_ONE = 1
const val LEVEL_TWO = 2
const val LEVEL_THREE = 3
abstract class LevelType<E>(
    private val itemLevel: Int,
    private val subData:MutableList<E>,
    private var isExpand: Boolean = false
) : IExpandable<E>{
    override fun isExpand(): Boolean {
        return isExpand
    }

    override fun getSubItems(): MutableList<E> {
        return subData
    }

    override fun setExpandStatus(expand: Boolean) {
        this.isExpand = expand
    }

    override fun getLevel(): Int {
        return itemLevel
    }
}