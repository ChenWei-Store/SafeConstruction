package com.shuangning.safeconstruction.data.net

import com.shuangning.safeconstruction.bean.request.LoginReq
import com.shuangning.safeconstruction.bean.response.LoginResp
import com.shuangning.safeconstruction.utils2.net.String2Result
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Chenwei on 2023/11/6.
 */
interface ApiService {
    @POST(LOGIN)
    suspend fun login(@Body data: LoginReq): String2Result<LoginResp>?
}