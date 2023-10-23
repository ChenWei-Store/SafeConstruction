package com.shuangning.safeconstruction.manager

import com.shuangning.safeconstruction.bean.other.UserInfo
import com.shuangning.safeconstruction.data.mmkv.MMKVResp

/**
 * Created by Chenwei on 2023/10/23.
 */
object UserInfoManager {
    private var userInfo: UserInfo? = null
    fun init(){
        userInfo = MMKVResp.resp.getUserInfo()
    }

    fun setUserInfo(loginName: String, pwd: String){
        userInfo = UserInfo(loginName, pwd).apply {
            MMKVResp.resp.putUserInfo(this)
        }
    }

    fun getUserInfo(): UserInfo?{
        return userInfo
    }

    fun clear(){
        userInfo = null
    }
}