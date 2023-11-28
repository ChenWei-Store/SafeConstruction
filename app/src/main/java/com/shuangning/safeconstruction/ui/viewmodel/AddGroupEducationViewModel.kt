package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.request.AddGroupEducationReq
import com.shuangning.safeconstruction.bean.response.GetTeamInfoDetailResp
import com.shuangning.safeconstruction.bean.response.Resp
import com.shuangning.safeconstruction.bean.response.UploadVideoItem
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by Chenwei on 2023/11/25.
 */
class AddGroupEducationViewModel : ViewModel() {
    private val _group: MutableLiveData<MutableList<String>?> = MutableLiveData()
    val group: LiveData<MutableList<String>?> = _group

    private val _TeamInfo: MutableLiveData<GetTeamInfoDetailResp?> = MutableLiveData()
    val teamInfo: LiveData<GetTeamInfoDetailResp?> = _TeamInfo

    private val _video: MutableLiveData<MutableList<UploadVideoItem>?> = MutableLiveData()
    val video: LiveData<MutableList<UploadVideoItem>?> = _video
    private val _resp: MutableLiveData<Resp?> = MutableLiveData()
    val resp: LiveData<Resp?> = _resp
    fun getGroup(section: String) {
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getGroupList(section)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _group.postValue(data)
        }
    }

    fun getTeamInfo(section: String, teamGroup: String){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getTeamInfo(section, teamGroup)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _TeamInfo.postValue(data)
        }
    }
    fun uploadVideo(file: File) {
        viewModelScope.launch {
            val requestBody = RequestBody.create("video/*".toMediaTypeOrNull(), file)
            // "file"这个 name 要和后端约定好
            val part = MultipartBody.Part.createFormData("files", file.name, requestBody)
            val result = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .uploadVideo(part)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _video.postValue(result)
        }
    }

    fun commit(data: AddGroupEducationReq){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .addGroupEducation(data)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _resp.postValue(data)
        }
    }
}