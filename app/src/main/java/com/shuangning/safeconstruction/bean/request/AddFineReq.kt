package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/18.
 */
@JsonClass(generateAdapter = true)
data class AddFineReq(
    @Json(name = "beichufadanwei") val beichufadanwei: String = "",
    @Json(name = "jianchadanwei") val jianchadanwei: String = "",
    @Json(name = "richangxunchabianhao") val richangxunchabianhao: String = "",
)
