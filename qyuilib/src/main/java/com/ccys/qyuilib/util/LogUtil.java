package com.ccys.qyuilib.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.ccys.qyuilib.base.QyBaseApplication;
import com.ccys.qyuilib.floatwindow.FloatWindowManager;
import com.ccys.qyuilib.floatwindow.LogEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * 打印log的util
 */
public class LogUtil {
    /**
     * 将log信息发送到悬浮界面
     * @param
     */
    public static void sendLog(String url,String headers,String params){
        if(QyBaseApplication.isDebug){
            StringBuffer buffer = new StringBuffer();
            buffer.append("请求地址：="+url+"\n");
            if(!TextUtils.isEmpty(headers)){
                buffer.append("请求头：="+headers+"\n");
            }
            if(!TextUtils.isEmpty(params)){
                buffer.append("请求参数：="+params+"\n");
            }
           v("NET_WORK",buffer.toString());
        }
    }
    /**
     * 将log信息发送到悬浮界面
     * @param
     */
    public static void sendContentLog(String url,String success,String error){
        if(QyBaseApplication.isDebug){
            if(!TextUtils.isEmpty(success)){
                v("NET_WORK","服务器成功返回="+success+"\n");
            }
            if(!TextUtils.isEmpty(error)){
                v("NET_WORK","服务器错误返回="+error+"\n");
            }
        }
    }

    public static void e(String tag, String msg){
        if(QyBaseApplication.isDebug){
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.e(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.e(tag, msg);
        }
    }
    public static void v(String tag, String msg){
        if(QyBaseApplication.isDebug){
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.v(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.v(tag, msg);
        }
    }
    public static void w(String tag, String msg){
        if(QyBaseApplication.isDebug){
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.w(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.w(tag, msg);
        }
    }
}
