package com.shuangning.safeconstruction.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuangning.safeconstruction.bean.other.DepartmentBean
import com.shuangning.safeconstruction.bean.request.AttendanceManagementListReq
import com.shuangning.safeconstruction.bean.response.AttendanceManagementListResp
import com.shuangning.safeconstruction.data.net.ApiService
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import kotlinx.coroutines.launch

/**
 * Created by Chenwei on 2023/11/23.
 */
class AttendanceManagementDetailViewModel: ViewModel() {
    private val departmentBeans = mutableListOf<DepartmentBean>()
    private val _result: MutableLiveData<AttendanceManagementListResp?> = MutableLiveData()
    val result: LiveData<AttendanceManagementListResp?> = _result
    private val _selection: MutableLiveData<String?> = MutableLiveData()
    val selection: LiveData<String?> = _selection
    fun getSectionAndList(personType: Int, day: String){
        viewModelScope.launch {
           val selectionData = getSection()
            selectionData?.let {
                it.forEachIndexed { index, str ->
                    if (index == 0){
                        departmentBeans.add(DepartmentBean(str,true))
                    }else{
                        departmentBeans.add(DepartmentBean(str))
                    }
                }
                _selection.postValue(it[0])
                val data = getList(it[0], personType, day)
                _result.postValue(data)
            }?:let {
                _result.postValue(null)
            }
        }
    }
    fun refreshData(selection: String, personType: Int, day: String){
        viewModelScope.launch {
            val data = getList(selection, personType, day)
            _result.postValue(data)
        }
    }
    private suspend fun getSection(): MutableList<String>?{
        return kotlin.runCatching {
            val companyType = UserInfoManager.getUserInfo()?.companyType?:""
            NetworkClient.client.retrofit()
                .createService(ApiService::class.java)
                .getAttendanceManagementSectionList(companyType)
        }.onFailure {
            MyLog.e(it.message.toString())
        }.getOrNull()?.data
    }
    private suspend fun getList(selection: String, personType: Int, day: String): AttendanceManagementListResp? {
        return kotlin.runCatching {
            val req = AttendanceManagementListReq(selection, personType, day)
            NetworkClient.client.retrofit()
                .createService(ApiService::class.java)
                .getAttendanceManagementList(req)
        }.onFailure {
            MyLog.e(it.message.toString())
        }.getOrNull()?.data
    }

    fun getDepartmentData(): MutableList<DepartmentBean>{
        return departmentBeans
    }
}