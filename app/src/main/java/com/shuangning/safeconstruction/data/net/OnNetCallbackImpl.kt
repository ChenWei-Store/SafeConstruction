package com.shuangning.safeconstruction.data.net

import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils.archcore.ArchTaskExecutor
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient

/**
 * Created by Chenwei on 2023/11/6.
 */
class OnNetCallbackImpl: NetworkClient.OnNetCallback {
    override fun onCodeError(code: Int, msg: String) {
        MyLog.e("OnNetCallbackImpl onCodeError:$code-$msg")
        showToast(msg)
//        if (code == 1004){
//            EventbusUtils.post(EventCode.LOGIN, null)
//            showToast("token失效")
//        }else{
//
//        }
    }

    override fun onOtherError(throwable: Throwable) {
        MyLog.e("OnNetCallbackImpl onOtherError:${throwable.message}")
        showToast("请求失败")
    }

    override fun getToken(): String {
        return UserInfoManager.getToken()?:""
    }

    private fun showToast(msg: String){
        ArchTaskExecutor.getInstance().executeOnMainThread {
            ToastUtil.showCustomToast(msg)
        }
    }
}