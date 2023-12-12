package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.request.CommitRoutineInspectionReq
import com.shuangning.safeconstruction.bean.request.GroupEducationListReq
import com.shuangning.safeconstruction.bean.response.UploadPhotoItem
import com.shuangning.safeconstruction.bean.response.UploadVideoItem
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by Chenwei on 2023/12/10.
 */
class ProblemReportViewModel : ViewModel() {
    private val _photos: MutableLiveData<MutableList<UploadPhotoItem>?> = MutableLiveData()
    val photos: LiveData<MutableList<UploadPhotoItem>?> = _photos

    private val _uploadResult: MutableLiveData<Boolean> = MutableLiveData()
    val uploadResult: LiveData<Boolean> = _uploadResult

    fun uploadPhotos(files: MutableList<String>) {
        viewModelScope.launch {
            val parts = mutableListOf<MultipartBody.Part>()
            files.forEach {
                val file = File(it)
                val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                // "file"这个 name 要和后端约定好
                val part = MultipartBody.Part.createFormData("files", file.name, requestBody)
                parts.add(part)
            }
            val result = mutableListOf<UploadPhotoItem>()
            if (parts.size > 4) {
                val data1 = upload(parts.subList(0, 4))
                val data2 = upload(parts.subList(4, parts.size))
                if (data1 != null) {
                    result.addAll(data1)
                }
                if (data2 != null){
                    result.addAll(data2)
                }
            } else {
                val data =upload(parts)
                if (data != null) {
                    result.addAll(data)
                }
            }
            _photos.postValue(result)
        }
    }

    suspend fun upload(parts: MutableList<MultipartBody.Part>): MutableList<UploadPhotoItem>? {
        val result = kotlin.runCatching {
            NetworkClient.client.retrofit()
                .createService(ApiService::class.java)
                .uploadPhotos(parts)
        }.onFailure {
            MyLog.e(it.message.toString())
        }.getOrNull()?.data
        return result
    }

    fun commit(data: CommitRoutineInspectionReq) {
        viewModelScope.launch {
            val token = UserInfoManager.getToken() ?: ""
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .commitRoutineInspection(data)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            if (data != null){
                _uploadResult.postValue(true)
            }else{
                _uploadResult.postValue(false)
            }
        }
    }
}