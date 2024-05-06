package com.shuangning.safeconstruction.ui.dialog

import android.content.Context
import android.view.View
import com.lxj.xpopup.core.CenterPopupView
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.extension.onClick
import com.shuangning.safeconstruction.extension.setText
import com.shuangning.safeconstruction.extension.setVisible

/**
 * Created by Chenwei on 2023/10/23.
 */
class CommonConfirmDialog(ctx: Context, val title: String, val content: String?):
    CenterPopupView(ctx) {
    override fun onCreate() {
        super.onCreate()
        R.id.tv_title.setText(this, title)
        if (content.isNullOrBlank()){
            R.id.tv_content.setVisible(this, View.GONE)
        }else{
            R.id.tv_content.setText(this, content)
        }

        R.id.ll_confirm.onClick(this){
            dismiss()
        }
    }
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_common_confirm
    }
}