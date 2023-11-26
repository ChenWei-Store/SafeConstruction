package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/11/25.
 */
class SelectionViewModel: ViewModel() {
    private val _result: MutableLiveData<MutableList<String>?> = MutableLiveData()
    val result: LiveData<MutableList<String>?> = _result

    fun getData(){
        viewModelScope.launch {
            val data = getSection()
            _result.postValue(data)
        }
    }

    private suspend fun getSection(): MutableList<String>?{
        return kotlin.runCatching {
            val companyType = UserInfoManager.getUserInfo()?.companyType?:""
            NetworkClient.client.retrofit()
                .createService(ApiService::class.java)
                .getAttendanceManagementSectionList(companyType)
        }.onFailure {
            MyLog.e(it.message.toString())
        }.getOrNull()?.data
    }
}