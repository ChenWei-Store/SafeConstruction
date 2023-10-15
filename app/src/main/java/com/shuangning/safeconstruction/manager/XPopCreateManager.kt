package com.shuangning.safeconstruction.manager

import android.content.Context
import android.view.View
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.shuangning.safeconstruction.bean.other.SelectTypeBean
import com.shuangning.safeconstruction.ui.dialog.SelectTypeDialog
import com.shuangning.safeconstruction.utils.ScreenUtil
import java.security.AccessController.getContext
import java.util.Date


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
            .isDestroyOnDismiss(true)
            .popupWidth(ScreenUtil.getScreenWidth())
            .popupHeight((ScreenUtil.getScreenHeight() * 5.5 / 10.0f ).toInt())
            .enableDrag(false)
            .isThreeDrag(false)
            .atView(attachView)
            .asCustom(SelectTypeDialog(ctx, data))
            .show()
    }

    fun showFinesListDialog(ctx: Context, data: Array<String>,
                          block: (index: Int, text: String)->Unit){
        XPopup.Builder(ctx)
            .isDestroyOnDismiss(true)
            .dismissOnBackPressed(true)
            .dismissOnTouchOutside(true)
            .asCenterList("请选择", data) {
                    position, text ->
                block(position, text)
            }
            .show()
    }

    fun showYearMonthDialog(ctx: Context){
        val dialog = TimePickerPopup(ctx)
            .setMode(TimePickerPopup.Mode.YM)
            .setTimePickerListener(object: TimePickerListener {
                override fun onTimeChanged(date: Date?) {
                }

                override fun onTimeConfirm(date: Date?, view: View?) {
                }

                override fun onCancel() {
                }

            })
        XPopup.Builder(ctx)
            .asCustom(dialog)
            .show();
    }
}