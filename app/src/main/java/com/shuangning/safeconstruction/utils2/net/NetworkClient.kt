package com.shuangning.safeconstruction.utils2.net

import com.shuangning.safeconstruction.utils.PathUtils
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.calladapter.string2Result.String2ResultCallAdapterFactory
import com.shuangning.safeconstruction.utils2.net.convert.StringResponseConvertFactory
import com.shuangning.safeconstruction.utils2.net.interceptor.CustomLogInterceptor
import com.shuangning.safeconstruction.utils2.net.interceptor.HeaderInterceptor
import com.shuangning.safeconstruction.utils2.net.interceptor.TimeOutInterceptor
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit

/**
 * Created by Chenwei on 2023/9/13.
 * TODO:
 * 1.dns, cache拦截器
 * 2.重试拦截器
 * 3.gzip拦截器
 */
class NetworkClient {
    private var callback: OnNetCallback? = null
    private var baseUrl = ""
    private val servicesMap = mutableMapOf<String, Any>()
    private var retrofit: Retrofit? = null
    private lateinit var okHttpClient: OkHttpClient
    fun init(baseUrl: String, callback: OnNetCallback?) {
        this.baseUrl = baseUrl
        this.callback = callback
    }

    fun retrofit(): NetworkClient {
        if (baseUrl.isNullOrBlank()) {
            throw NullPointerException("请调用NetworkClient.init() 初始化baseurl")
        }
        MyLog.d("baseUrl:${baseUrl}")
        if (retrofit == null) {
            okHttpClient = newOkhttp()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                //ConverterFactory按添加顺序先后执行，第一个返回null，
                // 则执行第二个ConverterFactory，如果第一个执行了，则不继续执行
                .addConverterFactory(StringResponseConvertFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(String2ResultCallAdapterFactory(callback))
//                .addCallAdapterFactory(ResultCallAdapterFactory(callback))
                .build()
        }
        return this
    }

    private fun newOkhttp(): OkHttpClient {
        val cache = Cache(File(PathUtils.getExternalAppCachePath(), "cache"), CACHE_SIZE)
        return OkHttpClient.Builder()
            .readTimeout(TIME_OUT_TIME, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT_TIME, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_TIME, TimeUnit.SECONDS)
            //自定义连接池最大空闲连接数和等待时间大小，否则默认最大5个空闲连接(优化文件上传)
            .connectionPool(ConnectionPool(32, 5, TimeUnit.MINUTES))
            .cache(cache)
            .addInterceptor(HeaderInterceptor(callback))
            .addInterceptor(CustomLogInterceptor())
            .addInterceptor(TimeOutInterceptor())
            .build()
    }

    fun <T> createService(serviceCls: Class<T>): T {
        if (servicesMap.containsKey(serviceCls.name)) {
            return servicesMap[serviceCls.name] as T
        }
        val service = retrofit?.create(serviceCls)
        servicesMap[serviceCls.name] = service!!
        return service
    }

    interface OnNetCallback {
        fun onCodeError(code: Int, msg: String)
        fun onOtherError(throwable: Throwable)
        fun getToken(): String
    }

    companion object {
        val client = NetworkClient()
        const val TIME_OUT_TIME = 10L
        const val CACHE_SIZE = 100 * 1024 * 1024L
        const val UPLOAD_TIME_OUT_TIME = 60L
    }
}