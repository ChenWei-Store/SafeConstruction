package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/18.
 */
@JsonClass(generateAdapter = true)
data class AddFineItemReq(
    @Json(name = "chufashuoming") val chufashuoming: String = "",
    @Json(name = "fakuanjine") val fakuanjine: String = "",
    @Json(name = "fakuanleibie") val fakuanleibie: String = "",
    @Json(name = "id") val id: Int = 0,
    )
