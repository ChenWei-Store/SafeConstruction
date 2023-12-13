package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CameraListResp(
    @Json(name = "deviceName") val deviceName: String = "",
    @Json(name = "deviceNo") val deviceNo: String = "",
    @Json(name = "deviceSerialNo") val deviceSerialNo: String = "",
    @Json(name = "biaoduan") val biaoduan: String = "",
):Serializable