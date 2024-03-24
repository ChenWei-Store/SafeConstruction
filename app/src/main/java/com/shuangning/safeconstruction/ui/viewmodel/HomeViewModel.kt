package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/12/3.
 */
class HomeViewModel : ViewModel() {
    private val _data: MutableLiveData<MutableList<String>?> = MutableLiveData()
    val data: LiveData<MutableList<String>?> = _data
    fun getData() {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO){
                getNewsInfo()
            }
            val result = withContext(Dispatchers.IO){
                getProjectName()
            }
        }
    }

    private suspend fun getNewsInfo(): MutableList<String>{
         val data = kotlin.runCatching {
            NetworkClient.client.retrofit()
                .createService(ApiService::class.java)
                .getNewsInfo()
        }.onFailure {
            MyLog.e(it.message.toString())
        }.getOrNull()?.data
        val result = mutableListOf<String>()
        data?.newsInfoList?.let {
            MyLog.d("getNewsInfo url")
            it.forEach {
                val url = getUrl(it)
                MyLog.d("url:$url")
                result.add(url)
            }
        }
        return result
    }

    private suspend fun getProjectName(): String{
        return kotlin.runCatching {
            NetworkClient.client.retrofit()
                .createService(ApiService::class.java)
                .getProjectName()
        }.onFailure {
            MyLog.e(it.message.toString())
        }.getOrNull()?: ""
    }
    private fun getUrl(json: String): String {
        if (json.isEmpty()){
            return ""
        }
        val jsonObject = JSONObject(json)
        val ja = jsonObject.optJSONArray("attach")
        return ja?.optJSONObject(0)?.optString("url") ?: ""
    }
}