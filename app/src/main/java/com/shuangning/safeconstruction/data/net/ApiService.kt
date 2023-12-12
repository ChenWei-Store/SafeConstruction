package com.shuangning.safeconstruction.data.net

import com.shuangning.safeconstruction.bean.request.AddGroupEducationReq
import com.shuangning.safeconstruction.bean.request.AttendanceManagementListReq
import com.shuangning.safeconstruction.bean.request.AttendancePunchReq
import com.shuangning.safeconstruction.bean.request.CommitRoutineInspectionReq
import com.shuangning.safeconstruction.bean.request.GroupEducationListReq
import com.shuangning.safeconstruction.bean.request.JoinParticipantReq
import com.shuangning.safeconstruction.bean.request.LoginReq
import com.shuangning.safeconstruction.bean.response.AttendanceManagementListResp
import com.shuangning.safeconstruction.bean.response.FinesDetailResp
import com.shuangning.safeconstruction.bean.response.FinesListItem
import com.shuangning.safeconstruction.bean.response.GetProjectBaseInfoResp
import com.shuangning.safeconstruction.bean.response.Resp
import com.shuangning.safeconstruction.bean.response.GetTeamInfoDetailResp
import com.shuangning.safeconstruction.bean.response.GroupEducationDetailResp
import com.shuangning.safeconstruction.bean.response.GroupEducationListResp
import com.shuangning.safeconstruction.bean.response.JoinParticipantResp
import com.shuangning.safeconstruction.bean.response.LoginResp
import com.shuangning.safeconstruction.bean.response.NewsListResp
import com.shuangning.safeconstruction.bean.response.QuestionOperatorResp
import com.shuangning.safeconstruction.bean.response.RoutineInspectionListResp
import com.shuangning.safeconstruction.bean.response.UploadPhotoItem
import com.shuangning.safeconstruction.bean.response.UploadVideoItem
import com.shuangning.safeconstruction.bean.response.UserBaseInfoResp
import com.shuangning.safeconstruction.bean.response.UserInfoResp
import com.shuangning.safeconstruction.utils2.net.HttpResult
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    @FormUrlEncoded
    @POST(ATTENDANCE_MANAGEMENT_SECTION_LIST)
    suspend fun getAttendanceManagementSectionList(@Field("companyType") companyType: String): HttpResult<MutableList<String>>?
    @POST(ATTENDANCE_PUNCH)
    suspend fun attendancePunch(@Body data: AttendancePunchReq): HttpResult<Resp>?
    @POST(GROUP_EDUCATION_LIST)
    suspend fun getGroupEducationList(@Body data: GroupEducationListReq): HttpResult<GroupEducationListResp>?
    @POST(JOIN_PARTICIPANT)
    suspend fun getoinParticipant(@Body data: JoinParticipantReq): HttpResult<JoinParticipantResp>?
    @Multipart
    @POST(UPLOAD_VIDEO)
    suspend fun uploadVideo(@Part file: MultipartBody.Part): HttpResult<MutableList<UploadVideoItem>>?
    @POST(ADD_GROUP_EDUCATION)
    suspend fun addGroupEducation(@Body data: AddGroupEducationReq): HttpResult<Resp>?
    @FormUrlEncoded
    @POST(GROUP_DEUCATION_DETAIL)
    suspend fun getGroupEducationDetail(@Field("trainTopic") trainTopic: String): HttpResult<GroupEducationDetailResp>?
    @FormUrlEncoded
    @POST(GROUP_LIST)
    suspend fun getGroupList(@Field("section") section: String): HttpResult<MutableList<String>>?
    @GET(TEAM_INFO)
    suspend fun getTeamInfo(@Field("section") section: String, @Field("teamGroup") teamGroup: String): HttpResult<GetTeamInfoDetailResp>?
    @GET(PROJECT_BASE_INFO)
    suspend fun getProjectBaseInfo(): HttpResult<GetProjectBaseInfoResp>?
    @FormUrlEncoded
    @POST(USER_BASE_INFO)
    suspend fun getUserBaseInfo(@Field("userId") userId: Int): HttpResult<UserBaseInfoResp>?
    @GET(GET_NEWS_INFO)
    suspend fun getNewsInfo(): HttpResult<NewsListResp>?
    @FormUrlEncoded
    @POST(ROUTINE_INSPECTION_LIST)
    suspend fun getRoutineInspectionList(@Field("biaoduan") biaoduan: String, @Field("pageNo") pageNo: Int, @Field("pageSize") pageSize: Int): HttpResult<RoutineInspectionListResp>?
    @FormUrlEncoded
    @POST(QUESTION_OPERATOR)
    suspend fun getQuestionOperator(@Field("id") id: String): HttpResult<QuestionOperatorResp>?
    @POST(COMMIT_ROUTINE_INSPECTION)
    suspend fun commitRoutineInspection(@Body data: CommitRoutineInspectionReq): HttpResult<Any>?
    @Multipart
    @POST(UPLOAD_VIDEO)
    suspend fun uploadPhotos(@Part files: MutableList<MultipartBody.Part>): HttpResult<MutableList<UploadPhotoItem>>?
    @FormUrlEncoded
    @POST(FINES_LIST)
    suspend fun getFinesList(@Field("checkOutNo") checkOutNo: String): HttpResult<MutableList<FinesListItem>>?
    @FormUrlEncoded
    @POST(FINES_DETAIL)
    suspend fun getFineDetail(@Field("id") id: String): HttpResult<FinesDetailResp>?
}