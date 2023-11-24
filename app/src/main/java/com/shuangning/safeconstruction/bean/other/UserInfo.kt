package com.shuangning.safeconstruction.bean.other

/**
 * Created by Chenwei on 2023/10/23.
 */
data class UserInfo(
    val userName: String,
    val pwd: String,
    var companyType: String = "",
    var userId: String = "",
)