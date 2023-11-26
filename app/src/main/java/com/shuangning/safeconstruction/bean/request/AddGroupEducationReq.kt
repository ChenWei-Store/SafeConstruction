package com.shuangning.safeconstruction.bean.request

import com.shuangning.safeconstruction.bean.response.Participant
import com.shuangning.safeconstruction.bean.response.ParticipantItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/11/25.
 */
@JsonClass(generateAdapter = true)
data class AddGroupEducationReq(
    @Json(name = "teamGroup") val teamGroup: String = "",
    @Json(name = "trainTopic") val trainTopic: String = "",
    @Json(name = "squadLeader") val squadLeader: String = "",
    @Json(name = "participant") val participant: Participant = Participant(),
//    @Json(name = "participant") val participant: JSONObject = JSONObject(),
    @Json(name = "constructionState") val constructionState: String = "",
    @Json(name = "educationTime") val educationTime: String = "",
    @Json(name = "userNo") val userNo: String = "",
//    @Json(name = "attachment") val attachment: String = "",
)