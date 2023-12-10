package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.response.QuestionOperatorResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/10.
 */
class QuestionOperatorViewModel: ViewModel() {
    private val _result: MutableLiveData<QuestionOperatorResp?> = MutableLiveData()
    val result: LiveData<QuestionOperatorResp?> = _result
    fun getData(id: String){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getQuestionOperator(id)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _result.postValue(data)
        }
    }

}