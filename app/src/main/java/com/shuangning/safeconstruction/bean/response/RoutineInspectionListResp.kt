package com.shuangning.safeconstruction.bean.response

import com.shuangning.safeconstruction.base.adapter.CONTENT
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/8.
 */
@JsonClass(generateAdapter = true)
data class RoutineInspectionListResp(
    @Json(name = "result") val result: MutableList<RoutineInspectionItem> = mutableListOf(),
)
@JsonClass(generateAdapter = true)
data class RoutineInspectionItem(
    @Json(name = "biaoduan") val biaoduan: String = "",
    @Json(name = "jianchaxiang") val jianchaxiang: String = "",
    @Json(name = "xianchangmiaoshu0") val xianchangmiaoshu0: String = "",
    @Json(name = "flowInstanceId") val flowInstanceId: Int = 0,
    @Json(name = "flowNodeInstanceId") val flowNodeInstanceId: Int = 0,
    @Json(name = "status") val status: String = "",
    @Json(ignore = true) val type: Int = CONTENT,
): IItemViewType {
    override fun getItemType(): Int {
        return type
    }
}