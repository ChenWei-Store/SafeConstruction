package com.shuangning.safeconstruction.utils2.net.interceptor

import com.shuangning.safeconstruction.utils2.MyLog
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset

/**
 * Created by Chenwei on 2023/11/26.
 */
class CustomLogInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        printRequestLog(request)
        printResponseLog(response)
        return response
    }

    private fun printResponseLog(response: Response?){
        if (response == null) {
            return
        }
        MyLog.d("response: code=${response.code} data=${getResponseText(response)}")
    }

    private fun printRequestLog(request: Request?) {
        if (request == null) {
            return
        }
        MyLog.d("request:${request.method} ${request.url}")
        MyLog.d("header:${getRequestHeaders(request)}")
        val body = request.body
        if (body is MultipartBody){
            MyLog.d("Multipart: size=${body.parts.size}")
        }else{
            MyLog.d("request param: ${getRequestParams(request)}")
        }
    }

    /**
     * 获取请求参数
     */
    private fun getRequestParams(request: Request): String {
        var str: String? = null
        try {
            request.body?.let {
                val buffer = Buffer()
                it.writeTo(buffer)
                val charset = it.contentType()?.charset(Charset.forName("UTF-8"))
                    ?: Charset.forName("UTF-8")
                str = buffer.readString(charset)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            str = ""
        }

        return if (str.isNullOrEmpty()) "Empty!" else str!!
    }

    private fun getRequestHeaders(request: Request): String {
        val headers = request.headers
        return if (headers.size > 0) {
            headers.toString()
        } else {
            "Empty!"
        }
    }

    /**
     * 获取返回数据字符串
     */
    private fun getResponseText(response: Response): String {
        try {
            response.body?.let {
                val source = it.source()
                source.request(Long.MAX_VALUE)
                val buffer = source.buffer
                val charset = it.contentType()?.charset(Charset.forName("UTF-8"))
                    ?: Charset.forName("UTF-8")
                if (it.contentLength().toInt() != 0) {
                    buffer.clone().readString(charset).let { result ->
                        return result
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "Empty!"
    }
}