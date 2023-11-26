package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/26.
 */
@JsonClass(generateAdapter=true)
data class GetTeamInfoDetailResp(
    @Json(name = "trainTopic") val trainTopic: String = "",
    @Json(name = "squadLeader") val squadLeader: String = "",
    @Json(name = "participant") val participant: Participant = Participant(),
    )
