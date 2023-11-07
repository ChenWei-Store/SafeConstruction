package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.base.BaseViewModel
import com.shuangning.safeconstruction.bean.request.LoginReq
import com.shuangning.safeconstruction.bean.response.LoginResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/11/6.
 */
class LoginViewModel: BaseViewModel() {
    private val _loginResult: MutableLiveData<LoginResp?> = MutableLiveData()
    val loginResult: LiveData<LoginResp?> = _loginResult
    fun login(userName: String, pwd: String){
        viewModelScope.launch {
            val req = LoginReq(userName, pwd)
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .login(req)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _loginResult.postValue(data)
        }
    }

}