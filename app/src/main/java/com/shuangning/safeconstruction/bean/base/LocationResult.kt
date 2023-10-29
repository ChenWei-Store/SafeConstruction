package com.shuangning.safeconstruction.bean.base

/**
 * Created by Chenwei on 2023/10/28.
 */
data class LocationResult(
    val latitude: Double, //纬度
    val longitude: Double, //精度
    val addr: String, //详细地址信息
    val country: String, //获取国家
    val province: String,  //获取省份
    val city: String, //获取城市
    val district: String, //获取区县
    val street: String,  //获取街道信息
    val town: String, //获取乡镇信息
)