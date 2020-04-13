package com.ccys.qyuilib.floatwindow;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.ccys.qyuilib.permission.QyPermissionCallBackLisener;
import com.ccys.qyuilib.permission.QyPermissionUtil;
import com.ccys.qyuilib.util.AppUtil;
import com.ccys.qyuilib.util.QyDisplayUtil;
import com.ccys.qyuilib.util.ToastUtils;

/**
 * 悬浮窗权限
 */
public class FloatWindowManager{
    private Context mContext;
    private WindowManager mWindowManager;
    private FloatView mFloatView;
    private int width;
    private int height;
    private static volatile FloatWindowManager instance;
    private FloatWindowManager(Context context){
        this.mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = QyDisplayUtil.dp2px(mContext,60);
        height = QyDisplayUtil.dp2px(mContext,60);
    }
    public static FloatWindowManager getInstance(Context context){
        if(instance == null){
            synchronized (FloatWindowManager.class){
                if(instance == null){
                    instance = new FloatWindowManager(context);
                }
            }
        }
        return instance;
    }
    public FloatView getFloatView(){
        return mFloatView;
    }
    public void checkFloatPermission(Activity activity, FragmentManager fm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 7.0 以上需要引导用去设置开启窗口浮动权限
            if(Settings.canDrawOverlays(activity)){
                initWindow();
            }else {
                FloatWindowPermissionsDialog.Show(fm, new FloatWindowPermissionsDialog.PermissionCallBackListener() {
                    @Override
                    public void callBack(boolean isSuccess) {
                        if(isSuccess){
                            initWindow();
                        }
                    }
                });
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 6.0 动态申请
            if(QyPermissionUtil.checkPermission(activity,new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW})){
                initWindow();
            }else {
                QyPermissionUtil.requestPermission(activity, fm, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, new QyPermissionCallBackLisener() {
                    @Override
                    public void denied() {
                        ToastUtils.showToast("悬浮窗权限获取失败", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void granted() {
                        initWindow();
                    }
                });
            }
        }
    }
    private void initWindow(){
        mFloatView = new FloatView(mContext);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                width,
                height,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 8.0 以上type需要设置成这个
            lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        lp.gravity = Gravity.NO_GRAVITY;
        mFloatView.setWindowLayoutParams(lp);
        mFloatView.setWindowManager(mWindowManager);
        mWindowManager.addView(mFloatView,lp);
    }

    // 移除window
    public void stop() {
        if (mWindowManager != null && mFloatView != null && mFloatView.getWindowId() != null) {
            mWindowManager.removeView(mFloatView);
        }
    }
    public void restart(){
        if (mWindowManager != null && mFloatView != null && mFloatView.getWindowId() == null) {
            mWindowManager.addView(mFloatView,mFloatView.getWindowLayoutParams());
        }
    }
}
