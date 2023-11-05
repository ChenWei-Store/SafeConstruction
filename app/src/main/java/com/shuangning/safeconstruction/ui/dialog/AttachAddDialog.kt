package com.shuangning.safeconstruction.ui.dialog

import android.content.Context
import com.lxj.xpopup.core.AttachPopupView
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.extension.onClick
import com.shuangning.safeconstruction.manager.StartActivityManager

/**
 * Created by Chenwei on 2023/10/17.
 */
open class AttachAddDialog(val ctx: Context): AttachPopupView(ctx)  {
    override fun onCreate() {
        super.onCreate()
        R.id.view_left.onClick(this){
            dismiss()
            StartActivityManager.startToAddContent(ctx)
        }
        R.id.view_right.onClick(this){
            dismiss()
            StartActivityManager.startToProblemReport(ctx)
        }
    }
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_attach_add
    }
}