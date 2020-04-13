package com.ccys.qyui;

import android.content.Context;

import com.ccys.qyuilib.network.QyNetworkManager;
import com.ccys.qyuilib.util.AppUtil;

import java.util.HashMap;

public class NetWorkManager extends QyNetworkManager {
    private static volatile NetWorkManager instance;
    private NetWorkManager(Context context) {
        super(context);
    }
    public static NetWorkManager getInstance(Context context){
        if(instance == null){
            synchronized (NetWorkManager.class){
                if(instance == null){
                    instance = new NetWorkManager(context);
                }
            }
        }
        return instance;
    }
    @Override
    protected String getBaseUrl() {
        return "http://47.108.198.81:8080/riceBallLive/";
      //  return "http://47.108.64.206:8080/clz/";
    }

    @Override
    protected HashMap<String, String> headers() {
        HashMap<String,String> header = new HashMap<>();
        header.put("api-version","1");
        header.put("token","55e23d40a4d1184314981007c69ea969");
        return header;
    }

    @Override
    protected HashMap<String, String> params() {
        HashMap<String,String> params = new HashMap<>();
        params.put("pageNum","1");
        params.put("pageSize","10");
        return params;
    }

    @Override
    protected int otherDevicesLoginCode() {
        return 401;
    }

    @Override
    protected String otherDevicesLoginAction() {
        return AppUtil.getAppPackageName()+"exit";
    }

    @Override
    protected boolean isSingleLogin() {
        return false;
    }

    @Override
    protected String sslName() {
        return null;
    }

    @Override
    protected int unLoginCode() {
        return 0;
    }

    @Override
    protected String unLoginAction() {
        return null;
    }

    @Override
    protected int accountFrozenCode() {
        return 0;
    }

    @Override
    protected String accountFrozenAction() {
        return null;
    }
}
