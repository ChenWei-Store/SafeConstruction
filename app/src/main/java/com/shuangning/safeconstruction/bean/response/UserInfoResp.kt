package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/8.
 */
@JsonClass(generateAdapter=true)
data class UserInfoResp(
    @Json(name = "token") val token: String = "",

    )