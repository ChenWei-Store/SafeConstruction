package com.shuangning.safeconstruction.utils;

import android.content.res.Configuration;
import android.os.Build;

import com.shuangning.safeconstruction.base.BaseApplication;

/**
 * Created by it_ch on 2017/11/2.
 */

public class DeviceUtils {
    /**
     * 获得设备品牌
     */
    public static String getDeviceBrand() {
        return (Build.BRAND) == null ? "" : (Build.BRAND).trim();
    }

    /**
     * 获得设备型号
     */
    public static String getDeviceModel() {
        return (Build.MODEL) == null ? "" : (Build.MODEL).trim();
    }

    /**
     * 获取制造商信息
     */
    public static String getManufacturer() {
        return (Build.MANUFACTURER) == null ? "" : (Build.MANUFACTURER).trim();
    }

    /**
     * 获得系统版本值
     */
    public static int getDevicSDKINT() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 是否是华为荣耀6Plus
     */
    public static boolean isHW6Plus() {
        if (getManufacturer().toLowerCase().contains("huawei") && getDeviceModel().contains("PE-TL10")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是华为Mate9
     */
    public static boolean isHWMate9() {
        if (getManufacturer().toLowerCase().contains("huawei") && getDeviceModel().contains("LON-AL00")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是华为麦芒手机
     */
    public static boolean isHWMaiMang() {
        if (getManufacturer().toLowerCase().contains("huawei")) {
            //麦芒手机麦芒 6、麦芒 5、麦芒 4、
            if (getDeviceModel().contains("RNE-AL00") || getDeviceModel().contains("MLA")
                    || getDeviceModel().contains("RIO-AL00") || getDeviceModel().contains("C199s")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 是否是小米设备
     */
    public static boolean isMI() {
        if (getManufacturer().toLowerCase().contains("xiaomi")) {
            if (getDeviceModel().contains("MI")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 是否是红米设备
     */
    public static boolean isHM() {
        if (getManufacturer().toLowerCase().contains("xiaomi")) {
            if (getDeviceModel().contains("MI")) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 是否是OPPO手机
     */
    public static boolean isOPPO() {
        if (getManufacturer().toLowerCase().contains("oppo")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是三星手机
     * Android 4.0到5.0的手机
     */
    public static boolean isSamSung4() {
        if (getManufacturer().toLowerCase().contains("samsung") && getDevicSDKINT() >= 14 && getDevicSDKINT() <= 20) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是三星手机
     * Android 5.0到5.1的手机
     */
    public static boolean isSamSung5() {
        if (getManufacturer().toLowerCase().contains("samsung") && getDevicSDKINT() >= 21 && getDevicSDKINT() <= 22) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是三星手机
     * Android 6.0的手机
     */
    public static boolean isSamSung6() {
        if (getManufacturer().toLowerCase().contains("samsung") && getDevicSDKINT() == 23) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是VIVO X7手机
     */
    public static boolean isVivoX7() {
        if (getManufacturer().toLowerCase().contains("vivo") && getDeviceModel().contains("X7")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是VIVO X9手机
     */
    public static boolean isVivoX9() {
        if (getManufacturer().toLowerCase().contains("vivo") && getDeviceModel().contains("X9")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是乐视手机
     */
    public static boolean isLeTv() {
        if (getManufacturer().toLowerCase().contains("letv") || getManufacturer().toLowerCase().contains("lemobile")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMeizu(){
        if(getManufacturer().toLowerCase().contains("meizu")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断是否是平板
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isTablet() {
        try {
            return (BaseApplication.appContext.getResources().getConfiguration().screenLayout
                    & Configuration.SCREENLAYOUT_SIZE_MASK
            ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        } catch (Exception e) {
        }
        return false;
    }
}
