package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_TWO
import com.shuangning.safeconstruction.base.adapter.LevelType

/**
 * Created by Chenwei on 2023/10/18.
 */
data class TermsOfReferenceLevelTwo(
    val title: String, val subData: MutableList<TermsOfReferenceLevelThree>)
    : LevelType<TermsOfReferenceLevelThree>(LEVEL_TWO, subData), IItemViewType{
    override fun getItemType(): Int {
        return LEVEL_TWO
    }
}
