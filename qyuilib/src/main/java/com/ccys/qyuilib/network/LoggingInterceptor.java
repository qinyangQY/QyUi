package com.ccys.qyuilib.network;

import com.ccys.qyuilib.util.LogUtil;
import okhttp3.logging.HttpLoggingInterceptor;

public class LoggingInterceptor implements HttpLoggingInterceptor.Logger{
    public LoggingInterceptor(){

    }

    @Override
    public void log(String message) {
        LogUtil.v("NET_WORK",message);
    }
}
