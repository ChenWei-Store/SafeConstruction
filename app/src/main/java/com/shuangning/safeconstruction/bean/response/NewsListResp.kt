package com.shuangning.safeconstruction.bean.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/3.
 */
@JsonClass(generateAdapter = true)
data class NewsListResp(
    @Json(name = "newsInfoList") val newsInfoList: MutableList<String> = mutableListOf(),
)