package com.shuangning.safeconstruction.data.net

import com.shuangning.safeconstruction.bean.base.MessageEvent
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils.archcore.ArchTaskExecutor
import com.shuangning.safeconstruction.utils2.EventbusUtils
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.utils2.net.NetworkClient

/**
 * Created by Chenwei on 2023/11/6.
 */
class OnNetCallbackImpl: NetworkClient.OnNetCallback {
    override fun onCodeError(code: Int, msg: String) {
        MyLog.e("OnNetCallbackImpl onCodeError:$code-$msg")
        if (code == 500){
            showToast("请求失败")
        }else if (code == 400){
            showToast("非法请求参数")
        }else if (code == 401){
            showToast("token失效，请重新登录")
            EventbusUtils.post(EventCode.LOGIN)
        }else{
            showToast(msg)
        }
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