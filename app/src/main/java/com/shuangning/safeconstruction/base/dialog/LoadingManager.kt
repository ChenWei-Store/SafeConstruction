package com.shuangning.safeconstruction.base.dialog

import android.content.Context
import com.lxj.xpopup.core.BasePopupView
import com.shuangning.safeconstruction.utils2.MyLog
import com.shuangning.safeconstruction.manager.XPopCreateUtils

/**
 * Created by Chenwei on 2023/9/30.
 */
object LoadingManager {
    private var dialog: BasePopupView ?= null
    fun startLoading(ctx: Context){
        MyLog.d("startLoading")
        if (dialog == null){
            dialog = LoadingDialog(ctx)
        }
        dialog?.let {
            XPopCreateUtils.commonShow2(ctx, it)
        }
    }

    fun stopLoading(){
        MyLog.d("stopLoading")
        if (dialog?.isShow == true) {
            dialog?.dismiss()
        }
    }
}