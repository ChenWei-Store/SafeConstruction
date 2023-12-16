package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/14.
 */
@JsonClass(generateAdapter = true)
data class ConstructionTeamResp(
    @Json(name = "name") val name: String = "",
    @Json(name = "id") val id: Int = 0,
    @Json(name = "children") val children: MutableList<ConstructionTeamItem> = mutableListOf(),
)

@JsonClass(generateAdapter = true)
data class ConstructionTeamItem(
    @Json(name = "name") val name: String = "",
    @Json(name = "id") val id: Int = 0,
)