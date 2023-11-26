package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.request.GroupEducationListReq
import com.shuangning.safeconstruction.bean.response.GroupEducationDetailResp
import com.shuangning.safeconstruction.bean.response.GroupEducationListResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/11/25.
 */
class GroupEducationDetailViewModel: ViewModel() {
    private val _result: MutableLiveData<GroupEducationDetailResp?> = MutableLiveData()
    val result: LiveData<GroupEducationDetailResp?> = _result
    fun getData(trainTopic: String){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getGroupEducationDetail(trainTopic)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _result.postValue(data)
        }
    }
}