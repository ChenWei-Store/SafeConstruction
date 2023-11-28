package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/28.
 */
@JsonClass(generateAdapter = true)
data class UploadVideoItem(
    @Json(name = "size") val size: Long = 0L,
    @Json(name = "createTime") val createTime: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "id") val id: String = "",
    @Json(name = "key") val key: String = "",
    @Json(name = "url") val url: String = "",
    @Json(name = "storageId") val storageId: String = "",
)
