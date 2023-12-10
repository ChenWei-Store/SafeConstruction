package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/10.
 */
@JsonClass(generateAdapter = true)
data class FinesListItem(
    @Json(name = "biaoduan") val biaoduan: String = "",
    @Json(name = "wentibianhao") val wentibianhao: String = "",
    @Json(name = "beizhu") val beizhu: String = "",
    @Json(name = "status") val status: String = "",
    @Json(name = "id") val id: String = "",
)