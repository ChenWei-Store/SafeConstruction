package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_ONE
import com.shuangning.safeconstruction.base.adapter.LevelType

/**
 * Created by Chenwei on 2023/10/18.
 */
data class TermsOfReferenceLevelOne(
    val title: String, val subData: MutableList<TermsOfReferenceLevelTwo>)
    : LevelType<TermsOfReferenceLevelTwo>(LEVEL_ONE, subData), IItemViewType{
    override fun getItemType(): Int {
        return LEVEL_ONE
    }
}
