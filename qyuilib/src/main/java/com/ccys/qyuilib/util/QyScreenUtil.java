package com.ccys.qyuilib.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.ccys.qyuilib.base.QyBaseApplication;
import com.ccys.qyuilib.interfaces.QyDeviceHasNavigationBarStatusListener;

/**
 * 包名：com.qinyang.qyuilib.util
 * 创建人：秦洋
 * 创建时间：2018/12/23
 * 屏幕相关的工具类
 */
public class QyScreenUtil {
    //获取屏幕的尺寸(不包含虚拟按键的高度)
    public static int[] getScreenSize(Context context){
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.
                        WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int[] screenXY = new int[2];
        screenXY[0] = outMetrics.widthPixels;
        screenXY[1] = outMetrics.heightPixels;
        return screenXY;
    }

    //获取屏幕的尺寸(包含虚拟按键的高度)
    public static int[] getWindowSize(Context context){
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.
                        WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        display.getRealSize(outPoint);
        int[] screenXY = new int[2];
        screenXY[0] = outPoint.x;
        screenXY[1] = outPoint.y;
        return screenXY;
    }
    //获取屏幕的尺寸(不包含虚拟按键的高度)
    public static int[] getScreenSize(){
        WindowManager windowManager =
                (WindowManager) QyBaseApplication.getContext().getSystemService(Context.
                        WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int[] screenXY = new int[2];
        screenXY[0] = outMetrics.widthPixels;
        screenXY[1] = outMetrics.heightPixels;
        return screenXY;
    }

    //获取屏幕的尺寸(包含虚拟按键的高度)
    public static int[] getWindowSize(){
        WindowManager windowManager =
                (WindowManager) QyBaseApplication.getContext().getSystemService(Context.
                        WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        display.getRealSize(outPoint);
        int[] screenXY = new int[2];
        screenXY[0] = outPoint.x;
        screenXY[1] = outPoint.y;
        return screenXY;
    }
    public static int getStatusBarHeight(Context context){
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    //获取是否存在NavigationBar(true 存在 false不存在)
    public static void checkDeviceHasNavigationBar(final Activity activity, final QyDeviceHasNavigationBarStatusListener deviceHasNavigationBarStatusListener) {
       final View root = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                 int rootHeight = root.getMeasuredHeight();
                if(rootHeight == getWindowSize(activity)[1]){//不存在底部导航栏
                    if(deviceHasNavigationBarStatusListener!= null){
                        deviceHasNavigationBarStatusListener.navigationBarShowStatus(false,0);
                    }
                }else {//存在
                    if(deviceHasNavigationBarStatusListener!= null){
                        deviceHasNavigationBarStatusListener.navigationBarShowStatus(true,getNavigationBarHeight(activity));
                    }
                }
                if(rootHeight > 0){
                    root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }
}
