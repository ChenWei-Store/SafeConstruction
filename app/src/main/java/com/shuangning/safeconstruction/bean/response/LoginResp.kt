package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/6.
 */
@JsonClass(generateAdapter=true)
data class LoginResp(
    @Json(name = "token") val token: String = "",
    @Json(name = "userId") val userId: String = "",
    @Json(name = "username") val username: String = "",
    @Json(name = "fullName") val fullName: String = "",
)