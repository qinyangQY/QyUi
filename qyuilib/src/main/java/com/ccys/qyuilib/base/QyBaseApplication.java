package com.ccys.qyuilib.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ccys.qyuilib.util.AppUtil;
import com.ccys.qyuilib.util.QyAppManagerUtil;
import com.ccys.qyuilib.util.QyAppUtil;

/**
 * @ProjectName: ZhenMei
 * @Package: com.ccys.qyuilib.base
 * @ClassName: QyBaseApplication
 * @描述: java类作用描述
 * @作者: 秦洋
 * @日期: 2019-12-12 09:47
 */
public abstract class QyBaseApplication extends Application {
    private static Context context;
    private QyAppManagerUtil appManager;
    public static boolean isDebug;//debug状态是否开启
    public static boolean isCreateFloatWindow;//是否创建的悬浮窗（防止对此创建）
    //app的活动状态的广播 前台和后台的切换
    public static String APP_ACTIVE_STATUS_ACTION;
    private int count;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        appManager = QyAppManagerUtil.getAppManager();
        InitMultiApp();
        if (QyAppUtil.isMainProcess(this)) {
            InitCurrentApp();
            registerActivityLifecycleCallbacks(new QyActivityLifecycleCallbacks());
        }
        APP_ACTIVE_STATUS_ACTION = AppUtil.getAppPackageName()+"app_active_status";
    }
    public static Context getContext() {
        return context;
    }

    public QyAppManagerUtil getAppManager() {
        return appManager;
    }

    //初始化app(只会被当前进程初始化)
    protected abstract void InitCurrentApp();

    //会被多个进程初始化
    protected abstract void InitMultiApp();

    /**
     * 监听程序是在前台还是后台
     */
    private class QyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            if(count == 0){
                Bundle bundle = new Bundle();
                bundle.putInt("status",1);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setAction(APP_ACTIVE_STATUS_ACTION);
                sendBroadcast(intent);
            }
            count ++;
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {

        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
            count --;
            if(count == 0){
                Bundle bundle = new Bundle();
                bundle.putInt("status",0);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setAction(APP_ACTIVE_STATUS_ACTION);
                sendBroadcast(intent);
            }
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }
    }
}
