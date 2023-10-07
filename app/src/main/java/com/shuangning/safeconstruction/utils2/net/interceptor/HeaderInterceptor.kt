package com.shuangning.safeconstruction.utils2.net.interceptor

import com.shuangning.safeconstruction.utils2.net.NetworkClient
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Chenwei on 2023/9/26.
 */
class HeaderInterceptor(private val callback: NetworkClient.OnNetCallback?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = callback?.getToken()?:""
        val request = chain.request()
            .newBuilder()
//            .addHeader(KeyName.TOKEN_KEY, token)
//            .addHeader(KeyName.VERSION_KEY,  APPUtils.getVersionCode().toString())
//            .addHeader(KeyName.PACKAGE_NAME_KEY, APPUtils.getPackageName())
            .build()
        return chain.proceed(request)
    }
}