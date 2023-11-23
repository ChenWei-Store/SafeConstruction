package com.shuangning.safeconstruction.bean.other

import com.bin.david.form.annotation.SmartColumn
import com.bin.david.form.annotation.SmartTable
import com.shuangning.safeconstruction.bean.response.AttendanceManagementItem
import com.shuangning.safeconstruction.utils.DateUtil

/**
 * Created by Chenwei on 2023/10/15.
 */
@SmartTable(name="考勤表")
data class AttendanceData (
    @SmartColumn(name="姓名", id = 0) val userName: String,
    @SmartColumn(name="岗位", id = 1) val job: String,
    @SmartColumn(name="出勤天数", id = 2) val dayNum: Int,
    @SmartColumn(name="1", id = 3) var _1: String = "",
    @SmartColumn(name="2", id = 4) var _2: String = "",
    @SmartColumn(name="3", id = 5) var _3: String = "",
    @SmartColumn(name="4", id = 6) var _4: String = "",
    @SmartColumn(name="5", id = 7) var _5: String = "",
    @SmartColumn(name="6", id = 8) var _6: String = "",
    @SmartColumn(name="7", id = 9) var _7: String = "",
    @SmartColumn(name="8", id = 10) var _8: String = "",
    @SmartColumn(name="9", id = 11) var _9: String = "",
    @SmartColumn(name="10", id = 12) var _10: String = "",
    @SmartColumn(name="11", id = 13) var _11: String = "",
    @SmartColumn(name="12", id = 14) var _12: String = "",
    @SmartColumn(name="13", id = 15) var _13: String = "",
    @SmartColumn(name="14", id = 16) var _14: String = "",
    @SmartColumn(name="15", id = 17) var _15: String = "",
    @SmartColumn(name="16", id = 18) var _16: String = "",
    @SmartColumn(name="17", id = 19) var _17: String = "",
    @SmartColumn(name="18", id = 20) var _18: String = "",
    @SmartColumn(name="19", id = 21) var _19: String = "",
    @SmartColumn(name="20", id = 22) var _20: String = "",
    @SmartColumn(name="21", id = 23) var _21: String = "",
    @SmartColumn(name="22", id = 24) var _22: String = "",
    @SmartColumn(name="23", id = 25) var _23: String = "",
    @SmartColumn(name="24", id = 26) var _24: String = "",
    @SmartColumn(name="25", id = 27) var _25: String = "",
    @SmartColumn(name="26", id = 28) var _26: String = "",
    @SmartColumn(name="27", id = 29) var _27: String = "",
    @SmartColumn(name="28", id = 30) var _28: String = "",
    @SmartColumn(name="29", id = 31) var _29: String = "",
    @SmartColumn(name="30", id = 32) var _30: String = "",
    @SmartColumn(name="31", id = 33) var _31: String = "",
){
    companion object{
        fun transform(item: AttendanceManagementItem): AttendanceData{
            val data = AttendanceData(item.userName, item.job, item.days)
            item.dayTime.forEach {
                val day = DateUtil.getDay(it)
                when(day){
                    1 ->{
                        data._1 = "✔"
                    }
                    2->{
                        data._2 = "✔"
                    }
                    3->{
                        data._3 = "✔"
                    }
                    4->{
                        data._4 = "✔"
                    }
                    5 ->{
                        data._5 = "✔"
                    }
                    6->{
                        data._6 = "✔"
                    }
                    7->{
                        data._7 = "✔"
                    }
                    8->{
                        data._8 = "✔"
                    }
                    9 ->{
                        data._9 = "✔"
                    }
                    10->{
                        data._10 = "✔"
                    }
                    11->{
                        data._11 = "✔"
                    }
                    12->{
                        data._12 = "✔"
                    }
                    13 ->{
                        data._13 = "✔"
                    }
                    14->{
                        data._14 = "✔"
                    }
                    15->{
                        data._15 = "✔"
                    }
                    16->{
                        data._16 = "✔"
                    }
                    17 ->{
                        data._17 = "✔"
                    }
                    18->{
                        data._18 = "✔"
                    }
                    19->{
                        data._19 = "✔"
                    }
                    20->{
                        data._20 = "✔"
                    }
                    21 ->{
                        data._21 = "✔"
                    }
                    22->{
                        data._22 = "✔"
                    }
                    23->{
                        data._23 = "✔"
                    }
                    24->{
                        data._24 = "✔"
                    }
                    25 ->{
                        data._25 = "✔"
                    }
                    26->{
                        data._26 = "✔"
                    }
                    27->{
                        data._27 = "✔"
                    }
                    28->{
                        data._28 = "✔"
                    }
                    29 ->{
                        data._29 = "✔"
                    }
                    30->{
                        data._30 = "✔"
                    }
                    31->{
                        data._31 = "✔"
                    }
                }
            }
            return data
        }
    }
}
