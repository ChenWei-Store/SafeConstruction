package com.shuangning.safeconstruction.utils2.net.interceptor

import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Created by Chenwei on 2023/12/10.
 */
class TimeOutInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        MyLog.d("TimeOutInterceptor")
        val request = chain.request()
        val newChain = if (request.body is MultipartBody){
            MyLog.d("UPLOAD_TIME_OUT")
            chain.withReadTimeout(NetworkClient.UPLOAD_TIME_OUT_TIME.toInt(), TimeUnit.SECONDS)
                .withWriteTimeout(NetworkClient.UPLOAD_TIME_OUT_TIME.toInt(), TimeUnit.SECONDS)
                .withConnectTimeout(NetworkClient.UPLOAD_TIME_OUT_TIME.toInt(), TimeUnit.SECONDS)
        }else{
            MyLog.d("COMMON_TIME_OUT")
            chain.withReadTimeout(NetworkClient.TIME_OUT_TIME.toInt(), TimeUnit.SECONDS)
                .withWriteTimeout(NetworkClient.TIME_OUT_TIME.toInt(), TimeUnit.SECONDS)
                .withConnectTimeout(NetworkClient.TIME_OUT_TIME.toInt(), TimeUnit.SECONDS)
        }
        MyLog.d("TimeOutInterceptor end")
        return newChain.proceed(request)
    }
}