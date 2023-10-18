package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.ItemViewType

/**
 * Created by Chenwei on 2023/10/17.
 */
data class Photo(val path: String = "", override val type: Int): ItemViewType(type)
