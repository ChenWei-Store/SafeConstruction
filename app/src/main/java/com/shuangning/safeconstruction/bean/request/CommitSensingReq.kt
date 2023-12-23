package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/23.
 */
@JsonClass(generateAdapter = true)
data class CommitSensingReq(
    @Json(name = "jianyanshijian") val jianyanshijian: String = "",
    @Json(name = "jianyanjieguo") val jianyanjieguo: String= "",
    @Json(name = "xiacijianyanshijian") val xiacijianyanshijian: String = "",
    @Json(name = "id") val id: Int = 0,
)