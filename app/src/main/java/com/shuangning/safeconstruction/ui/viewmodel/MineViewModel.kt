package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.base.BaseViewModel
import com.shuangning.safeconstruction.bean.response.UserInfoResp
import com.shuangning.safeconstruction.bean.response.VersionResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.JsonUtils
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/11/8.
 */
class MineViewModel : BaseViewModel() {
    private val _userInfo: MutableLiveData<UserInfoResp?> = MutableLiveData()
    val userInfo: LiveData<UserInfoResp?> = _userInfo

    private val _version: MutableLiveData<VersionResp?> = MutableLiveData()
    val version: LiveData<VersionResp?> = _version

    fun getUserInfo() {
        viewModelScope.launch {
            val token = UserInfoManager.getToken() ?: ""
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getUserInfo(token)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            data?.token?.let {
                UserInfoManager.setToken(it)
            }
            data?.let {
                UserInfoManager.updateUserInfo(it)
            }
            _userInfo.postValue(data)
        }
    }

    fun getVersion() {
        viewModelScope.launch {
            val data = kotlin.runCatching {
                val result = NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getVersion()
                result
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()
            data?.data?.let {
                _version.postValue(it)
            }?:let {
                 _version.postValue(null)
            }
        }
    }

}