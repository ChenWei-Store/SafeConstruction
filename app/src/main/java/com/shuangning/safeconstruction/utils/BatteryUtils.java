package com.shuangning.safeconstruction.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresApi;

import com.shuangning.safeconstruction.base.BaseApplication;
import com.shuangning.safeconstruction.utils.archcore.ArchTaskExecutor;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/03/31
 *     desc  :
 * </pre>
 */
public final class BatteryUtils {

    @IntDef({BatteryStatus.UNKNOWN, BatteryStatus.DISCHARGING, BatteryStatus.CHARGING,
            BatteryStatus.NOT_CHARGING, BatteryStatus.FULL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BatteryStatus {
        int UNKNOWN      = BatteryManager.BATTERY_STATUS_UNKNOWN;
        int DISCHARGING  = BatteryManager.BATTERY_STATUS_DISCHARGING;
        int CHARGING     = BatteryManager.BATTERY_STATUS_CHARGING;
        int NOT_CHARGING = BatteryManager.BATTERY_STATUS_NOT_CHARGING;
        int FULL         = BatteryManager.BATTERY_STATUS_FULL;
    }

    /**
     * Return whether the app is on the device's power whitelist.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isIgnoringBatteryOptimizations() {
        return isIgnoringBatteryOptimizations(BaseApplication.appContext.getPackageName());
    }

    /**
     * Return whether the app is on the device's power whitelist.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isIgnoringBatteryOptimizations(String pkgName) {
        try {
            PowerManager pm = (PowerManager) BaseApplication.appContext.getSystemService(Context.POWER_SERVICE);
            //noinspection ConstantConditions
            return pm.isIgnoringBatteryOptimizations(pkgName);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Register the status of battery changed listener.
     *
     * @param listener The status of battery changed listener.
     */
    public static void registerBatteryStatusChangedListener(final OnBatteryStatusChangedListener listener) {
        BatteryChangedReceiver.getInstance().registerListener(listener);
    }

    /**
     * Return whether the status of battery changed listener has been registered.
     *
     * @param listener The status of battery changed listener.
     * @return true to registered, false otherwise.
     */
    public static boolean isRegistered(final OnBatteryStatusChangedListener listener) {
        return BatteryChangedReceiver.getInstance().isRegistered(listener);
    }

    /**
     * Unregister the status of battery changed listener.
     *
     * @param listener The status of battery changed listener.
     */
    public static void unregisterBatteryStatusChangedListener(final OnBatteryStatusChangedListener listener) {
        BatteryChangedReceiver.getInstance().unregisterListener(listener);
    }

    public static final class BatteryChangedReceiver extends BroadcastReceiver {

        private static BatteryChangedReceiver getInstance() {
            return LazyHolder.INSTANCE;
        }

        private Set<OnBatteryStatusChangedListener> mListeners = new HashSet<>();

        void registerListener(final OnBatteryStatusChangedListener listener) {
            if (listener == null) return;
            ArchTaskExecutor.getInstance().postToMainThread(() -> {
                int preSize = mListeners.size();
                mListeners.add(listener);
                if (preSize == 0 && mListeners.size() == 1) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
                    BaseApplication.appContext.registerReceiver(BatteryChangedReceiver.getInstance(), intentFilter);
                }
            });
        }

        boolean isRegistered(final OnBatteryStatusChangedListener listener) {
            if (listener == null) return false;
            return mListeners.contains(listener);
        }

        void unregisterListener(final OnBatteryStatusChangedListener listener) {
            if (listener == null) return;
            ArchTaskExecutor.getInstance().postToMainThread(new Runnable() {
                @Override
                public void run() {
                    int preSize = mListeners.size();
                    mListeners.remove(listener);
                    if (preSize == 1 && mListeners.size() == 0) {
                        BaseApplication.appContext.unregisterReceiver(BatteryChangedReceiver.getInstance());
                    }
                }
            });
        }

        @Override
        public void onReceive(Context context, final Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                ArchTaskExecutor.getInstance().postToMainThread((new Runnable() {
                    @Override
                    public void run() {
                        //剩余电量
                        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                        //电池状态
                        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryStatus.UNKNOWN);
                        //满电量
                        int max = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                        int chargeStatus = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                        boolean isCharging = chargeStatus != 0;
                        boolean isUsbCharging = chargeStatus == BatteryManager.BATTERY_PLUGGED_USB;
                        boolean isACCharging = chargeStatus == BatteryManager.BATTERY_PLUGGED_AC;
                        for (OnBatteryStatusChangedListener listener : mListeners) {
                            listener.onBatteryStatusChanged(new Status(level, status));
                        }
                    }
                }));
            }
        }

        private static class LazyHolder {
            private static final BatteryChangedReceiver INSTANCE = new BatteryChangedReceiver();
        }
    }

    public interface OnBatteryStatusChangedListener {
        void onBatteryStatusChanged(Status status);
    }

    public static final class Status {
        private int level;
        @BatteryStatus
        private int status;

        Status(int level, int status) {
            this.level = level;
            this.status = status;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        @BatteryStatus
        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return batteryStatus2String(status) + ": " + level + "%";
        }

        public static String batteryStatus2String(@BatteryStatus int status) {
            if (status == BatteryStatus.DISCHARGING) {
                return "discharging";
            }
            if (status == BatteryStatus.CHARGING) {
                return "charging";
            }
            if (status == BatteryStatus.NOT_CHARGING) {
                return "not_charging";
            }
            if (status == BatteryStatus.FULL) {
                return "full";
            }
            return "unknown";
        }
    }
}
