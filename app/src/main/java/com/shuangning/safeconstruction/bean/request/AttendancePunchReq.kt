package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/23.
 */
@JsonClass(generateAdapter = true)
data class AttendancePunchReq(
    @Json(name = "userNo") val userNo: String = "",
    @Json(name = "longitude") val longitude: String = "",
    @Json(name = "latitude") val latitude: String = "",
)