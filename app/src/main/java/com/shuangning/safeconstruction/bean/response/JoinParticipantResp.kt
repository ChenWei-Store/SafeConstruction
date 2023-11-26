package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json

/**
 * Created by Chenwei on 2023/11/25.
 */
data class JoinParticipantResp(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "state") val state: String = "",
    @Json(name = "avatar") val avatar: String = "",
    @Json(name = "fullName") val fullName: String = "",
    @Json(name = "username") val username: String = "",
    @Json(name = "leaderIds") val leaderIds: String = "",
    @Json(name = "createTime") val createTime: String = "",
    @Json(name = "description") val description: String = "",
)
