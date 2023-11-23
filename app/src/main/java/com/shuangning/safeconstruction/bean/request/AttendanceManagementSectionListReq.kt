package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/23.
 */

@JsonClass(generateAdapter = true)
data class AttendanceManagementSectionListReq(
    @Json(name = "companyType") val companyType: String = "",
)
