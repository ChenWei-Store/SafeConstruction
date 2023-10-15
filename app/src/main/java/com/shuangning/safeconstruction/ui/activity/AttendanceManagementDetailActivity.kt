package com.shuangning.safeconstruction.ui.activity

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import com.bin.david.form.data.CellInfo
import com.bin.david.form.data.format.bg.IBackgroundFormat
import com.bin.david.form.data.format.bg.ICellBackgroundFormat
import com.bin.david.form.data.style.FontStyle
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.bean.other.AttendanceData
import com.shuangning.safeconstruction.databinding.ActivityAttendanceManagementDetailBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/15.
 */
class AttendanceManagementDetailActivity: BaseActivity<ActivityAttendanceManagementDetailBinding>() {
    private val data: MutableList<AttendanceData> = mutableListOf()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAttendanceManagementDetailBinding? {
        return ActivityAttendanceManagementDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("考勤管理")
        setTable()

    }

    private fun setTable(){
        val titleFontStyle = FontStyle(this, 14, UIUtils.getColor(R.color.c_555))
        val contentFontStyle = FontStyle(this, 14, UIUtils.getColor(R.color.c_555))
        binding?.table?.config?.apply {
            isShowTableTitle = false
            isShowXSequence = false
            isShowYSequence = false
            tableTitleStyle = titleFontStyle
            contentStyle = contentFontStyle
            columnTitleBackground = IBackgroundFormat { canvas, rect, paint ->
                paint.style = Paint.Style.FILL
                paint.color = UIUtils.getColor(R.color.divider3)
                canvas.drawRect(rect, paint)
            }
            contentCellBackgroundFormat = object: ICellBackgroundFormat<CellInfo<Any>> {
                override fun drawBackground(
                    canvas: Canvas?,
                    rect: Rect?,
                    t: CellInfo<Any>?,
                    paint: Paint?
                ) {
                    t ?: return
                    paint ?: return
                    canvas ?: return
                    rect ?: return
                    if (t.row % 2 == 0){
                        paint.color = UIUtils.getColor(R.color.white)
                    }else{
                        paint.color =UIUtils.getColor(R.color.c_f2f8ff)
                    }
                    canvas.drawRect(rect, paint)
                }

                override fun getTextColor(t: CellInfo<Any>?): Int {
                    return UIUtils.getColor(R.color.c_555)
                }
            }
        }
        binding?.table?.setData(data)
    }
    override fun initData() {
        data.add(AttendanceData("徐华盛", "试验检测工程师", 0))
        data.add(AttendanceData("欧阳丽华", "试验检测工程师", 5))
        data.add(AttendanceData("陈云飞", "安全员", 8))
        data.add(AttendanceData("杨洋", "技术负责人", 8))

    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.tvDate?.setOnClickListener {
            XPopCreateUtils.showYearMonthDialog(this)
        }
    }

    override fun observeViewModel() {
    }
}