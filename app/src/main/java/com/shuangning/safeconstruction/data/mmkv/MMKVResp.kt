package com.shuangning.safeconstruction.data.mmkv

import com.shuangning.safeconstruction.bean.other.UserInfo
import com.shuangning.safeconstruction.utils2.KeyValueUtils


/**
 * Created by Chenwei on 2023/9/4.
 */
const val APP_CONFIG = "appConfig"
const val LOGIN_NAME = "loginName"
const val PWD = "pwd"
class MMKVResp {
    private var keyValueUtils: KeyValueUtils = KeyValueUtils()
    init {
        keyValueUtils.init()
    }

    fun putUserInfo(userInfo: UserInfo){
        keyValueUtils.putAllWithId(APP_CONFIG) {
            putString(LOGIN_NAME, userInfo.userName)
            putString(PWD, userInfo.pwd)
        }
    }
    fun getUserInfo(): UserInfo{
        val userName = keyValueUtils.getWithId(APP_CONFIG, key = LOGIN_NAME, valueType = "")
        val pwd = keyValueUtils.getWithId(APP_CONFIG, key = PWD, valueType = "")
        return UserInfo(userName, pwd)
    }

    fun clear(){
        keyValueUtils.clearWithId(APP_CONFIG)
    }
    companion object{
        val resp = MMKVResp()
    }
}