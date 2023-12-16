package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/16.
 */
@JsonClass(generateAdapter = true)
data class RectificationAndReplyDetailResp (
    @Json(name = "richangxunchabianhao") val richangxunchabianhao: String = "",
    @Json(name = "xianchangmiaoshu") val xianchangmiaoshu: String = "",
    @Json(name = "xunchatupian") val xunchatupian: String = "",
    @Json(name = "jianchaxiang") val jianchaxiang: String = "",
    @Json(name = "zhenggaichuliren") val zhenggaichuliren: String = "",
    @Json(name = "zhenggaiqixian") val zhenggaiqixian: String = "",
    @Json(name = "zhenggaitupian") val zhenggaitupian: String = "",
    @Json(name = "shenheren") val shenheren: String = "",
    @Json(name = "xianchangmiaoshu0") val xianchangmiaoshu0: String = "",
    @Json(name = "houqicuoshi") val houqicuoshi: String = "",
    @Json(name = "zhenggairen") val zhenggairen: String = "",
    @Json(name = "zhenggaishijian") val zhenggaishijian: String = "",
    @Json(name = "shenpijieguo") val shenpijieguo: String = "",
    @Json(name = "shenheyijian") val shenheyijian: String = "",
    @Json(name = "shenheshijian") val shenheshijian: String = "",
    @Json(name = "zhenggaiyaoqiu") val zhenggaiyaoqiu: String = "",
    @Json(name = "jianchafenlei") val jianchafenlei: String = "",

)