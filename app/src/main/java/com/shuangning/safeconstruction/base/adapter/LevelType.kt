package com.shuangning.safeconstruction.base.adapter

/**
 * Created by Chenwei on 2023/10/18.
 */
const val LEVEL_ONE = 1
const val LEVEL_TWO = 2
const val LEVEL_THREE = 3
open class LevelType<E: Any>(
    override val type: Int,
    subData:MutableList<E>? = null,
    ) : ItemViewType(type)