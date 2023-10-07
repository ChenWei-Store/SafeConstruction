package com.shuangning.safeconstruction.utils2.net.calladapter

import com.shuangning.safeconstruction.utils2.net.ErrorCode
import com.shuangning.safeconstruction.utils2.net.HttpResult
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import com.shuangning.safeconstruction.utils2.net.NetworkException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.ParameterizedType

/**
 * Created by Chenwei on 2023/9/27.
 */
class ResultCall(
    private val delegate: Call<Any>,
    private val dataType: ParameterizedType,
    private val onNetCallback: NetworkClient.OnNetCallback?
) : Call<Any>  {
    override fun clone(): Call<Any> {
        return ResultCall(delegate, dataType, onNetCallback)
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
                val body = response.body()
                if (body == null){
                    //可能接口没返回body
                    callback.onResponse(this@ResultCall, Response.success(body))
//                    val error = NetworkException(ErrorCode.NULL_BODY, "body == null")
//                    callback.onFailure(this@ResultCall, error)
//                    onNetCallback?.onOtherError(error)
                    return
                }
                if (body is HttpResult<*>) {
                    if (body.code == 200){
                        callback.onResponse(this@ResultCall, Response.success(body))
                    }else{
                        onNetCallback?.onCodeError(body.code, body.message)
                        callback.onFailure(this@ResultCall, NetworkException(body.code, body.message))
                    }
                }else{
                    val error = NetworkException(ErrorCode.RESPONSE_IS_NOT_HTTP_RESULT,
                        "response is not HttpResult class")
                    onNetCallback?.onOtherError(error)
                    callback.onFailure(this@ResultCall, error)
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                onNetCallback?.onOtherError(t)
                callback.onFailure(this@ResultCall, t)
            }

        })
    }
}