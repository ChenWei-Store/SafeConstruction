package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_ONE
import com.shuangning.safeconstruction.base.adapter.LevelType

/**
 * Created by Chenwei on 2023/10/18.
 */
data class SelectCheckLevelOne(
    val title: String = "",
    val id: Int = 0,
    val subData: MutableList<SelectCheckLevelTwo> = mutableListOf()
) : LevelType<SelectCheckLevelTwo>(LEVEL_ONE, subData), IItemViewType {
    override fun getItemType(): Int {
        return LEVEL_ONE
    }
}
