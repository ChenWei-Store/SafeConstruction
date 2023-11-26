package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.request.GroupEducationListReq
import com.shuangning.safeconstruction.bean.response.GroupEducationListResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/11/25.
 */
class GroupEducationListViewModel: ViewModel() {
    private val _result: MutableLiveData<GroupEducationListResp?> = MutableLiveData()
    val result: LiveData<GroupEducationListResp?> = _result
    fun getData(selection: String, pageNo: Int, trainTopic: String = "", beginTime: String = "",
                endTime: String = "", buildStatus: String = ""){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getGroupEducationList(GroupEducationListReq(selection, pageNo, trainTopic, beginTime,
                        endTime, buildStatus))
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _result.postValue(data)
        }
    }
}