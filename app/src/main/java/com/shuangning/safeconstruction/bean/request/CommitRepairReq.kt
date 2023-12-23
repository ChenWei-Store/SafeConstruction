package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/23.
 */
@JsonClass(generateAdapter = true)
data class CommitRepairReq(
    @Json(name = "weibaoshijian") val weibaoshijian: String = "",
    @Json(name = "weibaoneirong") val weibaoneirong: String = "",
    @Json(name = "xiaciweibaoshijian") val xiaciweibaoshijian: String = "",
    @Json(name = "id") val id: Int = 0,
)