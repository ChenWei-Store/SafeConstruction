package com.shuangning.safeconstruction.bean.response

import com.shuangning.safeconstruction.base.adapter.CONTENT
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/26.
 */
@JsonClass(generateAdapter=true)
data class GroupEducationDetailResp(
    @Json(name = "teamGroup") val teamGroup: String = "",
    @Json(name = "trainTopic") val trainTopic: String = "",
    @Json(name = "squadLeader") val squadLeader: String = "",
    @Json(name = "participant") val participant: Participant = Participant(),
    @Json(name = "createBy") val createBy: String = "",
    @Json(name = "createTime") val createTime: String = "",
    @Json(name = "buildStatus") val buildStatus: String = "",
    @Json(name = "educationTime") val educationTime: String = "",
    @Json(name = "attachment") val attachment: String = "",
    @Json(ignore = true) var type: Int = CONTENT,
    ): IItemViewType{
    override fun getItemType(): Int {
        return type
    }
}