package com.shuangning.safeconstruction.utils2.net.calladapter.string2Result

import com.shuangning.safeconstruction.utils2.JsonUtils
import com.shuangning.safeconstruction.utils2.net.HttpResult
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import com.shuangning.safeconstruction.utils2.net.NetworkException
import okhttp3.Request
import okio.Timeout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.ParameterizedType

/**
 * Created by Chenwei on 2023/9/27.
 */
class String2ResultCall(
    private val delegate: Call<Any>,
    private val dataType: ParameterizedType,
    private val onNetCallback: NetworkClient.OnNetCallback?
) : Call<Any>  {
    override fun clone(): Call<Any> {
        return String2ResultCall(delegate, dataType, onNetCallback)
    }

    override fun execute(): Response<Any> {
        throw UnsupportedOperationException()
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object: Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                // 无论请求响应成功还是失败都回调 Response.success
                val errorJson = response.errorBody()?.string()
                kotlin.runCatching {
                    errorJson?.let {
                        val result = JsonUtils.fromJson<HttpResult<Any?>>(it, dataType)
                        result?.let {
                            it2->
                            onNetCallback?.onCodeError(it2.code, it2.message)
                            callback.onFailure(this@String2ResultCall, NetworkException(it2.code, it2.message))
                        }?:let {
                            val error = Throwable("errorJson解析出错")
                            onNetCallback?.onOtherError(error)
                            callback.onFailure(this@String2ResultCall, error)
                        }

                    }?:let {
                        val dataJson = response.body()
                        if (dataJson is String){
                            val json = createJson(dataJson)
                            val data = JsonUtils.fromJson<HttpResult<Any?>>(json, dataType)
                            callback.onResponse(this@String2ResultCall, Response.success(data))
                        }else{
                            callback.onResponse(this@String2ResultCall, Response.success(dataJson))
                        }
                    }
                }.onFailure {
                    onNetCallback?.onOtherError(it)
                    callback.onFailure(this@String2ResultCall, it)
                }

                val body = response.body()
                if (body == null){
                    //可能接口没返回body
                    callback.onResponse(this@String2ResultCall, Response.success(body))
                    return
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                onNetCallback?.onOtherError(t)
                callback.onFailure(this@String2ResultCall, t)
            }
        })
    }

    fun createJson(data: String): String{
        val jsonObject = JSONObject()
        jsonObject.put("code", 1000)
        jsonObject.put("message", "success")
        val dataJsonObject = JSONObject(data)
        jsonObject.put("data", dataJsonObject)
        return jsonObject.toString()
    }
}