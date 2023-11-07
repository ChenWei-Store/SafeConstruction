package com.shuangning.safeconstruction.bean.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/6.
 */
@JsonClass(generateAdapter=true)
data class LoginReq(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String,
    @Json(name = "identityType") val identityType: String = "PASSWORD",
)