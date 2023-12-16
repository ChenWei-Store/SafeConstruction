package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.response.FinesDetailResp
import com.shuangning.safeconstruction.bean.response.RectificationAndReplyDetailResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/16.
 */
class RectifucationAndReplyDetailViewModel: ViewModel() {
    private val _data: MutableLiveData<RectificationAndReplyDetailResp?> = MutableLiveData()
    val data: LiveData<RectificationAndReplyDetailResp?> = _data
    fun getData(id: String, flowInstanceId: Int, taskInstanceId: Int){
        viewModelScope.launch {
            viewModelScope.launch {
                val data = kotlin.runCatching {
                    NetworkClient.client.retrofit()
                        .createService(ApiService::class.java)
                        .getRectificationAndReplyDetail(id, flowInstanceId, taskInstanceId)
                }.onFailure {
                    MyLog.e(it.message.toString())
                }.getOrNull()?.data
                _data.postValue(data)
            }
        }
    }
}