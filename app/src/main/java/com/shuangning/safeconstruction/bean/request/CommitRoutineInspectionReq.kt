package com.shuangning.safeconstruction.bean.request

import com.shuangning.safeconstruction.bean.response.UploadPhotoItem
import com.shuangning.safeconstruction.bean.response.UploadVideoItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/10.
 */
@JsonClass(generateAdapter = true)
data class CommitRoutineInspectionReq(
    @Json(name = "weidu") val weidu: Double = 0.0,
    @Json(name = "jingdu") val jingdu: Double = 0.0,
    @Json(name = "jianchafenlei") val jianchafenlei: String = "",
    @Json(name = "xunchatupian") val xunchatupian: MutableList<UploadPhotoItem> = mutableListOf(),
    @Json(name = "jianchaxiang") val jianchaxiang: JianChaXiang = JianChaXiang(),
    @Json(name = "biaoduan") val biaoduan: String = "",
    @Json(name = "shigongdui") val shigongdui: String = "",
    @Json(name = "zhenggaiqixian") val zhenggaiqixian: String = "",
    @Json(name = "xianchangmiaoshu") val xianchangmiaoshu: String = "",
    @Json(name = "zhenggaiyaoqiu") val zhenggaiyaoqiu: String = "",
    @Json(name = "zhenggaichuliren") val zhenggaichuliren: ZhengGaiChuLiRen = ZhengGaiChuLiRen(),
)

@JsonClass(generateAdapter = true)
data class JianChaXiang(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "referent") val referent: String = "",
)

@JsonClass(generateAdapter = true)
data class ZhengGaiChuLiRen(
    @Json(name = "id") val id: Int = 0,
)