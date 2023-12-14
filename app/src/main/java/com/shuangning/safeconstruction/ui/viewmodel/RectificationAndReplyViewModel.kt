package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.response.RectificationAndReplyItem
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/13.
 */
class RectificationAndReplyViewModel: ViewModel() {
    private val _result: MutableLiveData<MutableList<RectificationAndReplyItem>> = MutableLiveData()
    val result: LiveData<MutableList<RectificationAndReplyItem>> = _result
    fun getData(){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getRectificationAndReplyList()
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            data?.result?.let {
                _result.postValue(it)
            }?:let {
                _result.postValue(mutableListOf())
            }
        }
    }
}