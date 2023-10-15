package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.ItemViewType


/**
 * Created by Chenwei on 2023/10/14.
 */
data class SelectTypeBean(
    override val type: Int,
    val title: String = ""): ItemViewType(type)

