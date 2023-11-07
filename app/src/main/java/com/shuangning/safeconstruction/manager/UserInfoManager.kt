package com.shuangning.safeconstruction.manager

import com.shuangning.safeconstruction.bean.other.UserInfo
import com.shuangning.safeconstruction.data.mmkv.MMKVResp
import com.shuangning.safeconstruction.data.mmkv.TOKEN

/**
 * Created by Chenwei on 2023/10/23.
 */
object UserInfoManager {
    private var userInfo: UserInfo? = null
    private var token: String?= null
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

    fun setToken(token: String){
        this.token = token
        MMKVResp.resp.putToken(token)
    }

    fun getToken(): String?{
        if (null == token){
            token = MMKVResp.resp.getToken()
        }
        return token
    }

    fun clear(){
        userInfo = null
    }
}