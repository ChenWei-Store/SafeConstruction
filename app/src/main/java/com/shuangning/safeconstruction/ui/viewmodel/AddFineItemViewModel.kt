package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.request.AddFineItemReq
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/18.
 */
class AddFineItemViewModel : ViewModel() {
    private val _id: MutableLiveData<Int?> = MutableLiveData()
    val id: LiveData<Int?> = _id
    private val _result: MutableLiveData<Boolean?> = MutableLiveData()
    val result: LiveData<Boolean?> = _result
    fun getId() {
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getMaxId()
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()
            MyLog.d("id:$data")
            _id.postValue(data)
        }
    }

    fun commit(data: AddFineItemReq) {
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .commitAddFineItem(data)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()
            if (data != null){
                _result.postValue(data)
            }else{
                _result.postValue(false)
            }
        }
    }
}