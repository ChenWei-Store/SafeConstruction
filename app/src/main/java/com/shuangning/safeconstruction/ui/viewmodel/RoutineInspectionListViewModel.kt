package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.response.RoutineInspectionListResp
import com.shuangning.safeconstruction.bean.response.UserBaseInfoResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/12/8.
 */
class RoutineInspectionListViewModel : ViewModel() {
    private val _result: MutableLiveData<RoutineInspectionListWrapper?> = MutableLiveData()
    val result: LiveData<RoutineInspectionListWrapper?> = _result

    private val _data: MutableLiveData<RoutineInspectionListResp?> = MutableLiveData()
    val data: LiveData<RoutineInspectionListResp?> = _data
    fun getData(biaoduan: String, pageNo: Int, pageSize: Int) {
        val companyType = UserInfoManager.getUserInfo()?.companyType ?: ""
        viewModelScope.launch {
            val data = kotlin.runCatching {
                val deferred1 = async {
                    NetworkClient.client.retrofit()
                        .createService(ApiService::class.java)
                        .getRoutineInspectionList(biaoduan, pageNo, pageSize)
                }
                val deferred2 = async {
                    NetworkClient.client.retrofit()
                        .createService(ApiService::class.java)
                        .getAttendanceManagementSectionList(companyType)
                }
                val result1 = deferred1.await()?.data
                val result2 = deferred2.await()?.data
                val wrapper = RoutineInspectionListWrapper(result1, result2)
                wrapper
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()
            _result.postValue(data)
        }
    }

    fun onlyGetList(biaoduan: String, pageNo: Int, pageSize: Int) {
        viewModelScope.launch {
            val data = kotlin.runCatching {
                NetworkClient.client.retrofit()
                    .createService(ApiService::class.java)
                    .getRoutineInspectionList(biaoduan, pageNo, pageSize)
            }.onFailure {
                MyLog.e(it.message.toString())
            }.getOrNull()?.data
            _data.postValue(data)
        }
    }

    data class RoutineInspectionListWrapper(
        val routineInspectionList: RoutineInspectionListResp?,
        val sections: MutableList<String>?,
    )
}
