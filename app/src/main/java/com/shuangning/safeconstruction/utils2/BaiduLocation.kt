package com.shuangning.safeconstruction.utils2

import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.SDKInitializer
import com.shuangning.safeconstruction.BuildConfig
import com.shuangning.safeconstruction.base.BaseApplication

/**
 * Created by Chenwei on 2023/10/28.
 */
const val WGS84 = "wgs84" //GPS坐标系
const val GCJ02 = "gcj02" //国测局坐标,WGS84加密后得到的坐标系
const val BD09LL = "bd09ll" //百度经纬度坐标,在GCJ02坐标系基础上再次加密
const val BD09 = "bd09" //百度墨卡托坐标
object BaiduLocation: BDLocationListener{
    private var locationClient: LocationClient?= null
    private var locationCallback: OnLocationResultCallback?= null
    fun init(){
        SDKInitializer.setAgreePrivacy(BaseApplication.appContext, true)
        SDKInitializer.initialize(BaseApplication.appContext)
        SDKInitializer.setApiKey(BuildConfig.BAIDU_LBS_AK)
        LocationClient.setKey(BuildConfig.BAIDU_LBS_AK)
        //同意隐私政策后调用setAgreePrivacy接口以进行SDK初始化
        LocationClient.setAgreePrivacy(true)
        try {
            locationClient = LocationClient(BaseApplication.appContext)
            val options = LocationClientOption()
            //模式
            options.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
            //经纬度坐标类型
            options.coorType = GCJ02
            //第一次时准确度优先
            options.firstLocType = LocationClientOption.FirstLocType.ACCURACY_IN_FIRST_LOC
            //定位请求的间隔,ms,0为获取一次，如果设置非0，需设置1000ms以上才有效
            options.scanSpan = 1500
            options.setIsNeedLocationDescribe(true)
            options.setIsNeedAddress(true)
            locationClient?.locOption = options
        }catch (e: Exception){
            MyLog.e(e.message.toString())
        }
    }

    fun start(listener: OnLocationResultCallback){
        this.locationCallback = listener
        locationClient?.registerLocationListener(this)
        locationClient?.start()
    }
    fun stop(){
        locationClient?.stop()
        locationClient?.unRegisterLocationListener(this)
        this.locationCallback = null
    }

    override fun onReceiveLocation(location: BDLocation?) {
        location?.apply {
            val latitude = latitude //获取纬度信息
            val longitude = longitude //获取经度信息
            val addr: String = addrStr ?: ""//获取详细地址信息
            val country: String = country ?: "" //获取国家
            val province: String = province ?: ""//获取省份
            val city: String = city ?: ""//获取城市
            val district: String = district ?: ""//获取区县
            val street: String = street ?: ""//获取街道信息
            val town: String = town ?: ""//获取乡镇信息
            val locationDescribe = locationDescribe ?: ""
            val isSuccess = latitude > 0 && longitude > 0 && addr.isNotEmpty()
            locationCallback?.onLocationResult(isSuccess,
                LocationResult(latitude, longitude, addr, country,
                    province, city, district, street, town, locationDescribe))
        }?:let {
            locationCallback?.onLocationResult(false, null)
        }

    }

    interface OnLocationResultCallback{
        fun onLocationResult(isSuccess: Boolean, result: LocationResult?)
    }

    data class LocationResult(
        val latitude: Double = 0.0, //纬度
        val longitude: Double = 0.0, //精度
        val addr: String = "", //详细地址信息
        val country: String = "", //获取国家
        val province: String = "",  //获取省份
        val city: String = "", //获取城市
        val district: String = "", //获取区县
        val street: String = "",  //获取街道信息
        val town: String = "", //获取乡镇信息
        val locationDescribe: String = "" //位置描述
    )
}