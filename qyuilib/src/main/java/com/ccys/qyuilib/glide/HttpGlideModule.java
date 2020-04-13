package com.ccys.qyuilib.glide;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.ccys.qyuilib.network.SSlUtil;
import com.ccys.qyuilib.network.SetingsSSlUtil;
import com.ccys.qyuilib.util.SharedUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

import static org.apache.http.conn.ssl.SSLSocketFactory.SSL;

/**
 * Created by Administrator on 2019/3/20 0020.
 * 自定义加载框架 使用okhttp 加载https
 */

// 注意这个注解一定要加上，HttpGlideModule是自定义的名字
@GlideModule
public final class HttpGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        // 注意这里用我们刚才现有的Client实例传入即可
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(initOkhttp(context)));
    }

    //初始化okhttp
    private OkHttpClient initOkhttp(Context context){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        String sslName = (String) SharedUtils.getParam("sslName","");
        if(!TextUtils.isEmpty(sslName)){//配置证书
            builder.sslSocketFactory(SetingsSSlUtil.createSSLSocketFactory(context,sslName),new SetingsSSlUtil.MyX509TrustManager(context,sslName));
        }else {//不配置证书(就信任所有证书)
            builder.sslSocketFactory(SSlUtil.createSSLSocketFactory(),new SSlUtil.TrustAllManager());
            builder.hostnameVerifier(new SSlUtil.TrustAllHostnameVerifier());
        }
        return builder.build();
    }

}