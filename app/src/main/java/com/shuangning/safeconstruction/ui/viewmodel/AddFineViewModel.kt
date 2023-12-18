package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _constructionTeamResult: MutableLiveData<MutableList<ConstructionTeamResp>?> = MutableLiveData()
    val constructionTeamResult: LiveData<MutableList<ConstructionTeamResp>?> = _constructionTeamResult

    private val _personResult: MutableLiveData<PersonResp?> = MutableLiveData()
    val personResult: LiveData<PersonResp?> = _personResult
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

    private suspend fun getConstructionTeam(): MutableList<ConstructionTeamResp>? {
        return kotlin.runCatching {
            NetworkClient.client.retrofit()
                .createService(ApiService::class.java)
                .getConstructionTeam(true)
        }.onFailure {
            MyLog.e(it.message.toString())
        }.getOrNull()?.data
    }


    fun getData() {
        viewModelScope.launch {
            val section = getSection()
            val constructionTeam = getConstructionTeam()
            _sectionResult.postValue(section)
            _constructionTeamResult.postValue(constructionTeam)
        }
    }

    fun getPerson(id: Int){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getPerson(id.toString())
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _personResult.postValue(data)
        }
    }

}