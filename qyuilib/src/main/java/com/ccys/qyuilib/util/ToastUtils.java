package com.ccys.qyuilib.util;

import android.content.Context;
import android.widget.Toast;

import com.ccys.qyuilib.base.QyBaseApplication;

public class ToastUtils {
    private static Toast mToast = null;
    public static void showToast(String text, int duration) {
        Context context= QyBaseApplication.getContext();
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void showToast (int strId, int duration) {
        Context context= QyBaseApplication.getContext();
        showToast (context.getString(strId), duration);
    }
}
