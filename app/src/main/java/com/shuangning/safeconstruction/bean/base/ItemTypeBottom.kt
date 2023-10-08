package com.shuangning.safeconstruction.bean.base

import com.shuangning.safeconstruction.constants.base.ItemType

/**
 * Created by Chenwei on 2023/10/8.
 */
class ItemTypeBottom: IItemViewType {
    override fun getType(): Int {
        return ItemType.BOTTOM
    }
}