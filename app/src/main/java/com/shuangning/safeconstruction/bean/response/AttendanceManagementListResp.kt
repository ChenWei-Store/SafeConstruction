package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/6.
 */
@JsonClass(generateAdapter=true)
data class AttendanceManagementListResp(
    @Json(name = "total") val total: String = "",
    @Json(name = "result") val result: MutableList<AttendanceManagementItem> = mutableListOf(),
)

@JsonClass(generateAdapter=true)
data class AttendanceManagementItem(
    @Json(name = "userName") val userName: String = "",
    @Json(name = "job") val job: String = "",
    @Json(name = "days") val days: Int = 0,
    @Json(name = "dayTime") val dayTime:  MutableList<String> = mutableListOf(),
)