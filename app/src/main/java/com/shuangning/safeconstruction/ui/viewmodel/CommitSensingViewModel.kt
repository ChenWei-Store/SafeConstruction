package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bin.david.form.data.form.IForm
import com.shuangning.safeconstruction.bean.request.CommitSensingReq
import com.shuangning.safeconstruction.bean.response.DeviceResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/23.
 */
class CommitSensingViewModel: ViewModel() {
    private val _result: MutableLiveData<Boolean> = MutableLiveData()
    val result: LiveData<Boolean> = _result
    fun commit(req: CommitSensingReq){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .commitSensing(req)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()
            if (data == null){
                _result.postValue(false)
            }else{
                _result.postValue(data!!)

            }
        }
    }
}