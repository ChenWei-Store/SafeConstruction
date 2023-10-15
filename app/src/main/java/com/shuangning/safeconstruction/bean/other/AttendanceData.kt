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
)
