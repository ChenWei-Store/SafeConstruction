package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/25.
 */
@JsonClass(generateAdapter = true)
data class JoinParticipantReq(
    @Json(name = "section") val section: String = "",
    @Json(name = "teamGroup") val teamGroup: Int = 0,
)