package com.shuangning.safeconstruction.manager

import android.content.Context
import com.shuangning.safeconstruction.ui.activity.FinesDetailActivity
import com.shuangning.safeconstruction.ui.activity.FinesListActivity
import com.shuangning.safeconstruction.ui.activity.MainActivity
import com.shuangning.safeconstruction.ui.activity.RectificationAndReplyActivity
import com.shuangning.safeconstruction.ui.activity.RectificationAndReplyDetailActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersDetailsActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersStatusActivity
import com.shuangning.safeconstruction.ui.adapter.TakePhotosOfDangersStatusAdapter
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

    fun startToRectificationAndReplyDetail(ctx: Context,){
        ActivityUtils.start(ctx, RectificationAndReplyDetailActivity::class.java)
    }

    fun startToFinesList(ctx: Context,){
        ActivityUtils.start(ctx, FinesListActivity::class.java)
    }

    fun startToFinesDetail(ctx: Context,){
        ActivityUtils.start(ctx, FinesDetailActivity::class.java)
    }
}