package com.shuangning.safeconstruction.base

import android.app.Application

/**
 * Created by Chenwei on 2023/9/9.
 */
open class BaseApplication: Application() {
    init {
        appContext = this
    }
    companion object{
        lateinit var appContext: Application
    }
}