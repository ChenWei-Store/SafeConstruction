package com.shuangning.safeconstruction.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.response.CameraListResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {
    val cameraList: MutableLiveData<MutableList<CameraListResp>> = MutableLiveData()
    val cameraToken: MutableLiveData<String> = MutableLiveData()

    fun getCameraList() {
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getCameraList()
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            data?.let {
                cameraList.postValue(it)
            }
        }
    }

    fun getCameraToken() {
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getCameraToken()
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            data?.data?.accessToken?.let {
                cameraToken.postValue(it)
            }
        }
    }
}