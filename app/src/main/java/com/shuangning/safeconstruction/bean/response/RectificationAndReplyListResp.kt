package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/13.
 */
@JsonClass(generateAdapter = true)
data class RectificationAndReplyListResp(
    @Json(name = "list") val result: MutableList<RectificationAndReplyItem> = mutableListOf(),
)

@JsonClass(generateAdapter = true)
data class RectificationAndReplyItem(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "flowInstanceId") val flowInstanceId: Int = 0,
    @Json(name = "taskInstanceId") val taskInstanceId: Int = 0,
    @Json(name = "taskStatus") val taskStatus: String = "",
    @Json(name = "type") val type: String = "",
    @Json(name = "tenderCode") val tenderCode: String = "",
    @Json(name = "status") val status: String = "",
    @Json(name = "checkOutNo") val checkOutNo: String = "",
    @Json(name = "checkItem") val checkItem: String = "",
    @Json(name = "imgUrl") val imgUrl: String = "",
    @Json(name = "taskInfo") val taskInfo: TaskInfo = TaskInfo(),
)

@JsonClass(generateAdapter = true)
data class TaskInfo(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "createTime") val createTime: String = "",
    @Json(name = "updateTime") val updateTime: String = "",
    @Json(name = "type") val type: String = "",
    @Json(name = "checkUser") val checkUser: String = "",
    @Json(name = "checkItem") val checkItem: String = "",
    @Json(name = "tenderCode") val tenderCode: String = "",
    @Json(name = "isCorrect") val isCorrect: Boolean = false,
    @Json(name = "dueTime") val dueTime: String = "",
    @Json(name = "status") val status: String = "",
    @Json(name = "zhenggaishijian") val zhenggaishijian: String = "",
    @Json(name = "houqicuoshi") val houqicuoshi: String = "",
    @Json(name = "shenheyijian") val shenheyijian: String = "",
    @Json(name = "zhenggaitupian") val zhenggaitupian: String = "",
)
