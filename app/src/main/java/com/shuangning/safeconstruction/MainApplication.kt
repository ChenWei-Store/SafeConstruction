package com.shuangning.safeconstruction

import com.shuangning.safeconstruction.base.BaseApplication
import com.shuangning.safeconstruction.data.net.OnNetCallbackImpl
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils2.BaiduLocation
import com.shuangning.safeconstruction.utils2.net.NetworkClient

/**
 * Created by Chenwei on 2023/10/7.
 */
class MainApplication: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        UserInfoManager.init()
        BaiduLocation.init()
        NetworkClient.client.init(BuildConfig.BASE_URL, OnNetCallbackImpl())
    }
}