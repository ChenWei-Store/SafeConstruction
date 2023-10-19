package com.shuangning.safeconstruction.base.adapter

import kotlin.reflect.typeOf

/**
 * Created by Chenwei on 2023/10/8.
 */
const val HEADER = 0
const val CONTENT = 1
const val BOTTOM = 2
interface IItemViewType{
    fun getItemType() = CONTENT
}

open class ItemViewType(val type: Int = CONTENT): IItemViewType{
    override fun getItemType(): Int {
        return type
    }
}

