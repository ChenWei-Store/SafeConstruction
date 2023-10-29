package com.shuangning.safeconstruction.manager

import android.content.Context
import android.view.View
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.animator.EmptyAnimator
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupPosition
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.DepartmentBean
import com.shuangning.safeconstruction.ui.dialog.AttachAddDialog
import com.shuangning.safeconstruction.ui.dialog.AttachListDepartmentDialog
import com.shuangning.safeconstruction.ui.dialog.CommonConfirmDialog
import com.shuangning.safeconstruction.ui.dialog.SelectTypeDialog
import com.shuangning.safeconstruction.utils.ScreenUtil
import java.util.Calendar
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

    fun showSelectTypeView(ctx: Context, attachView: View, data: MutableList<IItemViewType>): BasePopupView{
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

    fun showListCenterDialog(ctx: Context, data: Array<String>,
                             block: (index: Int, text: String)->Unit){
        XPopup.Builder(ctx)
            .isDestroyOnDismiss(true)
            .dismissOnBackPressed(true)
            .dismissOnTouchOutside(true)
            .maxHeight((ScreenUtil.getScreenHeight() * 0.8f).toInt())
            .asCenterList("请选择", data) {
                    position, text ->
                block(position, text)
            }
            .show()
    }

    fun showYearMonthDialog(ctx: Context, mode: TimePickerPopup.Mode, selectedDate: Calendar, listener: TimePickerListener){
        val dialog = TimePickerPopup(ctx)
            .setMode(mode)
            .setDefaultDate(selectedDate)
            .setTimePickerListener(listener)
        XPopup.Builder(ctx)
            .asCustom(dialog)
            .show()
    }

    fun showAttachAdd(ctx: Context, attachView: View){
        XPopup.Builder(ctx)
            .isDestroyOnDismiss(true)
            .dismissOnBackPressed(true)
            .dismissOnTouchOutside(true)
            .enableDrag(false)
            .hasShadowBg(false)
            .isThreeDrag(false)
            .customAnimator(EmptyAnimator(attachView, 300))
            .popupPosition(PopupPosition.Top)
            .popupWidth(ScreenUtil.getScreenWidth())
            .atView(attachView)
            .asCustom(AttachAddDialog(ctx))
            .show()
    }

    fun showTipDialog(ctx: Context, title: String, content: String?){
        XPopup.Builder(ctx)
            .isDestroyOnDismiss(true)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .enableDrag(false)
            .isThreeDrag(false)
            .popupWidth((ScreenUtil.getScreenWidth() * 0.8f).toInt())
            .asCustom(CommonConfirmDialog(ctx, title, content))
            .show()
    }

    fun showConfirmCancelDialog(ctx: Context, title: String, content: String, block: ()-> Unit){
        XPopup.Builder(ctx)
            .autoDismiss(true)
            .isDestroyOnDismiss(true)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .enableDrag(false)
            .isThreeDrag(false)
            .asConfirm(title, content) {
            block()
        }.show()
    }

    fun showAttachDepartment(ctx: Context, attachView: View, data: MutableList<DepartmentBean>,
                             month: Int, department: String, listener: OnItemClickListener<DepartmentBean>){
        XPopup.Builder(ctx)
            .dismissOnBackPressed(true)
            .dismissOnTouchOutside(true)
            .enableDrag(false)
            .isThreeDrag(false)
            .customAnimator(EmptyAnimator(attachView, 300))
            .popupPosition(PopupPosition.Bottom)
            .popupWidth(ScreenUtil.getScreenWidth())
            .atView(attachView)
            .asCustom(AttachListDepartmentDialog(ctx, data, month, department, listener))
            .show()
    }
}