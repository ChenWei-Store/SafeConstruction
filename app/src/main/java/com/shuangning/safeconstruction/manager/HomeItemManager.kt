package com.shuangning.safeconstruction.manager

import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.bean.other.HomeBean

/**
 * Created by Chenwei on 2023/10/7.
 */
object HomeItemManager {
    const val TAKE_PTOTOS_OF_DANGERS = 0
    const val ATTENDANCE_MANAGEMENT = 1
    const val RECTIFICATION_AND_REPLY = 2
    const val ROUTINE_INSPCETION = 3
    const val SCAN_QRCODE = 4
    const val CLOCK_IN_OUT = 5

    private val attendanceManagement = mutableListOf(R.string.attendance_management, R.drawable.attendance_management_icon, ATTENDANCE_MANAGEMENT)
    //    private val videoSurveillance = mutableListOf(R.string.video_surveillance, R.drawable.video_surveillance)
    private val rectificationAndReply = mutableListOf(R.string.rectification_and_reply, R.drawable.rectification_and_reply, RECTIFICATION_AND_REPLY)
    private val routineInspection = mutableListOf(R.string.routine_inspection, R.drawable.routine_inspection_icon, ROUTINE_INSPCETION)
    private val takePhotosOfDangers = mutableListOf(R.string.take_photos_of_dangers, R.drawable.take_photos_of_hidden_dangers_icon, TAKE_PTOTOS_OF_DANGERS)
    private val datas = mutableListOf(attendanceManagement, rectificationAndReply, routineInspection, takePhotosOfDangers)
    private val homeContentBeans = mutableListOf<HomeBean>()

    init {
        datas.forEach{
            val nameId = it[0]
            val iconId = it[1]
            val funcId = it[2]
            val homeContentBean = HomeBean(iconId, nameId, funcId)
            homeContentBeans.add(homeContentBean)
        }
    }

    fun getData(): MutableList<HomeBean>{
        return homeContentBeans
    }
}