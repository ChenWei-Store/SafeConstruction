package com.shuangning.safeconstruction.bean.other

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/2.
 */
@JsonClass(generateAdapter = true)
data class QRcodeResult(
    @Json(name = "userId") val userId: Int = -1,
)
