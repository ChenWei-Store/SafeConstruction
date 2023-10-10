package com.shuangning.safeconstruction.manager

import android.content.Context
import com.shuangning.safeconstruction.ui.activity.MainActivity
import com.shuangning.safeconstruction.ui.activity.TakePhotosOfDangersActivity
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
}