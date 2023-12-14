package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/14.
 */
@JsonClass(generateAdapter = true)
data class PersonResp(
    @Json(name = "list") val children: MutableList<PersonItem> = mutableListOf(),
)

@JsonClass(generateAdapter = true)
data class PersonItem(
    @Json(name = "fullName") val name: String = "",
    @Json(name = "id") val id: Int = 0,
)