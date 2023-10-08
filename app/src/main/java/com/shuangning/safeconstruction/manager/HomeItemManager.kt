package com.shuangning.safeconstruction.manager

import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.bean.other.HomeContentBean

/**
 * Created by Chenwei on 2023/10/7.
 */
object HomeItemManager {
    private val attendanceManagement = mutableListOf(R.string.attendance_management, R.drawable.attendance_management_icon)
//    private val videoSurveillance = mutableListOf(R.string.video_surveillance, R.drawable.video_surveillance)
    private val rectificationAndReply = mutableListOf(R.string.rectification_and_reply, R.drawable.rectification_and_reply)
    private val routineInspection = mutableListOf(R.string.routine_inspection, R.drawable.routine_inspection_icon)
    private val takePhotosOfHiddenDangers = mutableListOf(R.string.take_photos_of_hidden_dangers, R.drawable.take_photos_of_hidden_dangers_icon)
    private val datas = mutableListOf(attendanceManagement, rectificationAndReply, routineInspection, takePhotosOfHiddenDangers)
    private val homeContentBeans = mutableListOf<HomeContentBean>()

    init {
        datas.forEach{
            val nameId = it[0]
            val iconId = it[1]
            val homeContentBean = HomeContentBean(iconId, nameId)
            homeContentBeans.add(homeContentBean)
        }
    }

    fun getData(): MutableList<HomeContentBean>{
        return homeContentBeans
    }
}