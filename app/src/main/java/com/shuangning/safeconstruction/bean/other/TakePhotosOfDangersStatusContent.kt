package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.ItemViewType

/**
 * Created by Chenwei on 2023/10/10.
 */
data class TakePhotosOfDangersStatusContent(
    val time: String,
    val status: Int,
    val desc: String,
    val commitName: String
): ItemViewType()
