package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.request.AttendancePunchReq
import com.shuangning.safeconstruction.bean.response.GetProjectBaseInfoResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/11/23.
 */
class ClockInOutViewModel: ViewModel() {
    private val _result: MutableLiveData<Boolean> = MutableLiveData()
    val result: LiveData<Boolean?> = _result

    private val _data: MutableLiveData<GetProjectBaseInfoResp?> = MutableLiveData()
    val data: LiveData<GetProjectBaseInfoResp?> = _data

    fun getData(){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getProjectBaseInfo()
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _data.postValue(data)
        }
    }

    fun perform(longitude: String, latitude: String){
        viewModelScope.launch {
            val userNum = UserInfoManager.getUserInfo()?.userId?:""
            val req = AttendancePunchReq(userNum, longitude, latitude)
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .attendancePunch(req)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            data?.let {
                _result.postValue(it.result)
            }?:let {
                _result.postValue(false)
            }
        }
    }
}