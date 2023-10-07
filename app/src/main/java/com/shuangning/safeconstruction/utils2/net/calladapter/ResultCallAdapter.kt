package com.shuangning.safeconstruction.utils2.net.calladapter

import com.shuangning.safeconstruction.utils2.net.NetworkClient
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Chenwei on 2023/9/27.
 */
class ResultCallAdapter(
    val dataType: Type,
    val retrofit: Retrofit,
    val delegate: CallAdapter<*, *>,
    val onNetCallback: NetworkClient.OnNetCallback?,): CallAdapter<Any, Call<Any>>  {
    override fun responseType(): Type {
        return delegate.responseType()
    }

    override fun adapt(call: Call<Any>): Call<Any> {
        return ResultCall(call, dataType as ParameterizedType, onNetCallback)
    }
}
