package com.shuangning.safeconstruction.bean.other

import com.bin.david.form.annotation.SmartColumn
import com.bin.david.form.annotation.SmartTable

/**
 * Created by Chenwei on 2023/10/15.
 */
@SmartTable(name="考勤表")
data class AttendanceData (
    @SmartColumn(name="姓名", id = 0) val userName: String,
    @SmartColumn(name="岗位", id = 1) val job: String,
    @SmartColumn(name="出勤天数", id = 2) val dayNum: Int,
    @SmartColumn(name="1", id = 3) val _1: String = "",
    @SmartColumn(name="2", id = 4) val _2: String = "",
    @SmartColumn(name="3", id = 5) val _3: String = "",
    @SmartColumn(name="4", id = 6) val _4: String = "",
    @SmartColumn(name="5", id = 7) val _5: String = "",
    @SmartColumn(name="6", id = 8) val _6: String = "",
    @SmartColumn(name="7", id = 9) val _7: String = "",
    @SmartColumn(name="8", id = 10) val _8: String = "",
    @SmartColumn(name="9", id = 11) val _9: String = "",
    @SmartColumn(name="10", id = 12) val _10: String = "",
    @SmartColumn(name="11", id = 13) val _11: String = "",
    @SmartColumn(name="12", id = 14) val _12: String = "",
    @SmartColumn(name="13", id = 15) val _13: String = "",
    @SmartColumn(name="14", id = 16) val _14: String = "",
    @SmartColumn(name="15", id = 17) val _15: String = "",
    @SmartColumn(name="16", id = 18) val _16: String = "",
    @SmartColumn(name="17", id = 19) val _17: String = "",
    @SmartColumn(name="18", id = 20) val _18: String = "",
    @SmartColumn(name="19", id = 21) val _19: String = "",
    @SmartColumn(name="20", id = 22) val _20: String = "",
    @SmartColumn(name="21", id = 23) val _21: String = "",
    @SmartColumn(name="22", id = 24) val _22: String = "",
    @SmartColumn(name="23", id = 25) val _23: String = "",
    @SmartColumn(name="24", id = 26) val _24: String = "",
    @SmartColumn(name="25", id = 27) val _25: String = "",
    @SmartColumn(name="26", id = 28) val _26: String = "",
    @SmartColumn(name="27", id = 29) val _27: String = "",
    @SmartColumn(name="28", id = 30) val _28: String = "",
    @SmartColumn(name="29", id = 31) val _29: String = "",
    @SmartColumn(name="30", id = 32) val _30: String = "",
    @SmartColumn(name="31", id = 33) val _31: String = "",

)
