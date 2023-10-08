package com.shuangning.safeconstruction.manager

import android.content.Context
import com.shuangning.safeconstruction.ui.activity.MainActivity
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/9/8.
 */
object StartActivityManager {

    fun startToMain(ctx: Context,){
        ActivityUtils.start(ctx, MainActivity::class.java)
    }
}