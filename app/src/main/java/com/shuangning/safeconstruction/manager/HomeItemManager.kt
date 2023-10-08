package com.shuangning.safeconstruction.manager

import com.shuangning.safeconstruction.R

/**
 * Created by Chenwei on 2023/10/7.
 */
object HomeItemManager {
    private val attendanceManagement = mutableListOf<Any>(R.string.attendance_management, R.drawable.attendance_management_icon)
//    private val videoSurveillance = mutableListOf<Any>(R.string.video_surveillance, R.drawable.video_surveillance)
    private val rectificationAndReply = mutableListOf<Any>(R.string.rectification_and_reply, R.drawable.rectification_and_reply)
    private val routineInspection = mutableListOf<Any>(R.string.routine_inspection, R.drawable.routine_inspection_icon)
    private val takePhotosOfHiddenDangers = mutableListOf<Any>(R.string.take_photos_of_hidden_dangers, R.drawable.take_photos_of_hidden_dangers_icon)

    private val datas = mutableListOf<Any>(attendanceManagement, rectificationAndReply, routineInspection, takePhotosOfHiddenDangers)

}