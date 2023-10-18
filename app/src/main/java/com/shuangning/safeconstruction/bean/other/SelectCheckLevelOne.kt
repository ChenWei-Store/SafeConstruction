package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.LEVEL_ONE
import com.shuangning.safeconstruction.base.adapter.LevelType

/**
 * Created by Chenwei on 2023/10/18.
 */
data class SelectCheckLevelOne(
    val title: String, val level2: MutableList<SelectCheckLevelTwo>)
    : LevelType<SelectCheckLevelTwo>(LEVEL_ONE, level2)