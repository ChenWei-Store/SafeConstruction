package com.shuangning.safeconstruction.utils2

import android.content.Context
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

/**
 * Created by Chenwei on 2023/9/30.
 */
object XPopCreateUtils {
    fun commonShow(ctx: Context, basePopupView: BasePopupView): BasePopupView {
        return XPopup.Builder(ctx)
            .isDestroyOnDismiss(true)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .enableDrag(false)
            .isThreeDrag(false)
            .asCustom(basePopupView)
            .show()
    }

    fun commonShow2(ctx: Context, basePopupView: BasePopupView): BasePopupView {
        return XPopup.Builder(ctx)
            .isDestroyOnDismiss(false)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .enableDrag(false)
            .isThreeDrag(false)
            .asCustom(basePopupView)
            .show()
    }
}