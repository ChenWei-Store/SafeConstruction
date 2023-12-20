package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_ONE
import com.shuangning.safeconstruction.base.adapter.LEVEL_TWO
import com.shuangning.safeconstruction.base.adapter.LevelType

/**
 * Created by Chenwei on 2023/10/18.
 */
data class SelectCheckLevelTwo(val title: String, val id: Int): ItemViewType(){
    override fun getItemType(): Int {
        return LEVEL_TWO
    }
}
