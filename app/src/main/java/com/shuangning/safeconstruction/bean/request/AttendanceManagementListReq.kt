package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/23.
 */
@JsonClass(generateAdapter = true)
data class AttendanceManagementListReq(
    @Json(name = "section") val section: String = "",
    @Json(name = "personType") val personType: String = "",
    @Json(name = "day") val day: String = "",
    @Json(name = "pageNo") val pageNo: String = "0",
    @Json(name = "pageSize") val pageSize: String = "100",
)
