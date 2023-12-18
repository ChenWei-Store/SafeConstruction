package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/18.
 */
class AddFineItemViewModel: ViewModel() {
    private val _id: MutableLiveData<Int?> = MutableLiveData()
    val id: LiveData<Int?> = _id
    fun getId(){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getMaxId()
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _id.postValue(data)
        }
    }

    fun commit(){
//        viewModelScope.launch {
//            val data = kotlin.runCatching {
//                NetworkClient.client.retrofit()
//                    .createService(ApiService::class.java)
////                    .commitAddFineItem()
//            }.onFailure {
//                MyLog.e(it.message.toString())
//            }.getOrNull()?.data
//            _personResult.postValue(data)
//        }
    }
}