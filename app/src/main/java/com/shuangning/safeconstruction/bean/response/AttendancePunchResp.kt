package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/26.
 */
@JsonClass(generateAdapter = true)
data class AttendancePunchResp(
    @Json(name = "result") val result: Boolean = false,
)