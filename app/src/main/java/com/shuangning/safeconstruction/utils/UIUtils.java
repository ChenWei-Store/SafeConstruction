package com.shuangning.safeconstruction.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.shuangning.safeconstruction.base.BaseApplication;


/**
 * Created by ZHT on 2017/4/17.
 * 有关UI的工具类，如获取资源(颜色，字符串，drawable等)，
 * 屏幕宽高，dp与px转换
 */

public class UIUtils {

    private static Context getContext() {
        return BaseApplication.appContext;
    }

    private static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取颜色值
     *
     * @param resId 颜色资源id
     * @return 颜色值
     */
    public static int getColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    public static int getColor(String color) {
        return Color.parseColor(color);
    }

    /**
     * 获取Drawable
     *
     * @param resTd Drawable资源id
     * @return Drawable
     */
    public static Drawable getDrawable(int resTd) {
        return ContextCompat.getDrawable(getContext(), resTd);
    }

    /**
     * 获取字符串
     *
     * @param resId 字符串资源id
     * @return 字符串
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取字符串数组
     *
     * @param resId 数组资源id
     * @return 字符串数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 将dp值转换为px值
     *
     * @param dp 需要转换的dp值
     * @return px值
     */
    public static float dp2px(float dp) {
        return getResources().getDisplayMetrics().density * dp + 0.5f;
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    /**
     * 将px值转换为dp值
     *
     * @param px 需要转换的px值
     * @return dp值
     */
    public static int px2dp(float px) {
        return (int) (px / getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    public static DisplayMetrics getDisplayMetrics() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics;
    }

    /**
     * 获取屏幕宽度 像素值
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度 像素值
     *
     * @return 屏幕高度
     */
    public static int getScreenHegith() {
        return getDisplayMetrics().heightPixels;
    }

    /**
     * 给图片设置tint
     */
    public static Drawable setTintDrawable(Context ctx, int drawableId, int tintColorId){
        Drawable drawable = getDrawable(drawableId);
        // 设置图片为绿色
        DrawableCompat.setTint(drawable, getColor(tintColorId));
        return drawable;
    }

    public static void setTextLeftDrawable(TextView tv, int resId){
        Drawable drawable = getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(drawable, null, null, null);
    }
}
