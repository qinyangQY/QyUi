package com.ccys.qyuilib.floatwindow;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.dialog.QyBaseFragmentDialog;
import com.ccys.qyuilib.dialog.QyViewHolder;
import com.ccys.qyuilib.util.AppUtil;
import com.ccys.qyuilib.util.ToastUtils;

public class FloatWindowPermissionsDialog extends QyBaseFragmentDialog {
   private final int requestCode = 1001;
   private PermissionCallBackListener mPermissionCallBackListener;
    protected FloatWindowPermissionsDialog(PermissionCallBackListener mPermissionCallBackListener) {
        super(0, 0, 0);
        this.mPermissionCallBackListener = mPermissionCallBackListener;
    }
   public static void Show(FragmentManager fm,PermissionCallBackListener mPermissionCallBackListener){
       FloatWindowPermissionsDialog dialog = new FloatWindowPermissionsDialog(mPermissionCallBackListener);
       dialog.show(fm,"FloatWindowPermissionsDialog");
   }
    @Override
    public void onBindView(QyViewHolder viewHolder) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.colorTransparent)));
        setCancelable(false);
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + AppUtil.getAppPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode) {
            if (!Settings.canDrawOverlays(getActivity())) {
                ToastUtils.showToast("设置权限拒绝", Toast.LENGTH_LONG);
                dismiss();
                mPermissionCallBackListener.callBack(false);
            } else {
                dismiss();
                mPermissionCallBackListener.callBack(true);
            }
        }
    }
    public interface PermissionCallBackListener{
        void callBack(boolean isSuccess);
    }
}
