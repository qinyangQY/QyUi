package com.ccys.qyuilib.service;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.ccys.qyuilib.base.QyBaseApplication;
import com.ccys.qyuilib.floatwindow.FloatWindowManager;
import com.ccys.qyuilib.floatwindow.LogEntity;
import com.ccys.qyuilib.util.AppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class FloatWindowService extends Service {
    private FloatWindowBroadcast broadcast;
    private FloatWindowManager mFloatWindowManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new FloatBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFloatWindowManager = FloatWindowManager.getInstance(getApplicationContext());
        broadcast = new FloatWindowBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(QyBaseApplication.APP_ACTIVE_STATUS_ACTION);
        registerReceiver(broadcast,new IntentFilter(filter));
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(broadcast);
        broadcast = null;
        mFloatWindowManager.stop();
    }
    @Subscribe
    public void OnEvent(LogEntity logEntity){
        if(logEntity.getAction().equals(AppUtil.getAppPackageName()+"_eventBus")){
            if(mFloatWindowManager != null && mFloatWindowManager.getFloatView() != null){
                mFloatWindowManager.getFloatView().addData(logEntity);
            }
        }
    }
    public class FloatBind extends Binder {
        public void checkFloatPermission(Activity activity, FragmentManager fm){
            if(mFloatWindowManager != null){
                mFloatWindowManager.checkFloatPermission(activity,fm);
            }
        }
    }
    public class FloatWindowBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
             if(action.equals(QyBaseApplication.APP_ACTIVE_STATUS_ACTION)){//前后台切换
                   int status = intent.getIntExtra("status",0);
                   if(status == 0){//app在后台
                       if(mFloatWindowManager != null){
                           mFloatWindowManager.stop();
                       }
                   }else {//app在前台
                       if(mFloatWindowManager != null){
                           mFloatWindowManager.restart();
                       }
                   }
              }
        }
    }
}
