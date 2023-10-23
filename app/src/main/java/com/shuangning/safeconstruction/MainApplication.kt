package com.shuangning.safeconstruction

import com.shuangning.safeconstruction.base.BaseApplication
import com.shuangning.safeconstruction.manager.UserInfoManager

/**
 * Created by Chenwei on 2023/10/7.
 */
class MainApplication: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        UserInfoManager.init()
    }
}