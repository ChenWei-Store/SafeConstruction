package com.shuangning.safeconstruction.manager

import android.content.Context
import com.shuangning.safeconstruction.ui.activity.AddContentActivity
import com.shuangning.safeconstruction.ui.activity.AddFineItemActivity
import com.shuangning.safeconstruction.ui.activity.AddFinesActivity
import com.shuangning.safeconstruction.ui.activity.AttendanceManagementDetailActivity
import com.shuangning.safeconstruction.ui.activity.AttendanceManagementListActivity
import com.shuangning.safeconstruction.ui.activity.ClockInOrOutActivity
import com.shuangning.safeconstruction.ui.activity.FinesDetailActivity
import com.shuangning.safeconstruction.ui.activity.FinesListActivity
import com.shuangning.safeconstruction.ui.activity.MainActivity
import com.shuangning.safeconstruction.ui.activity.ModifyPasswordActivity
import com.shuangning.safeconstruction.ui.activity.ProblemReportActivity
import com.shuangning.safeconstruction.ui.activity.RectificationAndReplyActivity
import com.shuangning.safeconstruction.ui.activity.QuestionOperatorActivity
import com.shuangning.safeconstruction.ui.activity.QuestionOperatorDetailActivity
import com.shuangning.safeconstruction.ui.activity.RoutineInspectionListActivity
import com.shuangning.safeconstruction.ui.activity.ScanQrcodeActivity
import com.shuangning.safeconstruction.ui.activity.SelectCauseActivity
import com.shuangning.safeconstruction.ui.activity.SelectCheckListActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersDetailsActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersStatusActivity
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/9/8.
 */
object StartActivityManager {

    fun startToMain(ctx: Context,){
        ActivityUtils.start(ctx, MainActivity::class.java)
    }

    fun startToTakePhotosOfDangers(ctx: Context,){
        ActivityUtils.start(ctx, TakePhotosOfDangersActivity::class.java)
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

    fun startToFinesList(ctx: Context,){
        ActivityUtils.start(ctx, FinesListActivity::class.java)
    }

    fun startToFinesDetail(ctx: Context,){
        ActivityUtils.start(ctx, FinesDetailActivity::class.java)
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
    fun startAttendanceManagementDetail(ctx: Context,){
        ActivityUtils.start(ctx, AttendanceManagementDetailActivity::class.java)
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

    fun startToSelectCause(ctx: Context,){
        ActivityUtils.start(ctx, SelectCauseActivity::class.java)
    }

    fun startToClockInOut(ctx: Context,){
        ActivityUtils.start(ctx, ClockInOrOutActivity::class.java)
    }
    fun startSelectCheckList(ctx: Context,){
        ActivityUtils.start(ctx, SelectCheckListActivity::class.java)
    }
}