package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.bean.base.IItemViewType
import com.shuangning.safeconstruction.constants.base.ItemType

/**
 * Created by Chenwei on 2023/10/8.
 */
data class HomeContentBean(
    val imageId: Int,
    val titleId: Int
): IItemViewType {
    override fun getType(): Int {
        return ItemType.CONTENT
    }
}

