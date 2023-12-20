package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.request.AddFineReq
import com.shuangning.safeconstruction.bean.response.ConstructionTeamResp
import com.shuangning.safeconstruction.bean.response.PersonResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/18.
 */
class AddFineViewModel: ViewModel() {
    private val _sectionResult: MutableLiveData<MutableList<String>?> = MutableLiveData()
    val sectionResult: LiveData<MutableList<String>?> = _sectionResult

    private val _result: MutableLiveData<Boolean> = MutableLiveData()
    val result: LiveData<Boolean> = _result

    private suspend fun getSection(): MutableList<String>? {
        return kotlin.runCatching {
            val companyType = UserInfoManager.getUserInfo()?.companyType ?: ""
            NetworkClient.client.retrofit()
                .createService(ApiService::class.java)
                .getAttendanceManagementSectionList(companyType)
        }.onFailure {
            MyLog.e(it.message.toString())
        }.getOrNull()?.data
    }

    fun getData() {
        viewModelScope.launch {
            val section = getSection()
            _sectionResult.postValue(section)
        }
    }

    fun commit(data: AddFineReq){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .commitAddFine(data)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()
            if (data == null || data <= 0){
                _result.postValue(false)
            }else{
                _result.postValue(true)
            }
        }
    }

}