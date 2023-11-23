package com.shuangning.safeconstruction.data.net

import com.shuangning.safeconstruction.bean.request.AttendanceManagementListReq
import com.shuangning.safeconstruction.bean.request.AttendanceManagementSectionListReq
import com.shuangning.safeconstruction.bean.request.AttendancePunchReq
import com.shuangning.safeconstruction.bean.request.LoginReq
import com.shuangning.safeconstruction.bean.response.AttendanceManagementListResp
import com.shuangning.safeconstruction.bean.response.LoginResp
import com.shuangning.safeconstruction.bean.response.UserInfoResp
import com.shuangning.safeconstruction.utils2.net.HttpResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Chenwei on 2023/11/6.
 */
interface ApiService {
    @POST(LOGIN)
    suspend fun login(@Body data: LoginReq): HttpResult<LoginResp>?
    @GET(USER_INFO)
    suspend fun getUserInfo(@Query("token") token: String): HttpResult<UserInfoResp>?
    @POST(ATTENDANCE_MANAGEMENT_LIST)
    suspend fun getAttendanceManagementList(@Body data: AttendanceManagementListReq): HttpResult<AttendanceManagementListResp>?
    @POST(ATTENDANCE_MANAGEMENT_SECTION_LIST)
    suspend fun getAttendanceManagementSectionList(@Body data: AttendanceManagementSectionListReq): HttpResult<MutableList<String>>?

    @POST(ATTENDANCE_PUNCH)
    suspend fun attendancePunch(@Body data: AttendancePunchReq): HttpResult<Any>?
}