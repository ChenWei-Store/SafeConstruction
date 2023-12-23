package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/23.
 */
@JsonClass(generateAdapter = true)
data class DeviceResp (
    @Json(name = "shebeimingcheng") val shebeimingcheng: String = "",
    @Json(name = "shebeibianma") val shebeibianma: String = "",
    @Json(name = "shebeifenlei") val shebeifenlei: String = "",
    @Json(name = "shiyongbuwei") val shiyongbuwei: String = "",
    @Json(name = "caozuofuzeren") val caozuofuzeren: String = "",
    @Json(name = "shifouzudai") val shifouzudai: String = "",
    @Json(name = "jinchangshijian") val jinchangshijian: String = "",
    @Json(name = "jianyan") val jianyan: String = "",
    @Json(name = "guigexinghao") val guigexinghao: String = "",
    @Json(name = "shebeizhaopian") val shebeizhaopian: String = "",
    @Json(name = "deviceInspectionVOS") val deviceSensing: MutableList<DeviceSensingItem> = mutableListOf(),
    @Json(name = "deviceMaintenanceVOS") val deviceRepair: MutableList<DeviceRepairItem> = mutableListOf(),
    )

@JsonClass(generateAdapter = true)
data class DeviceSensingItem (
    @Json(name = "jianyanshijian") val jianyanshijian: String = "",
    @Json(name = "jianyanjieguo") val jianyanjieguo: String = "",
    @Json(name = "xiacijianyanshijian") val xiacijianyanshijian: String = "",
    @Json(name = "userId") val userId: String = "",
    @Json(name = "date") val date: String = "",
    @Json(name = "id") val id: Int = 0,
    @Json(name = "shebeizhaopian") var shebeizhaopian: String = "",
    @Json(name = "shebeimingcheng") var shebeimingcheng: String = "",
)

@JsonClass(generateAdapter = true)
data class DeviceRepairItem (
    @Json(name = "weibaoshijian") val weibaoshijian: String = "",
    @Json(name = "weibaoneirong") val weibaoneirong: String = "",
    @Json(name = "xiaciweibaoshijian") val xiaciweibaoshijian: String = "",
    @Json(name = "date") val date: String = "",
    @Json(name = "userId") val userId: String = "",
    @Json(name = "id") val id: Int = 0,
    @Json(name = "shebeizhaopian") var shebeizhaopian: String = "",
    @Json(name = "shebeimingcheng") var shebeimingcheng: String = "",
)