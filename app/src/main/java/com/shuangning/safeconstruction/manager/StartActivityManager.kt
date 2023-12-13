package com.shuangning.safeconstruction.manager

import android.content.Context
import androidx.activity.ComponentActivity
import com.shuangning.safeconstruction.bean.response.CameraListResp
import com.shuangning.safeconstruction.ui.activity.AddContentActivity
import com.shuangning.safeconstruction.ui.activity.AddFineItemActivity
import com.shuangning.safeconstruction.ui.activity.AddFinesActivity
import com.shuangning.safeconstruction.ui.activity.AttendanceManagementListActivity
import com.shuangning.safeconstruction.ui.activity.CameraDetailActivity
import com.shuangning.safeconstruction.ui.activity.ClockInOrOutActivity
import com.shuangning.safeconstruction.ui.activity.LoginActivity
import com.shuangning.safeconstruction.ui.activity.MainActivity
import com.shuangning.safeconstruction.ui.activity.ModifyPasswordActivity
import com.shuangning.safeconstruction.ui.activity.ProblemReportActivity
import com.shuangning.safeconstruction.ui.activity.RectificationAndReplyActivity
import com.shuangning.safeconstruction.ui.activity.QuestionOperatorActivity
import com.shuangning.safeconstruction.ui.activity.QuestionOperatorDetailActivity
import com.shuangning.safeconstruction.ui.activity.RoutineInspectionListActivity
import com.shuangning.safeconstruction.ui.activity.ScanQrcodeActivity
import com.shuangning.safeconstruction.ui.activity.MultiSelectActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersDetailsActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersStatusActivity
import com.shuangning.safeconstruction.ui.activity.CameraListActivity
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/9/8.
 */
const val FROM_WHERE = "From_where"
const val CAMERA_INFO = "camera_info"
const val NONE = 0
const val FROM_TAKE_PHOTO_OF_DANAGE = 1
const val FROM_GROUP_EDUCATION = 2
const val FROM_PROBLEM_REPORT = 3
object StartActivityManager {
    fun startToLogin(ctx: Context,){
        ActivityUtils.start(ctx, LoginActivity::class.java)
    }
    fun startToMain(ctx: Context,){
        ActivityUtils.start(ctx, MainActivity::class.java)
    }

    fun startToTakePhotosOfDangers(ctx: Context, fromWhere: Int){
        ActivityUtils.start(ctx, TakePhotosOfDangersActivity::class.java){
            putExtra(FROM_WHERE, fromWhere)
        }
    }
    fun startToTakePhotosOfDangersStatus(ctx: Context,){
        ActivityUtils.start(ctx, TakePhotosOfDangersStatusActivity::class.java)
    }

    fun startToTakePhotosOfDangersDetail(ctx: Context,){
        ActivityUtils.start(ctx, TakePhotosOfDangersDetailsActivity::class.java)
    }

    fun startToRectificationAndReply(ctx: Context,){
        ActivityUtils.start(ctx, RectificationAndReplyActivity::class.java)
    }

    fun startToQuestionOperator(ctx: Context,){
        ActivityUtils.start(ctx, QuestionOperatorActivity::class.java)
    }


    fun startToQuestionOperatorDetail(ctx: Context,){
        ActivityUtils.start(ctx, QuestionOperatorDetailActivity::class.java)
    }

    fun startToAddFine(ctx: Context,){
        ActivityUtils.start(ctx, AddFinesActivity::class.java)
    }

    fun startToAddFineItem(ctx: Context,){
        ActivityUtils.start(ctx, AddFineItemActivity::class.java)
    }

    fun startToRoutineInspectionList(ctx: Context,){
        ActivityUtils.start(ctx, RoutineInspectionListActivity::class.java)
    }

    fun startToModifyPwdList(ctx: Context,){
        ActivityUtils.start(ctx, ModifyPasswordActivity::class.java)
    }

    fun startAttendanceManagement(ctx: Context,){
        ActivityUtils.start(ctx, AttendanceManagementListActivity::class.java)
    }

    fun startToScanQrcode(ctx: Context,){
        ActivityUtils.start(ctx, ScanQrcodeActivity::class.java)
    }

    fun startToAddContent(ctx: Context,){
        ActivityUtils.start(ctx, AddContentActivity::class.java)
    }

    fun startToProblemReport(ctx: Context,){
        ActivityUtils.start(ctx, ProblemReportActivity::class.java)
    }

    fun startToMultiSelectForResult(ctx: ComponentActivity, fromWhere: Int){
        ActivityUtils.start(ctx, MultiSelectActivity::class.java){
            putExtra(FROM_WHERE, fromWhere)
        }
    }

    fun startToClockInOut(ctx: Context,){
        ActivityUtils.start(ctx, ClockInOrOutActivity::class.java)
    }

    fun startToVideoSurveillance(ctx:Context){
        ActivityUtils.start(ctx, CameraListActivity::class.java)
    }

    fun startToCameraDetail(ctx:Context,cameraInfo: CameraListResp){
        ActivityUtils.start(ctx, CameraDetailActivity::class.java){
            putExtra(CAMERA_INFO,cameraInfo)
        }
    }
}