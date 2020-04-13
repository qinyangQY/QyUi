package com.ccys.qyuilib.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.ccys.qyuilib.R;
import com.ccys.qyuilib.dialog.QyBaseFragmentDialog;
import com.ccys.qyuilib.dialog.QyViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: QyUi
 * @Package: com.ccys.qyuilib.permission
 * @ClassName: QyPermissionDialog
 * @描述: 动态权限的对话框
 * @作者: 秦洋
 * @日期: 2019-12-03 17:49
 */
public class QyPermissionDialog extends QyBaseFragmentDialog implements QyPermissionTipsDialog.ClickCallBackLisener {

    private String[] permissions;
    private QyPermissionCallBackLisener lisener;
    private Context context;
    private final int REQUEST_CODE = 100;
    private QyPermissionTipsDialog dialog;
    private FragmentManager manager;
    private boolean isTryCheckPermission;

    protected QyPermissionDialog(Context context, FragmentManager manager, String[] permissions, QyPermissionCallBackLisener lisener) {
        super(0, 0, 0);
        this.permissions = permissions;
        this.lisener = lisener;
        this.context = context;
        this.manager = manager;
        this.dialog = new QyPermissionTipsDialog(this);
    }

    @Override
    public void onBindView(QyViewHolder viewHolder) {
        isTryCheckPermission = false;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.colorTransparent)));
        setCancelable(false);
        requestPermission();
    }

    private void requestPermission() {
        List<String> permissionList = new ArrayList<>();
        for (String p : permissions) {
            if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(p);
            }
        }
        if (permissionList.size() > 0) {
            String[] tempPermission = new String[permissionList.size()];
            for (int i = 0; i < permissionList.size(); i++) {
                tempPermission[i] = permissionList.get(i);
            }
            this.requestPermissions(tempPermission, REQUEST_CODE);
        } else {
            lisener.granted();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isTryCheckPermission) {
            if (QyPermissionUtil.checkPermission(context, permissions)) {
                dismiss();
                if (lisener != null) {
                    lisener.granted();
                }
            } else {
                dismiss();
                if (lisener != null) {
                    lisener.denied();
                }
            }
        } else {
            isTryCheckPermission = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermission = true;
        for (int rs : grantResults) {
            if (rs != PackageManager.PERMISSION_GRANTED) {
                isPermission = false;
                break;
            }
        }
        if (isPermission) {//权限申请成功
            dismiss();
            if (lisener != null) {
                lisener.granted();
            }
        } else {//用户拒绝权限
            isTryCheckPermission = false;
            dialog.show(manager, "qy_permission_tips");
        }
    }

    @Override
    public void cancel() {
        dismiss();
        if (lisener != null) {
            lisener.denied();
        }
    }

    /**
     * @return
     * @method 打开设置界面设置权限
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     */
    @Override
    public void confirm() {
        isTryCheckPermission = true;
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);
    }
}
