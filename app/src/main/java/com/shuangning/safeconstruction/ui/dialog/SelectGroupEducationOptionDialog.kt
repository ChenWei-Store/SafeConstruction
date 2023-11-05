package com.shuangning.safeconstruction.ui.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.bean.event.GroupEducationOption
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.extension.onClick
import com.shuangning.safeconstruction.extension.setText
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.utils.DateUtil
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils2.EventbusUtils
import java.util.Calendar
import java.util.Date

/**
 * Created by Chenwei on 2023/11/5.
 */
class SelectGroupEducationOptionDialog(val ctx: Context): AttachPopupView(ctx) {
    private var selectedPosition = 0
    private var tvs = mutableListOf<TextView>()
    private var selectedCalendar: Calendar = Calendar.getInstance()
    private var startDate = Date()
    private var endDate = Date()
    override fun onCreate() {
        super.onCreate()
        selectedCalendar.time = Date()
        tvs.add(findViewById(R.id.tv_all))
        tvs.add(findViewById(R.id.tv_works))
        tvs.add(findViewById(R.id.tv_not_works))
        selectedPosition = 0
        setStatus()
        R.id.view2.onClick(this){
            //开始日期
            XPopCreateUtils.showYearMonthDialog(ctx, TimePickerPopup.Mode.YMD, selectedCalendar, object: TimePickerListener{
                override fun onTimeChanged(date: Date?) {
                }

                override fun onTimeConfirm(date: Date?, view: View?) {
                    date ?: return
                    startDate = date
                    val start = TimeUtils.parseTime(date, TimeUtils.yyyy_MM_dd)
                    R.id.tv_start_time.setText(this@SelectGroupEducationOptionDialog, start)
                }

                override fun onCancel() {
                }

            })
        }
        R.id.view3.onClick(this){
            //结束日期
            XPopCreateUtils.showYearMonthDialog(ctx, TimePickerPopup.Mode.YMD, selectedCalendar, object: TimePickerListener{
                override fun onTimeChanged(date: Date?) {
                }

                override fun onTimeConfirm(date: Date?, view: View?) {
                    date ?: return
                    endDate = date
                    val end = TimeUtils.parseTime(date, TimeUtils.yyyy_MM_dd)
                    R.id.tv_end_time.setText(this@SelectGroupEducationOptionDialog, end)
                }

                override fun onCancel() {
                }

            })
        }
        R.id.tv_reset.onClick(this){
            //重置
            R.id.tv_start_time.setText(this, "请选择开始日期")
            R.id.tv_end_time.setText(this, "请选择结束日期")
            selectedPosition = 0
            setStatus()
        }
        R.id.tv_confirm.onClick(this){
            //确定
            val data = GroupEducationOption(startDate, endDate, selectedPosition)
            EventbusUtils.post(EventCode.GROUP_EDUCATION_STATUS, data)
            dismiss()
        }
        tvs.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                selectedPosition = index
                setStatus()
            }
        }
    }

    private fun setStatus(){
        tvs.forEachIndexed {
                index2, textView2 ->
            textView2.isSelected = index2 == selectedPosition
        }
    }

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_select_group_education_option
    }
}