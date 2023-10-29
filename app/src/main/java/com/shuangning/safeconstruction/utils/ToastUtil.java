package com.shuangning.safeconstruction.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.shuangning.safeconstruction.R;
import com.shuangning.safeconstruction.base.BaseApplication;


/**
 * 
 */
public class ToastUtil {
    private static Toast toast;
    private static Toast toast2;

    private static Toast initToast(CharSequence message, int duration) {
        if (toast == null) {
            //makeText中不设置文本是为了防止在小米系统中显示toast时出现app名称的情况
            toast = Toast.makeText(BaseApplication.appContext, "", duration);
            toast.setText(message);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
//        hookToastHandler();
        return toast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        initToast(message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
        initToast(BaseApplication.appContext.getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void
    showLong(CharSequence message) {
        initToast(message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        initToast(BaseApplication.appContext.getResources().getText(strResId), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        initToast(message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(Context context, int strResId, int duration) {
        initToast(context.getResources().getText(strResId), duration).show();
    }

    /**
     * 显示有image的toast
     *
     * @return
     */
    public static Toast showCustomToast(@StringRes int resID) {
        String text = UIUtils.getString(resID);
        return showCustomToast(text);
    }

    public static Toast showCustomToast(String text) {
        if (toast2 == null) {
            toast2 = new Toast(BaseApplication.appContext);
        }
        View view = LayoutInflater.from(BaseApplication.appContext).inflate(R.layout.toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_toast);
        tv.setText(TextUtils.isEmpty(text) ? "" : text);
        toast2.setDuration(Toast.LENGTH_SHORT);
        toast2.setView(view);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
        return toast2;
    }
    /**
     * 显示有image的toast
     *
     * @return
     */
//    public static Toast showToastWithResImg(final String tvStr, int resID, final int imageResource) {
//        if (toast2 == null) {
//            toast2 = new Toast(BaseApplication.appContext);
//        }
//        View view = LayoutInflater.from(BaseApplication.appContext).inflate(resID, null);
//        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
//        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
//        ImageView iv = (ImageView) view.findViewById(R.id.toast_custom_iv);
//        if (imageResource > 0) {
//            iv.setVisibility(View.VISIBLE);
//            iv.setImageResource(imageResource);
//        } else {
//            iv.setVisibility(View.GONE);
//        }
//        toast2.setView(view);
//        toast2.setGravity(Gravity.CENTER, 0, 0);
//        toast2.show();
//        return toast2;
//
//    }


    static class SafeHandlerWrapper extends Handler{
        private Handler mHandler;
        public SafeHandlerWrapper(Handler handler){
            mHandler = handler;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            }catch (Throwable e){

            }
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mHandler.handleMessage(msg);
        }
    }

    //此处通过代理和反射将Toast中内部类Tn的mHandler替换成SafeHandlerWrapper
    // 是为了防止显示toast时ui线程阻塞导致的BadTokenException异常造成闪退
//    private static void hookToastHandler(){
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
//
//            try {
//                Field sField_TN = null;
//                sField_TN = Toast.class.getDeclaredField("mTN");
//                sField_TN.setAccessible(true);
//                Field sField_TN_Handler = sField_TN.getType().getDeclaredField("mHandler");
//                sField_TN_Handler.setAccessible(true);
//                Object tn = sField_TN.get(toast);
//                Handler preHandler = (Handler) sField_TN_Handler.get(tn);
//                sField_TN_Handler.set(tn, new SafeHandlerWrapper(preHandler));
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
}
