package com.shuangning.safeconstruction.bean.event

import java.util.Date

/**
 * Created by Chenwei on 2023/11/5.
 */
data class GroupEducationOption(
    val startDate: Date,
    val endDate: Date,
    val status: Int,
)