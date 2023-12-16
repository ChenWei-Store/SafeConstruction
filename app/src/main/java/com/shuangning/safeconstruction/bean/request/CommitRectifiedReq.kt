package com.shuangning.safeconstruction.bean.request

import com.shuangning.safeconstruction.bean.response.UploadPhotoItem
import com.shuangning.safeconstruction.bean.response.UploadVideoItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/10.
 */
@JsonClass(generateAdapter = true)
data class CommitRectifiedReq(
    @Json(name = "flowInstanceId") val flowInstanceId: Int = 0,
    @Json(name = "taskInstanceId") val taskInstanceId: Int = 0,
    @Json(name = "zhenggaitupian") val xunchatupian: MutableList<UploadPhotoItem> = mutableListOf(),
    @Json(name = "xianchangmiaoshu0") val xianchangmiaoshu0: String = "",
    @Json(name = "houqicuoshi") val houqicuoshi: String = "",
)