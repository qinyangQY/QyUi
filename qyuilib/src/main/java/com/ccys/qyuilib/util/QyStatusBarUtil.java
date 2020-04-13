package com.ccys.qyuilib.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;

/**
 * 包名：com.qinyang.qyuilib.util
 * 创建人：秦洋
 * 创建时间：2018/12/23
 * 修改状态栏的颜色字体等
 * 只适用于5.0以上
 */
public class QyStatusBarUtil {

    /**
     * @param activity
     * @param colorRes 颜色的资源引用id主要用于设置渐变色
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setStateBarBgColor(Activity activity, int colorRes, boolean isBlack){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(colorRes));
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setStateBarTextColor(activity,isBlack);
        //让view不根据系统窗口来调整自己的布局 //关键
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            int[] screenXY = new int[2];
            mChildView.getLocationInWindow(screenXY);
            if(mChildView.getMeasuredHeight() > 0){
                mChildView.setPadding(0, QyScreenUtil.getStatusBarHeight(activity),0,0);
            }
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }
    /**
     * @param activity
     * @param colorRes 颜色的资源引用id主要用于设置渐变色
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setAnimationStateBarBgColor(Activity activity, int colorRes, boolean isBlack){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(colorRes);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setStateBarTextColor(activity,isBlack);
        //让view不根据系统窗口来调整自己的布局 //关键
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            int[] screenXY = new int[2];
            mChildView.getLocationInWindow(screenXY);
            if(mChildView.getMeasuredHeight() > 0){
                mChildView.setPadding(0, QyScreenUtil.getStatusBarHeight(activity),0,0);
            }
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }
    /**
     *
     * @param activity
     * @param color 颜色值
     */
    public static void setStateBarBgColor(Activity activity, String color, boolean isBlack){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(color));
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setStateBarTextColor(activity,isBlack);
        //让view不根据系统窗口来调整自己的布局 //关键
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            int[] screenXY = new int[2];
            mChildView.getLocationInWindow(screenXY);
            if(mChildView.getMeasuredHeight() > 0){
                mChildView.setPadding(0, QyScreenUtil.getStatusBarHeight(activity),0,0);
            }
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }
    /**
     * 设置状态栏为透明
     * @param activity
     */
    public static void setStateBarTranslucent(Activity activity, boolean isBlack){
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        if(isBlack){
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        //让view不根据系统窗口来调整自己的布局 //关键
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            int[] screenXY = new int[2];
            mChildView.getLocationInWindow(screenXY);
            if(mChildView.getMeasuredHeight() > 0){
                mChildView.setPadding(0,0,0,0);
            }
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }
    /**
     * 设置状态栏为透明
     * @param activity
     * View view 用于占据状态栏的view
     * isBlack 是否设置成黑色字体 true 黑色 false白色
     */
    public static void setStateBarTranslucent(Activity activity, View view, boolean isBlack){
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        if(isBlack){
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        view.setPadding(0, QyScreenUtil.getStatusBarHeight(activity),0,0);
        //让view不根据系统窗口来调整自己的布局 //关键
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            int[] screenXY = new int[2];
            mChildView.getLocationInWindow(screenXY);
            if(mChildView.getMeasuredHeight() > 0){
                mChildView.setPadding(0,0,0,0);
            }
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }
    public static void setStateBarTextColor(Activity activity, boolean isBlack){
        Window window = activity.getWindow();
        if(isBlack){
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

}
