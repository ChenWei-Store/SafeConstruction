package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2024/4/17.
 */
@JsonClass(generateAdapter = true)
data class VersionResp(
    @Json(name="versionCode")
    val versionCode: String = "",
    @Json(name="versionName")
    val versionName: String = "",
    @Json(name="content")
    val content: String = "",
    @Json(name="url")
    val url: String = "",
    @Json(name="capacity")
    val capacity: Double = 0.0,
)
