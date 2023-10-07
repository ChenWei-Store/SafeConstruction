package com.shuangning.safeconstruction.base.dialog

import android.content.Context
import com.lxj.xpopup.core.CenterPopupView
import com.shuangning.safeconstruction.R

/**
 * Created by Chenwei on 2023/9/30.
 */
class LoadingDialog(ctx: Context): CenterPopupView(ctx) {
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_loading
    }
}