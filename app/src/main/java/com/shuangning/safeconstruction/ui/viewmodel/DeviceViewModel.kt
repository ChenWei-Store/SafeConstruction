package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.response.DeviceResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/23.
 */
class DeviceViewModel: ViewModel() {
    private val _data: MutableLiveData<DeviceResp?> = MutableLiveData()
    val data: LiveData<DeviceResp?> = _data
    fun getData(id: Int){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getDevice(id)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _data.postValue(data)
        }
    }
}