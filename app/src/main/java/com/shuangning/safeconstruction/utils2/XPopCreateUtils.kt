package com.shuangning.safeconstruction.utils2

import android.content.Context
import android.view.View
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.other.SelectTypeBean
import com.shuangning.safeconstruction.ui.dialog.SelectTypeDialog
import com.shuangning.safeconstruction.utils.ScreenUtil

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

    fun showSelectTypeView(ctx: Context, attachView: View, data: MutableList<SelectTypeBean>): BasePopupView{
        return XPopup.Builder(ctx)
            .isDestroyOnDismiss(false)
            .popupWidth(ScreenUtil.getScreenWidth())
            .popupHeight((ScreenUtil.getScreenHeight() * 5.5 / 10.0f ).toInt())
            .enableDrag(false)
            .isThreeDrag(false)
            .atView(attachView)
            .asCustom(SelectTypeDialog(ctx, data))
            .show()
    }
}