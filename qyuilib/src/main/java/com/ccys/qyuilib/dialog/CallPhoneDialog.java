package com.ccys.qyuilib.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.permission.QyPermissionCallBackLisener;
import com.ccys.qyuilib.permission.QyPermissionUtil;
import com.ccys.qyuilib.util.ToastUtils;


/**
 * 包名：com.qinyang.qyuilib.dialog
 * 创建人：秦洋
 * 创建时间：2019/2/28
 * 拨打电话的对话框
 */
public class CallPhoneDialog extends QyBaseDialog {
    private String tip;
    private String phone;
    private OnEventLisener onEventLisener;
    private String title;
    private Context context;
    private FragmentManager fm;

    public CallPhoneDialog(Context context, FragmentManager fm,int themeResId, int layoutId, String title, String tip, String phone, OnEventLisener onEventLisener) {
        super(context, themeResId, layoutId);
        this.fm = fm;
        this.tip = tip;
        this.phone = phone;
        this.title = title;
        this.onEventLisener = onEventLisener;
        this.context = context;
    }

    public static void showAndroid(final Context context, final FragmentManager fm, final String title, final String phone, final OnEventLisener onEventLisener) {
        if (QyPermissionUtil.checkPermission(context, QyPermissionUtil.callphone_permissions)) {
            CallPhoneDialog dialog = new CallPhoneDialog(context,fm, R.style.qy_normal_dialog, R.layout.android_confirm_dialog_layout, title, null, phone, onEventLisener);
            dialog.setGravity(Gravity.CENTER);
            dialog.show();
        } else {
            QyPermissionUtil.requestPermission((Activity) context, fm, QyPermissionUtil.callphone_permissions, new QyPermissionCallBackLisener() {

                @Override
                public void denied() {
                    ToastUtils.showToast("请在设置中打开必要权限", Toast.LENGTH_SHORT);
                }

                @Override
                public void granted() {
                    CallPhoneDialog dialog = new CallPhoneDialog(context,fm, R.style.qy_normal_dialog, R.layout.android_confirm_dialog_layout, title, null, phone, onEventLisener);
                    dialog.setGravity(Gravity.CENTER);
                    dialog.show();
                }
            });
        }
    }

    public static void showAndroid(final Context context, final FragmentManager fm, final String title, final String tip, final String phone, final OnEventLisener onEventLisener) {
        if (QyPermissionUtil.checkPermission(context, QyPermissionUtil.callphone_permissions)) {
            CallPhoneDialog dialog = new CallPhoneDialog(context, fm,R.style.qy_normal_dialog, R.layout.android_confirm_dialog_layout, title, tip, phone, onEventLisener);
            dialog.setGravity(Gravity.CENTER);
            dialog.show();
        } else {
            QyPermissionUtil.requestPermission(context, fm, QyPermissionUtil.callphone_permissions, new QyPermissionCallBackLisener() {
                @Override
                public void denied() {
                    ToastUtils.showToast("请在设置中打开必要权限", Toast.LENGTH_SHORT);
                }

                @Override
                public void granted() {
                    CallPhoneDialog dialog = new CallPhoneDialog(context,fm, R.style.qy_normal_dialog, R.layout.android_confirm_dialog_layout, title, tip, phone, onEventLisener);
                    dialog.setGravity(Gravity.CENTER);
                    dialog.show();
                }
            });
        }
    }

    public static void showAndroid(final Context context, final FragmentManager fm, final String title, final String tip, final String phone) {
        if (QyPermissionUtil.checkPermission(context, QyPermissionUtil.callphone_permissions)) {
            CallPhoneDialog dialog = new CallPhoneDialog(context, fm,R.style.qy_normal_dialog, R.layout.android_confirm_dialog_layout, title, tip, phone, null);
            dialog.setGravity(Gravity.CENTER);
            dialog.show();
        } else {
            QyPermissionUtil.requestPermission(context, fm, QyPermissionUtil.callphone_permissions, new QyPermissionCallBackLisener() {
                @Override
                public void denied() {
                    ToastUtils.showToast("请在设置中打开必要权限", Toast.LENGTH_SHORT);
                }

                @Override
                public void granted() {
                    CallPhoneDialog dialog = new CallPhoneDialog(context, fm,R.style.qy_normal_dialog, R.layout.android_confirm_dialog_layout, title, tip, phone, null);
                    dialog.setGravity(Gravity.CENTER);
                    dialog.show();
                }
            });
        }
    }

    public static void showIos(final Context context, final FragmentManager fm, final String title, final String phone, final OnEventLisener onEventLisener) {
        if (QyPermissionUtil.checkPermission(context, QyPermissionUtil.callphone_permissions)) {
            CallPhoneDialog dialog = new CallPhoneDialog(context, fm,R.style.qy_normal_dialog, R.layout.ios_confirm_dialog_layout, title, null, phone, onEventLisener);
            dialog.setGravity(Gravity.CENTER);
            dialog.show();
        } else {
            QyPermissionUtil.requestPermission(context, fm, QyPermissionUtil.callphone_permissions, new QyPermissionCallBackLisener() {
                @Override
                public void denied() {
                    ToastUtils.showToast("请在设置中打开必要权限", Toast.LENGTH_SHORT);
                }

                @Override
                public void granted() {
                    CallPhoneDialog dialog = new CallPhoneDialog(context,fm, R.style.qy_normal_dialog, R.layout.ios_confirm_dialog_layout, title, null, phone, onEventLisener);
                    dialog.setGravity(Gravity.CENTER);
                    dialog.show();
                }
            });
        }
    }

    public static void showIos(final Context context, final FragmentManager fm, final String title, final String tip, final String phone, final OnEventLisener onEventLisener) {
        if (QyPermissionUtil.checkPermission(context, QyPermissionUtil.callphone_permissions)) {
            CallPhoneDialog dialog = new CallPhoneDialog(context,fm, R.style.qy_normal_dialog, R.layout.ios_confirm_dialog_layout, title, tip, phone, onEventLisener);
            dialog.setGravity(Gravity.CENTER);
            dialog.show();
        } else {
            QyPermissionUtil.requestPermission(context, fm, QyPermissionUtil.callphone_permissions, new QyPermissionCallBackLisener() {
                @Override
                public void denied() {
                    ToastUtils.showToast("请在设置中打开必要权限", Toast.LENGTH_SHORT);
                }

                @Override
                public void granted() {
                    CallPhoneDialog dialog = new CallPhoneDialog(context,fm, R.style.qy_normal_dialog, R.layout.ios_confirm_dialog_layout, title, tip, phone, onEventLisener);
                    dialog.setGravity(Gravity.CENTER);
                    dialog.show();
                }
            });
        }
    }

    public static void showIos(final Context context, final FragmentManager fm, final String title, final String tip, final String phone) {
        if (QyPermissionUtil.checkPermission(context, QyPermissionUtil.callphone_permissions)) {
            CallPhoneDialog dialog = new CallPhoneDialog(context, fm,R.style.qy_normal_dialog, R.layout.ios_confirm_dialog_layout, title, tip, phone, null);
            dialog.setGravity(Gravity.CENTER);
            dialog.show();
        } else {
            QyPermissionUtil.requestPermission(context, fm, QyPermissionUtil.callphone_permissions, new QyPermissionCallBackLisener() {
                @Override
                public void denied() {
                    ToastUtils.showToast("请在设置中打开必要权限", Toast.LENGTH_SHORT);
                }

                @Override
                public void granted() {
                    CallPhoneDialog dialog = new CallPhoneDialog(context,fm, R.style.qy_normal_dialog, R.layout.ios_confirm_dialog_layout, title, tip, phone, null);
                    dialog.setGravity(Gravity.CENTER);
                    dialog.show();
                }
            });
        }
    }

    @Override
    public void onBindView(QyViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_dialog_title, TextUtils.isEmpty(title) ? "拨打电话" : title);
        viewHolder.setText(R.id.tv_content, TextUtils.isEmpty(tip) ? phone : tip + phone);
        viewHolder.setOnClickLisener(R.id.tv_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallPhoneDialog.this.dismiss();
                if (onEventLisener != null) {
                    onEventLisener.OnEvent(0);
                }
            }
        });
        viewHolder.setOnClickLisener(R.id.tv_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallPhoneDialog.this.dismiss();
                if (onEventLisener != null) {
                    onEventLisener.OnEvent(1);
                }
                callPhone(context, fm,phone);
            }
        });
    }

    public static void callPhone(final Context context, FragmentManager fm, final String phone) {
        if (QyPermissionUtil.checkPermission(context, QyPermissionUtil.callphone_permissions)) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            context.startActivity(intent);
        } else {
            QyPermissionUtil.requestPermission(context, fm, QyPermissionUtil.callphone_permissions, new QyPermissionCallBackLisener() {
                @Override
                public void denied() {

                }

                @Override
                public void granted() {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + phone);
                    intent.setData(data);
                    if (ContextCompat.checkSelfPermission(context,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(intent);
                }
            });
        }

    }



    public interface OnEventLisener{
        void OnEvent(int type);
    }

}
