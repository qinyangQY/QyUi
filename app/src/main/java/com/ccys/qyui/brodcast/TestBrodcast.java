package com.ccys.qyui.brodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ccys.qyuilib.dialog.QyAlertDialog;
import com.ccys.qyuilib.dialog.SystemConfirmDialog;

public class TestBrodcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("map","广播启动----");
        SystemConfirmDialog.Show(context, "系统提示", "测试", new SystemConfirmDialog.OnEventListener() {
            @Override
            public void onClick() {

            }
        });
    }
}
