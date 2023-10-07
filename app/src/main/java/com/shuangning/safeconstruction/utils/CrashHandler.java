package com.shuangning.safeconstruction.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.shuangning.safeconstruction.BuildConfig;
import com.shuangning.safeconstruction.utils.archcore.ArchTaskExecutor;
import com.shuangning.safeconstruction.utils2.MyLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕捉类
 * 保存错误信息和设备信息
 * Created by Administrator o n 2016/1/30.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    String TAG = CrashHandler.class.getCanonicalName();
    //系统默认的uncaughtExceptionHandler
    Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    Context mContext;
    //保存错误信息和设备信息
    Map<String, String> mErrorMap = new HashMap();
    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmsssss");
    static CrashHandler mCrashHandler;

    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            mCrashHandler = new CrashHandler();
        }
        return mCrashHandler;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mUncaughtExceptionHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mUncaughtExceptionHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }
            Log.e(TAG, "error : ", ex);
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        ArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                //收集设备参数信息
                collectDeviceInfo(mContext);
                //保存日志文件
                if(BuildConfig.DEBUG) {
                    saveCrashInfoFile(ex);
                }
                upload(ex);
            }
        });
        return true;
    }

    private void upload(Throwable ex){
        StringBuilder sb = new StringBuilder();
        sb.append("model:")
                .append(APPUtils.getDeviceModel())
                .append(" version:")
                .append(APPUtils.getOSVersion())
                .append(" error: ")
                .append(ex.getStackTrace());
    }
    /**
     * 收集设备参数信息
     * @param context
     */
    private void collectDeviceInfo(Context context) {

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                int versionCode = packageInfo.versionCode;
                mErrorMap.put("versionName", versionName);
                mErrorMap.put("versionCode", versionCode + "");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info=", e);
        }
        Field[] fields = Build.class.getFields();
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                mErrorMap.put(f.getName(), f.get(null).toString());
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "an error occured when collect crash info", e);
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfoFile(Throwable ex) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : mErrorMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuffer.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        stringBuffer.append(result);
        MyLog.INSTANCE.e(stringBuffer.toString());
        try {
            long timestamp = System.currentTimeMillis();
            String time = format.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = (PathUtils.getExternalAppDataPath()+ "/crash/") ;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(stringBuffer.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }

        return null;
    }
}
