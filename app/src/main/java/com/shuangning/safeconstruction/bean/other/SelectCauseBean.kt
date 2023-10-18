package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.ItemViewType

/**
 * Created by Chenwei on 2023/10/18.
 */
data class SelectCauseBean(
    val reason: String,
    var isSelect: Boolean = false
): ItemViewType()