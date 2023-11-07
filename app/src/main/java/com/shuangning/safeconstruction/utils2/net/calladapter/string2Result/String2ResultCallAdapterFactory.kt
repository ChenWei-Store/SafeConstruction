package com.shuangning.safeconstruction.utils2.net.calladapter.string2Result

import com.shuangning.safeconstruction.utils2.net.NetworkClient
import com.shuangning.safeconstruction.utils2.net.String2Result
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Chenwei on 2023/9/27.
 */
class String2ResultCallAdapterFactory(val onNetCallback: NetworkClient.OnNetCallback?): CallAdapter.Factory() {
    /**
     * 获取自定义callAdapter
     * 返回null则跳过当前factory执行下一个factory
     */
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // suspend 函数在 Retrofit 中的返回值其实是 `Call`
        // 例如：Call<ApiResponse<User>>
        if (getRawType(returnType) != Call::class.java) return null
        if(returnType !is ParameterizedType)  throw  IllegalArgumentException(
                "return type must be parameterized as Call<Foo> or Call<? extends Foo>");
        // 取 Call 里边一层泛型参数
        val innerType: Type = getParameterUpperBound(0, returnType)
        // 如果不是 String 则不由本 CallAdapter.Factory 处理
        if (getRawType(innerType) != String2Result::class.java) return null
        // 获取后续代理
        val delegate: CallAdapter<*, *> = retrofit
            .nextCallAdapter(this, returnType, annotations)
        return ResultCallAdapter(
            innerType,
            retrofit,
            delegate,
            onNetCallback
        )
    }

    class ResultCallAdapter(
        val dataType: Type,
        val retrofit: Retrofit,
        val delegate: CallAdapter<*, *>,
        val onNetCallback: NetworkClient.OnNetCallback?,): CallAdapter<Any, Call<Any>>  {
        override fun responseType(): Type {
            return delegate.responseType()
        }

        override fun adapt(call: Call<Any>): Call<Any> {
            return String2ResultCall(call, dataType as ParameterizedType, onNetCallback)
        }
    }
}