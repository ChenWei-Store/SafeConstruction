package com.shuangning.safeconstruction.utils2

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.system.exitProcess

/**
 * Created by Chenwei on 2023/9/8.
 */
object ActivityUtils {
    private val activities = CopyOnWriteArrayList<Activity>()
    fun addActivity(activity: Activity){
        MyLog.d("addActivity:" + activity.localClassName)
        activities.add(activity)
    }

    fun removeActivity(activity: Activity){
        MyLog.d("removeActivity:" + activity.localClassName)
        activities.remove(activity)
    }

    fun getTopActivity(): Activity?{
        MyLog.d("getTopActivity")
        if(activities.size <= 0){
            return null
        }
        return activities[activities.size - 1]
    }
    fun removeAllActivities(){
        MyLog.d("removeAllActivities")
        activities.clear()
    }

    fun finishAllActivities(){
        activities.forEach {
            it.finish()
            activities.remove(it)
        }
    }

    fun finishActivitiesExcludeTop(){
        val it = activities.listIterator()
        var index = activities.size - 1
        while (it.hasPrevious()){
            if (index != activities.size - 1){
                val item = it.previous()
                item.finish()
                it.remove()
            }
            index--
        }
    }
    fun finishActivity(){
        activities[activities.size - 1].finish()
        activities.removeAt(activities.size - 1)
//        if(activities.size == 0){
//            return
//        }
//        if (activities.size == 1){
//            ExitAppUtils.exitApp(activities[0])
//        }else{
//            activities[activities.size - 1].finish()
//        }
    }
    @UiThread
    fun exitApp(){
        MyLog.d("exitApp")
        removeAllActivities()
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(1)
    }

    fun <T: AppCompatActivity>start(ctx: Context, cls: Class<T>){
        val intent = Intent(ctx, cls)
        ctx.startActivity(intent)
    }
    fun <T: AppCompatActivity>start(ctx: Context, cls: Class<T>, addExtra: Intent.()->Unit){
        val intent = Intent(ctx, cls)
        intent.addExtra()
        ctx.startActivity(intent)
    }
}