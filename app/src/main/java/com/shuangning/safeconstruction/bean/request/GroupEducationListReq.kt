package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/23.
 */
@JsonClass(generateAdapter = true)
data class GroupEducationListReq(
    @Json(name = "section") val section: String = "",
    @Json(name = "pageNo") val pageNo: Int = 0,
    @Json(name = "trainTopic") val trainTopic: String = "",
    @Json(name = "beginTime") val beginTime: String = "",
    @Json(name = "endTime") val endTime: String = "",
    @Json(name = "buildStatus") val buildStatus: String = "",
    @Json(name = "pageSize") val pageSize: Int = 30,
)