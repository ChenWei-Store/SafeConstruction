package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/10.
 */
@JsonClass(generateAdapter = true)
data class FinesListItem(
    @Json(name = "jiancharen") val jiancharen: String = "",
    @Json(name = "wentibianhao") val wentibianhao: String = "",
    @Json(name = "jianchadanwei") val jianchadanwei: String = "",
    @Json(name = "beichufadanwei") val beichufadanwei: String = "",
    @Json(name = "leijijine") val leijijine: String = "",
    @Json(name = "id") val id: String = "",
)