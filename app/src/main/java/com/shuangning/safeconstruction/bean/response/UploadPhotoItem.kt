package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/28.
 */
@JsonClass(generateAdapter = true)
data class UploadPhotoItem(
    @Json(name = "name") val name: String = "",
    @Json(name = "id") val id: Int = 0,
    @Json(name = "key") val key: String = "",
    @Json(name = "url") val url: String = "",
)
