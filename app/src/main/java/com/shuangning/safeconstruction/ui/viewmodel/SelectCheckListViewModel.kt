package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.response.CheckListItem
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/14.
 */
class SelectCheckListViewModel: ViewModel() {
    private val _result: MutableLiveData<MutableList<CheckListItem>?> = MutableLiveData()
    val result: LiveData<MutableList<CheckListItem>?> = _result
    fun getCheckList(){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getCheckList()
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _result.postValue(data)
        }
    }

}