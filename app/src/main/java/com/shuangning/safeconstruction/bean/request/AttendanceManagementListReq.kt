package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/23.
 */
@JsonClass(generateAdapter = true)
data class AttendanceManagementListReq(
    @Json(name = "section") val section: String = "",
    @Json(name = "personType") val personType: Int = 1,
    @Json(name = "day") val day: String = "",
)
