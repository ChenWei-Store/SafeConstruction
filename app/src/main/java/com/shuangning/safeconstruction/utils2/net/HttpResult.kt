package com.shuangning.safeconstruction.utils2.net

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/9/26.
 */
@JsonClass(generateAdapter=true)
data class HttpResult<T>(
    @Json(name = "code") val code: Int,
    @Json(name = "message") val message: String,
    @Json(name = "data") val data: T? = null
)

fun <T> HttpResult<T>.getOrNull(): T? {
    if (code == 200){
        return data
    }else{
        return null
    }
}

fun <T> HttpResult<T>.getOrThrow(): T? {
    if (code == 200){
        return data
    }else{
        throw NetworkException(code, message)
    }
}


