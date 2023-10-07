package com.shuangning.safeconstruction.utils2.net

import com.shuangning.safeconstruction.BuildConfig
import com.shuangning.safeconstruction.utils.PathUtils
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.calladapter.ResultCallAdapterFactory
import com.shuangning.safeconstruction.utils2.net.interceptor.HeaderInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    private var callback: OnNetCallback?= null
    private var baseUrl = ""
    private val servicesMap = mutableMapOf<String, Any>()
    private var retrofit: Retrofit? = null
    fun init(baseUrl: String, callback:OnNetCallback){
        this.baseUrl = baseUrl
        this.callback = callback
    }
    fun retrofit(): NetworkClient {
        if(baseUrl.isNullOrBlank()){
            throw NullPointerException("请调用NetworkClient.init() 初始化baseurl")
        }
        MyLog.d("baseUrl:${baseUrl}")
        if (retrofit == null) {
            val okHttpClient = newOkhttp()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(ResultCallAdapterFactory(callback))
                .build()
        }
        return this
    }

    private fun newOkhttp(): OkHttpClient{
        val cache = Cache(File(PathUtils.getExternalAppCachePath(), "cache"), CACHE_SIZE)
        return OkHttpClient.Builder()
            .readTimeout(TIME_OUT_TIME, TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_OUT_TIME, TimeUnit.MILLISECONDS)
            .cache(cache)
            .addInterceptor(HeaderInterceptor(callback))
            .addInterceptor(HttpLoggingInterceptor(MyHttpLogger()).apply {
                level = if(BuildConfig.DEBUG){
                    HttpLoggingInterceptor.Level.BODY
                }else{
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }
    fun <T> createService(serviceCls: Class<T>): T{
        if (servicesMap.containsKey(serviceCls.name)){
            return servicesMap[serviceCls.name] as T
        }
        val service = retrofit?.create(serviceCls)
        servicesMap[serviceCls.name] = service!!
        return service
    }

    class MyHttpLogger: HttpLoggingInterceptor.Logger{
        override fun log(message: String?) {
            message?.let {
                MyLog.d(it)
            }
        }
    }

    interface OnNetCallback{
        fun onCodeError(code: Int, msg: String)
        fun onOtherError(throwable: Throwable)
        fun getToken(): String?
    }

    companion object{
        val client = NetworkClient()
        const val TIME_OUT_TIME = 5000L
        const val CACHE_SIZE = 100 * 1024 * 1024L
    }
}