package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/10.
 */
@JsonClass(generateAdapter = true)
data class FinesDetailResp(
    @Json(name = "beichufadanwei") val beichufadanwei: String = "",
    @Json(name = "jianchadanwei") val jianchadanwei: String = "",
    @Json(name = "jiancharen") val jiancharen: String = "",
    @Json(name = "shifoushenpi") val shifoushenpi: String = "",
    @Json(name = "leijijine") val leijijine: Double = 0.0,
    @Json(name = "tijiaozhi") val tijiaozhi: String = "",
    @Json(name = "status") val status: Int = 0,
)