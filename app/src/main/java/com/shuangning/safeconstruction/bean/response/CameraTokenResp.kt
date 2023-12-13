package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CameraTokenResp(
    @Json(name = "status") val status: String = "",
    @Json(name = "expireTime") val expireTime: String = "",
)