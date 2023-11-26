package com.shuangning.safeconstruction.bean.response

import com.shuangning.safeconstruction.base.adapter.CONTENT
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/6.
 */
@JsonClass(generateAdapter=true)
data class GroupEducationListResp(
    @Json(name = "total") val total: String = "",
    @Json(name = "result") val result: MutableList<GroupEducationItem> = mutableListOf(),
)

@JsonClass(generateAdapter=true)
data class GroupEducationItem(
    @Json(name = "teamGroup") val teamGroup: String = "",
    @Json(name = "trainTopic") val trainTopic: String = "",
    @Json(name = "squadLeader") val squadLeader: String = "",
    @Json(name = "participant") val participant: String = "",
    @Json(name = "createBy") val createBy: String = "",
    @Json(name = "createTime") val createTime: String = "",
    @Json(ignore = true) var type: Int = CONTENT,
): IItemViewType{
    override fun getItemType(): Int {
        return type
    }
}