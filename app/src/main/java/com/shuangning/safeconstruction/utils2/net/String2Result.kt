package com.shuangning.safeconstruction.utils2.net

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/7.
 */
@JsonClass(generateAdapter=true)
data class String2Result<T>(@Json(name = "code") val code: Int,
                         @Json(name = "message") val message: String,
                         @Json(name = "data") val data: T?)