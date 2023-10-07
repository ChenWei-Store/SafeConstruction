package com.shuangning.safeconstruction.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.WindowManager;

import com.shuangning.safeconstruction.base.BaseApplication;
import com.shuangning.safeconstruction.utils2.MyLog;

import java.util.UUID;


/**
 *
 */

public class APPUtils {
    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }


    /**
     * 获得设备品牌
     */
    public static String getDeviceBrand() {
        return (Build.BRAND) == null ? "" : (Build.BRAND).trim();
    }

    /**
     * 获取制造商信息
     */
    public static String getManufacturer() {
        return (Build.MANUFACTURER) == null ? "" : (Build.MANUFACTURER).trim();
    }

    /**
     * 获取android系统版本号
     */
    public static String getOSVersion() {
        String release = Build.VERSION.RELEASE; // android系统版本号
        release = "android " + release;
        return release;
    }

    /**
     * 获取屏幕的分辨率
     */
    @SuppressWarnings("deprecation")
    public static int[] getResolution() {

        WindowManager windowMgr = (WindowManager) BaseApplication.appContext.getSystemService(Context.WINDOW_SERVICE);
        int[] res = new int[2];
        res[0] = windowMgr.getDefaultDisplay().getWidth();
        res[1] = windowMgr.getDefaultDisplay().getHeight();
        return res;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    //版本名
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    //版本信息
    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm = BaseApplication.appContext.getPackageManager();
            pi = pm.getPackageInfo(BaseApplication.appContext.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    //获取UUID
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }

    /**
     * 获取 APP 包名
     * @return APP 包名
     */
    public static String getPackageName() {
        try {
            return BaseApplication.appContext.getPackageName();
        } catch (Exception e) {
            MyLog.INSTANCE.e("getPackageName error:" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取app名称
     * @return
     */
    public static String getAppName(){
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = BaseApplication.appContext.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            String applicationName =
                    (String) packageManager.getApplicationLabel(applicationInfo);
            return applicationName;
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }

        return "";
    }

    /**
     * 获取安装时间
     */
    public static String getInstallTime(){
        PackageManager packageManager = null;
        PackageInfo packageInfo = null;
        try {
            packageManager = BaseApplication.appContext.getPackageManager();
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return String.valueOf(packageInfo.firstInstallTime);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return "";
    }

    /**
     * 获取更新时间
     */
    public static String getUpdateTime(){
        PackageManager packageManager = null;
        PackageInfo packageInfo = null;
        try {
            packageManager = BaseApplication.appContext.getPackageManager();
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return String.valueOf(packageInfo.lastUpdateTime);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return "";
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }
}
