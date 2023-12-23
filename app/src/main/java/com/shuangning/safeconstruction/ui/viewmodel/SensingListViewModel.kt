package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.response.DeviceSensingItem
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/23.
 */
class SensingListViewModel: ViewModel() {
    private val _data: MutableLiveData<MutableList<DeviceSensingItem>> = MutableLiveData()
    val data: LiveData<MutableList<DeviceSensingItem>> = _data
    fun getData(id: Int){
        val result = mutableListOf<DeviceSensingItem>()
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getDevice(id)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            data?.deviceSensing?.let {
                it.forEach {
                    it.shebeimingcheng = data.shebeimingcheng
                    it.shebeizhaopian = data.shebeizhaopian
                 }
                result.addAll(it)
            }
            _data.postValue(result)
        }
    }
}