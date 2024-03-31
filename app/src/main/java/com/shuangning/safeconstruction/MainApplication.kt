package com.shuangning.safeconstruction

import com.shuangning.safeconstruction.base.BaseApplication
import com.shuangning.safeconstruction.data.net.OnNetCallbackImpl
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils.CrashHandler
import com.shuangning.safeconstruction.utils2.BaiduLocation
import com.shuangning.safeconstruction.utils2.net.NetworkClient
import com.videogo.openapi.EZOpenSDK

/**
 * Created by Chenwei on 2023/10/7.
 */
class MainApplication: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        CrashHandler.getInstance().init(this)
        UserInfoManager.init()
        NetworkClient.client.init(BuildConfig.BASE_URL, OnNetCallbackImpl())
        BaiduLocation.init()
        /** * sdk日志开关，正式发布需要去掉 */
        EZOpenSDK.showSDKLog(true)
        EZOpenSDK.initLib(this, "7e261fa8fc0d4112a8b32ad06b484a29")
    }
}