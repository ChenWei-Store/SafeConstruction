package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/2.
 */
@JsonClass(generateAdapter = true)
data class UserBaseInfoResp (
    @Json(name = "sex") val sex: String = "",
    @Json(name = "userName") val userName: String = "",
    @Json(name = "imageUrl") val imageUrl: String = "",
    @Json(name = "idCardNo") val idCardNo: String = "",
    @Json(name = "nativePlace") val nativePlace: String = "",
    @Json(name = "companyName") val companyName: String = "",
    @Json(name = "userNo") val userNo: String = "",
    @Json(name = "departmentName") val departmentName: String = "",
    @Json(name = "jobName") val jobName: String = "",
    @Json(name = "teamGroup") val teamGroup: String = "",
    @Json(name = "manType") val manType: String = "",
    @Json(name = "enterStatus") val enterStatus: String = "",
    @Json(name = "enterTime") val enterTime: String = "",
    @Json(name = "remark") val remark: String = "",
)