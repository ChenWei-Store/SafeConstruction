package com.shuangning.safeconstruction.bean.request

import com.shuangning.safeconstruction.bean.response.UploadPhotoItem
import com.shuangning.safeconstruction.bean.response.UploadVideoItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/10.
 */
@JsonClass(generateAdapter = true)
data class CommitAuditReq(
    @Json(name = "flowInstanceId") val flowInstanceId: Int = 0,
    @Json(name = "taskInstanceId") val taskInstanceId: Int = 0,
    @Json(name = "shenheyijian") val shenheyijian: String = "",
    @Json(name = "shenpijieguo") val shenpijieguo: String = "",
)