package com.ccys.qyuilib.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.fragment.app.FragmentManager;

import java.util.HashMap;

/**
 * @ProjectName: QyUi
 * @Package: com.ccys.qyuilib.permission
 * @ClassName: QyPermissionUtil
 * @描述: java类作用描述
 * @作者: 秦洋
 * @日期: 2019-12-03 09:20
 */
public class QyPermissionUtil {
    //选择图片需要的权限
    public static String[] camera_permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    //操作文件需要的权限
    public static String[] file_permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    //录制视频需要的权限
    public static String[] video_permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
    //录音需要的权限
    public static String[] viceo_permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};

    //获取通讯录需要的权限
    public static String[] contact_permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS};
    //拨打电话需要的权限
    public static String[] callphone_permissions = new String[]{Manifest.permission.CALL_PHONE};
    //写入联系人
    public static String[] addcontact_permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS};
    //定位
    public static String[] location_permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

    /**
     * @method  检查动态权限
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public static boolean checkPermission(Context context,String[] permissions){
           if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
               for (String p:permissions){
                   if(context.checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED){
                       return false;
                   }
               }
               return true;
           }else {
               return true;
           }
    }
    /**
     * @method  申请动态权限
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public static void requestPermission(Context context,FragmentManager manager,String[] permissions,QyPermissionCallBackLisener lisener){
        QyPermissionDialog dialog = new QyPermissionDialog(context,manager,permissions,lisener);
        dialog.show(manager,"qy_permission_dialog");
    }
}
