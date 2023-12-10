package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.response.FinesDetailResp
import com.shuangning.safeconstruction.bean.response.FinesListItem
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/10.
 */
class FinesDetailViewModel: ViewModel() {
    private val _data: MutableLiveData<FinesDetailResp?> = MutableLiveData()
    val data: LiveData<FinesDetailResp?> = _data
    fun getData(id: String){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getFineDetail(id)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _data.postValue(data)
        }
    }
}