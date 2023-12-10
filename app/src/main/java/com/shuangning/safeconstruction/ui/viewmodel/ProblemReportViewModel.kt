package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
 * Created by Chenwei on 2023/12/10.
 */
class ProblemReportViewModel: ViewModel() {
    private val _photos: MutableLiveData<MutableList<UploadVideoItem>?> = MutableLiveData()
    val photos: LiveData<MutableList<UploadVideoItem>?> = _photos
    fun uploadPhotos(files: MutableList<String>){
        viewModelScope.launch {
            val parts = mutableListOf<MultipartBody.Part>()
            files.forEach {
                val file = File(it)
                val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                // "file"这个 name 要和后端约定好
                val part = MultipartBody.Part.createFormData("files", file.name, requestBody)
                parts.add(part)
            }

            val result = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .uploadPhotos(parts)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _photos.postValue(result)
        }
    }

    fun commit(){

    }
}