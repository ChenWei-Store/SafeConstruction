package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.request.AttendancePunchReq
import com.shuangning.safeconstruction.bean.response.UserBaseInfoResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/2.
 */
class QrcodeResultViewModel: ViewModel() {
    private val _result: MutableLiveData<UserBaseInfoResp?> = MutableLiveData()
    val result: LiveData<UserBaseInfoResp?> = _result
    fun getData(userId: Int){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getUserBaseInfo(userId)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _result.postValue(data)
        }
    }
}