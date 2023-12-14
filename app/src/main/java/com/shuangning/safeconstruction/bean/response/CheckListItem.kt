package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/14.
 */
@JsonClass(generateAdapter=true)
data class CheckListItem(
    @Json(name = "parentId") val parentId: Int = 0,
    @Json(name = "id") val id: Int = 0,
    @Json(name = "name") val name: String = "",
)
