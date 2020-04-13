package com.ccys.qyuilib.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.ccys.qyuilib.base.QyBaseApplication;

import java.io.File;
import java.util.List;

/**
 * 包名：com.qinyang.qyuilib.util
 * 创建人：秦洋
 * 创建时间：2018/12/31
 * 整个app需要的工具类
 */
public class AppUtil {

    private static final long HOME_VALID_TIME = 2000;//有效时间2秒
    private static final long CLICK_VALID_TIME = 800;//有效时间800毫秒
    public static long FirstEventTime;//记录上一次的时间

    public static void BackHomeApp(Context context) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - FirstEventTime > HOME_VALID_TIME) {
            Toast.makeText(context, "在点击一次返回桌面", Toast.LENGTH_SHORT).show();
        } else {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            context.startActivity(home);
        }
        FirstEventTime = currentTime;
    }

    //返回true执行，false不能执行
    public static boolean isFastDoubleClick() {
        long currentTime = System.currentTimeMillis();
        long timeD = currentTime - FirstEventTime;
        FirstEventTime = currentTime;
        return timeD >= CLICK_VALID_TIME;
    }

    /**
     * 获取当前应用程序的包名
     *
     * @param context 上下文对象
     * @return 返回包名
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }
    public static String getAppPackageName(){
        return QyBaseApplication.getContext().getPackageName();
    }
    public static String getImagPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/" + getAppPackageName(QyBaseApplication.getContext()) + "/image";
    }

    public static String getFilePath() {
        return Environment.getExternalStorageDirectory().getPath() + "/" + getAppPackageName(QyBaseApplication.getContext()) + "/file";
    }

    public static String getVideoPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/" + getAppPackageName(QyBaseApplication.getContext()) + "/video";
    }

    public static String getVoicePath() {
        return Environment.getExternalStorageDirectory().getPath() + "/" + getAppPackageName(QyBaseApplication.getContext()) + "/voice";
    }

    public static String getCachePath() {
        return Environment.getExternalStorageDirectory().getPath() + "/" + getAppPackageName(QyBaseApplication.getContext()) + "/cache";
    }
    public static String getAppFilePath(){
        return Environment.getExternalStorageDirectory().getPath() + "/" + getAppPackageName(QyBaseApplication.getContext());
    }
    //创建app运行所需的必要文件路径
    public static void CreateFilePath() {
        if (!(new File(getImagPath())).exists()) {
            if (!(new File(getImagPath()).isDirectory())) {
                (new File(getImagPath())).mkdirs();
            }
        }
        if (!(new File((getFilePath()))).exists()) {
            if (!(new File(getFilePath())).isDirectory()) {
                (new File(getFilePath())).mkdirs();
            }
        }
        if (!(new File((getVideoPath()))).exists()) {
            if (!(new File(getVideoPath())).isDirectory()) {
                (new File(getVideoPath())).mkdirs();
            }
        }
        if (!(new File((getVoicePath()))).exists()) {
            if (!(new File(getVoicePath())).isDirectory()) {
                (new File(getVoicePath())).mkdirs();
            }
        }
        if (!(new File((getCachePath()))).exists()) {
            if (!(new File(getCachePath())).isDirectory()) {
                (new File(getCachePath())).mkdirs();
            }
        }
    }

    //获取app内部的缓存目录
    public static String getAppCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    //获取进程名称
    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }

    //判断当前进程是不是主经进程
    public static boolean isMainProcess(Context context) {
        if (!TextUtils.isEmpty(getProcessName(context)) && !TextUtils.isEmpty(context.getPackageName()) && context.getPackageName().equals(getProcessName(context))) {
            return true;
        } else {
            return false;
        }
    }


    //清除app的缓存目录
    public static void cleanAppFileCache(Context context) {
        deleteDirWihtFile(context, new File(getImagPath()));
        deleteDirWihtFile(context, new File(getFilePath()));
        deleteDirWihtFile(context, new File(getVideoPath()));
        deleteDirWihtFile(context, new File(getVoicePath()));
        deleteDirWihtFile(context, new File(getCachePath()));
        deleteDirWihtFile(context, new File(getAppCachePath(context)));
    }

    public static void deleteDirWihtFile(Context context, File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete(); // 删除所有文件
                if (file.getName().indexOf(".jpeg") >= 0 || file.getName().indexOf(".jpg") >= 0 || file.getName().indexOf(".gif") >= 0 ||
                        file.getName().indexOf(".png") >= 0 || file.getName().indexOf(".mp4") >= 0 || file.getName().indexOf(".mp3") >= 0) {
                    // 最后通知图库更新
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
                }
            } else if (file.isDirectory()) {
                deleteDirWihtFile(context, file); // 递规的方式删除文件夹
            }
        }
    }
}
