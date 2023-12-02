package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/26.
 */
@JsonClass(generateAdapter = true)
data class GetProjectBaseInfoResp(
    @Json(name = "projectName") val projectName: String = "",
    @Json(name = "beginTime") val beginTime: String = "",
    @Json(name = "endTime") val endTime: String = "",
    @Json(name = "longitude") val longitude: Double = 0.0,
    @Json(name = "latitude") val latitude: Double = 0.0,
)
