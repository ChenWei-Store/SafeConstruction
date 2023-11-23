package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/8.
 */
@JsonClass(generateAdapter = true)
data class UserInfoResp(
    @Json(name="token") val token: String = "",
    @Json(name="extend") val extend: Extends = Extends(),

)

@JsonClass(generateAdapter = true)
data class Company(
    @Json(name = "reference") val realCompany: RealCompany = RealCompany()
)

@JsonClass(generateAdapter = true)
data class RealCompany(
    @Json(name = "referent") val companyName: String = "",
    @Json(name = "id") val companyId: String = "",
)

@JsonClass(generateAdapter = true)
data class Extends(
    @Json(name="xingming") val name: String = "",
    @Json(name="danwei0") val company: Company = Company(),
    @Json(name="danweileixing") val danweileixing: String = "",
)

