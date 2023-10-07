package com.shuangning.safeconstruction.utils2

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.content.res.Resources

/**
 * Created by Chenwei on 2023/9/9.
 */
object AdaptScreenUtil {
    private var sNonCompatDensity: Float = 0f
    private var sNonCompatScaledDensity: Float = 0f

    /**
     * 字节屏幕适配方案
     */
    fun setCustomDensity(activity: Activity, application: Application, designWidthDp: Int) {
        val appDisplayMetrics = application.resources.displayMetrics
        if(sNonCompatDensity == 0f){
            sNonCompatDensity = appDisplayMetrics.density
            sNonCompatScaledDensity = appDisplayMetrics.scaledDensity
            application.registerComponentCallbacks(object: ComponentCallbacks {
                override fun onConfigurationChanged(p0: Configuration) {
                   if(p0 != null && p0.fontScale > 0){
                       sNonCompatScaledDensity = application.resources.displayMetrics.scaledDensity
                   }
                }

                override fun onLowMemory() {
                }
            }

            )
        }
        val targetDensity = 1.0f * appDisplayMetrics.widthPixels / designWidthDp
        val targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity)
        val targetDensityDpi = (targetDensity * 160).toInt()
        appDisplayMetrics.density = targetDensity
        appDisplayMetrics.densityDpi = targetDensityDpi
        appDisplayMetrics.scaledDensity = targetScaledDensity
        val activityDisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
        activityDisplayMetrics.scaledDensity = targetScaledDensity
    }

    fun resetScreen(activity: Activity) {
        //系统的屏幕尺寸
        val systemDM = Resources.getSystem().displayMetrics
        //app整体的屏幕尺寸
        val appDM = activity.application.resources.displayMetrics
        //activity的屏幕尺寸
        val activityDM = activity.resources.displayMetrics

        activityDM.density = systemDM.density
        activityDM.scaledDensity = systemDM.scaledDensity
        activityDM.densityDpi = systemDM.densityDpi

        appDM.density = systemDM.density
        appDM.scaledDensity = systemDM.scaledDensity
        appDM.densityDpi = systemDM.densityDpi
    }
}