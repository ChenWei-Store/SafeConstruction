package com.shuangning.safeconstruction.manager

import com.shuangning.safeconstruction.bean.other.UserInfo
import com.shuangning.safeconstruction.bean.response.UserInfoResp
import com.shuangning.safeconstruction.data.mmkv.MMKVResp
import com.shuangning.safeconstruction.data.mmkv.TOKEN

/**
 * Created by Chenwei on 2023/10/23.
 */
object UserInfoManager {
    private var userInfo: UserInfo? = null
    private var token: String?= null
    private var isLogin: Boolean = false
    fun init(){
        userInfo = MMKVResp.resp.getUserInfo()
        isLogin = MMKVResp.resp.getLogin()
        token = MMKVResp.resp.getToken()
    }

    fun setLogin(isLogin: Boolean){
        this.isLogin = isLogin
        MMKVResp.resp.putLogin(isLogin)
    }

    fun getLogin(): Boolean{
        return isLogin
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

    fun updateUserInfo(userInfoResp: UserInfoResp){
        userInfo?.let {
            it.companyType = userInfoResp.extend.danweileixing
            MMKVResp.resp.putUserInfo(it)
        }
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