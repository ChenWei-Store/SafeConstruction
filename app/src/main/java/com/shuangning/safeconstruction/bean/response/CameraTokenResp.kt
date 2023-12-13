package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CameraTokenResp(
    @Json(name = "msg") val msg: String = "",
    @Json(name = "code") val code: String = "",
    @Json(name = "data") val data: CameraTokenRespItem? = CameraTokenRespItem(),
)

@JsonClass(generateAdapter = true)
data class CameraTokenRespItem(
    @Json(name = "accessToken") val accessToken: String = "",
    @Json(name = "expireTime") val expireTime: String = "",
)