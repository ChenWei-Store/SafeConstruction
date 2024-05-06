package com.shuangning.safeconstruction.utils2

import android.util.Log
import com.shuangning.safeconstruction.BuildConfig

/**
 * Created by Chenwei on 2023/9/9.
 */
const val DEFAULT_TAG = "MyLog"
const val MAX_LENGTH = 4000
object MyLog {
    private var isLog = BuildConfig.DEBUG
    private var tag = DEFAULT_TAG
    fun init(tag: String = DEFAULT_TAG){
        this.tag = tag
    }
    fun v(msg: String){
        if (isPrintLog()){
            printLog(msg){
                start, end ->
                Log.v(tag, msg.substring(start, end))
            }
        }
    }
    fun i(msg: String){
        if (isPrintLog()){
            printLog(msg){
                    start, end ->
                Log.i(tag, msg.substring(start, end))
            }
        }
    }
    fun w(msg: String){
        if (isPrintLog()){
            printLog(msg){
                    start, end ->
                Log.w(tag, msg.substring(start, end))
            }
        }
    }
    fun d(msg: String){
        if (isPrintLog()){
            printLog(msg){
                    start, end ->
                Log.d(tag, msg.substring(start, end))
            }
        }
    }
    fun e(msg: String){
        if (isPrintLog()){
            printLog(msg){
                    start, end ->
                Log.e(tag, msg.substring(start, end))
            }
        }
    }

    fun printLog(msg: String, block: (start: Int, end: Int)->Unit){
        if (msg.length > MAX_LENGTH){
            val printCount = msg.length / MAX_LENGTH + 1
            for (i in 1.. printCount){
                if (i == printCount){
                    block(MAX_LENGTH * (i - 1), msg.length)
                }else{
                    block(MAX_LENGTH * (i - 1), MAX_LENGTH * i)
                }
            }
        }else{
            block(0, msg.length)
        }

    }
    private fun isPrintLog(): Boolean = isLog

}