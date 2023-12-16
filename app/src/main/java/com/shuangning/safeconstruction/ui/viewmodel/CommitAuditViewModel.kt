package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.base.BaseViewModel
import com.shuangning.safeconstruction.bean.request.CommitApprovalRejectReq
import com.shuangning.safeconstruction.bean.request.CommitAuditReq
import com.shuangning.safeconstruction.bean.request.CommitRectifiedReq
import com.shuangning.safeconstruction.bean.response.UploadPhotoItem
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by Chenwei on 2023/12/16.
 */
class CommitAuditViewModel: BaseViewModel() {

    private val _uploadResult: MutableLiveData<Boolean> = MutableLiveData()
    val uploadResult: LiveData<Boolean> = _uploadResult

    fun commitAduit(data: CommitAuditReq){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .commitAduit(data)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            if (data != null) {
                _uploadResult.postValue(true)
            } else {
                _uploadResult.postValue(false)
            }
        }
    }

    fun commitApproval(data: CommitApprovalRejectReq){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .commitApproval(data)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            if (data != null) {
                _uploadResult.postValue(true)
            } else {
                _uploadResult.postValue(false)
            }
        }
    }

    fun commitReject(data: CommitApprovalRejectReq){
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .commitReject(data)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            if (data != null) {
                _uploadResult.postValue(true)
            } else {
                _uploadResult.postValue(false)
            }
        }
    }
}