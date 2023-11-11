package com.shuangning.safeconstruction.utils2.net.convert

import com.shuangning.safeconstruction.utils2.net.HttpResult
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by Chenwei on 2023/11/7.
 */
class StringResponseConvertFactory: Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        //直接用type，由于包含泛型时，不匹配
        if (getRawType(type) != HttpResult::class.java){
            //类型不匹配，执行下一个converfactory
            return null
        }
        return StringResponseBodyConvert()
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        //request不处理，直接返回null跳过
        return null
    }


    class StringResponseBodyConvert: Converter<ResponseBody, String>{
        override fun convert(value: ResponseBody): String? {
            return value.string()
        }
    }

    companion object{
        fun create(): StringResponseConvertFactory{
            return StringResponseConvertFactory()
        }
    }
}