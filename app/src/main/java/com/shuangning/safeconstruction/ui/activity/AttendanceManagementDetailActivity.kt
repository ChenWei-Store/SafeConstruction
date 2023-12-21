package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.bin.david.form.core.AnnotationParser
import com.bin.david.form.data.CellInfo
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.format.bg.IBackgroundFormat
import com.bin.david.form.data.format.bg.ICellBackgroundFormat
import com.bin.david.form.data.style.FontStyle
import com.bin.david.form.data.table.TableData
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.other.AttendanceData
import com.shuangning.safeconstruction.bean.other.DepartmentBean
import com.shuangning.safeconstruction.databinding.ActivityAttendanceManagementDetailBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.viewmodel.AttendanceManagementDetailViewModel
import com.shuangning.safeconstruction.utils.DateUtil
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.TimeUtils.yyyy_MM
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils
import com.shuangning.safeconstruction.utils2.MyLog
import java.util.Calendar
import java.util.Date


/**
 * Created by Chenwei on 2023/10/15.
 */

class AttendanceManagementDetailActivity :
    BaseActivity<ActivityAttendanceManagementDetailBinding>() {
    private val data: MutableList<AttendanceData> = mutableListOf()
    private var annotationParser: AnnotationParser<AttendanceData> =
        AnnotationParser(ScreenUtil.dp2px(10f))
    private var selectedMonth = -1
    private var selectedYear = -1
    private var selectedDepartment = ""
    private var selectedCalendar: Calendar = Calendar.getInstance()
    private var selectedDay: String = ""
    private val personType: Int by lazy {
        intent?.getIntExtra(PERSON_TYPE, 1) ?: 1
    }
    private var nowDay = 0
    private val viewModel by viewModels<AttendanceManagementDetailViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAttendanceManagementDetailBinding? {
        return ActivityAttendanceManagementDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("考勤管理")
        binding?.tvDate?.text = "$selectedMonth 月"
        setTable()
    }

    private fun setTable() {
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
            contentCellBackgroundFormat = object : ICellBackgroundFormat<CellInfo<Any>> {
                override fun drawBackground(
                    canvas: Canvas?, rect: Rect?, t: CellInfo<Any>?, paint: Paint?
                ) {
                    t ?: return
                    paint ?: return
                    canvas ?: return
                    rect ?: return
                    if (t.row % 2 == 0) {
                        paint.color = UIUtils.getColor(R.color.white)
                    } else {
                        paint.color = UIUtils.getColor(R.color.c_f2f8ff)
                    }
                    canvas.drawRect(rect, paint)
                }

                override fun getTextColor(t: CellInfo<Any>?): Int {
                    return UIUtils.getColor(R.color.c_555)
                }
            }
        }
    }

    private fun setTableData() {
        var tableData = annotationParser.parse(data)
        tableData?.let {
            it.columns = tableData.columns.subList(0, 3 + nowDay)
            binding?.table?.setTableData(it)
        }?:let{
            initEmptyTable(nowDay)
        }
    }

    private fun initEmptyTable(nowDay: Int) {
        //普通列
        //普通列
        val columns: MutableList<Column<String>> = mutableListOf()
        val column1: Column<String> = Column("姓名", "name")
        val column2: Column<String> = Column("岗位", "age")
        val column3: Column<String> = Column("出勤天数", "time")
        columns.add(column1)
        columns.add(column2)
        columns.add(column3)
        for (index in 1..nowDay){
            val column: Column<String> = Column("$index", "_${index}")
            columns.add(column)
        }
        //表格数据 datas是需要填充的数据
        //表格数据 datas是需要填充的数据
        val tableData: TableData<AttendanceData> = TableData<AttendanceData>("", mutableListOf(), columns.toList())
        binding?.table?.setTableData(tableData)
    }

    override fun initData() {
        selectedCalendar.time = Date()
        selectedMonth = DateUtil.getMonth(TimeUtils.getCurrentDate(TimeUtils.yyyy_MM_dd))
        selectedYear = DateUtil.getYears(TimeUtils.getCurrentDate(TimeUtils.yyyy_MM_dd))
        selectedDay = TimeUtils.getCurrentDate(yyyy_MM)
        nowDay = selectedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        LoadingManager.startLoading(this)
        viewModel.getSectionAndList(personType, selectedDay)
        MyLog.d("nowDay:$nowDay")
    }


    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.tvDate?.setOnClickListener {
            XPopCreateUtils.showYearMonthDialog(this,
                TimePickerPopup.Mode.YM,
                selectedCalendar,
                object : TimePickerListener {
                    override fun onTimeChanged(date: Date?) {
                    }

                    override fun onTimeConfirm(date: Date?, view: View?) {
                        date?.let {
                            selectedMonth =
                                DateUtil.getMonth(TimeUtils.parseTime(date, TimeUtils.yyyy_MM_dd))
                            selectedYear =
                                DateUtil.getYears(TimeUtils.parseTime(date, TimeUtils.yyyy_MM_dd))
                            selectedDay = TimeUtils.parseTime(date, yyyy_MM)
                            binding?.tvDate?.text = "$selectedMonth 月"
                            selectedCalendar.time = date
                            nowDay = selectedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                            refreshData()
                        }
                    }

                    override fun onCancel() {

                    }
                })
        }

        binding?.tvDepartment?.setOnClickListener {
            binding?.view2?.apply {
                XPopCreateUtils.showAttachDepartment(this@AttendanceManagementDetailActivity,
                    this,
                    viewModel.getDepartmentData(),
                    selectedMonth,
                    selectedDepartment,
                    object : OnItemClickListener<DepartmentBean> {
                        override fun onItemClick(data: DepartmentBean, position: Int) {
                            binding?.tvDepartment?.text = data.name
                            selectedDepartment = data.name
                            refreshData()
                        }
                    })
            }
        }
    }

    private fun refreshData() {
        LoadingManager.startLoading(this@AttendanceManagementDetailActivity)
        viewModel.refreshData(selectedDepartment, personType, selectedDay)
    }

    override fun observeViewModel() {
        viewModel.selection.observe(this) {
            it?.let {
                selectedDepartment = it
                binding?.tvDepartment?.text = selectedDepartment
            }
        }
        viewModel.result.observe(this) {
            it?.result?.let {
                data.clear()
                it.forEach {
                        it2->
                    val item = AttendanceData.transform(it2)
                    data.add(item)
                }
                setTableData()
                LoadingManager.stopLoading()

            }?:let {
                LoadingManager.stopLoading()
            }

        }
    }

    companion object {
        const val PERSON_TYPE = "personType"
        fun startTo(ctx: Context, personType: Int) {
            ActivityUtils.start(ctx, AttendanceManagementDetailActivity::class.java) {
                putExtra(PERSON_TYPE, personType)
            }
        }
    }
}