package com.shuangning.safeconstruction.bean.base

import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.ui.adapter.SHOW_PHOTO

/**
 * Created by Chenwei on 2023/10/28.
 */
data class ShowPhoto(
    val url: String,
    val isShowDelete: Boolean = false
): ItemViewType() {
    override fun getItemType(): Int {
        return SHOW_PHOTO
    }
}