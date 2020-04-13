package com.ccys.qyuilib.network;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.ccys.qyuilib.util.AppUtil;
import com.ccys.qyuilib.util.NetCheckUtil;
import com.ccys.qyuilib.util.SharedUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class QyNetworkManager {
    private Context context;
    public Retrofit retrofit;

    protected QyNetworkManager(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置统一的请求头
        builder.addInterceptor(getHttpHeaderInterceptor());
        //设置统一的请求参数
        builder.addInterceptor(getHttpParamsInterceptor());
        //设置缓存
        File cacheFile = new File(AppUtil.getAppCachePath(context));
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        builder.addNetworkInterceptor(getHttpCacheInterceptor());
        builder.addInterceptor(getAppCacheInterceptor());
        Interceptor logInterceptor = getLogInterceptor();
        if(logInterceptor != null){
            builder.addNetworkInterceptor(logInterceptor);//使用自定义的Log拦截器
        }
        builder.cache(cache);
        //添加http状态码的code
        builder.addInterceptor(getHttpCodeInterceptor());
        //设置超时
        builder.connectTimeout(getTimeout(), TimeUnit.SECONDS);
        builder.readTimeout(getTimeout(), TimeUnit.SECONDS);
        builder.writeTimeout(getTimeout(), TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        //配置ssl证书
        if (TextUtils.isEmpty(sslName())) {
            builder.sslSocketFactory(SSlUtil.createSSLSocketFactory(), new SSlUtil.TrustAllManager());
            builder.hostnameVerifier(new SSlUtil.TrustAllHostnameVerifier());
        } else {
            SharedUtils.setParam("sslName",sslName());
            builder.sslSocketFactory(SetingsSSlUtil.createSSLSocketFactory(context, sslName()), new SetingsSSlUtil.MyX509TrustManager(context, sslName()));
        }
        return builder.build();
    }
    protected int getTimeout(){
        return 30;
    }
    /**
     * 获取HTTP 添加header的拦截器
     *
     * @return
     */
    private Interceptor getHttpHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HashMap<String, String> headers = headers();
                if (headers != null && headers.size() > 0) {
                    Request.Builder builder = request.newBuilder();
                    Set<String> keys = headers.keySet();
                    for (String k : keys) {
                        builder.addHeader(k, headers.get(k));
                    }
                    builder.method(request.method(), request.body());
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                } else {
                    return chain.proceed(request);
                }
            }
        };
    }
    /**
     * 获取HTTP 添加公共参数的拦截器
     * 暂时支持get、head请求&Post put patch的表单数据请求
     *
     * @return
     */
    private Interceptor getHttpParamsInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HashMap<String, String> params = params();
                if (params != null && params.size() > 0) {
                    if (request.method().equalsIgnoreCase("GET") || request.method().equalsIgnoreCase("HEAD")
                    || request.method().equalsIgnoreCase("PUT") || request.method().equalsIgnoreCase("DELETE")
                    || request.method().equalsIgnoreCase("PATCH")) {
                        HttpUrl.Builder builder = request.url().newBuilder();
                        Set<String> keys = params.keySet();
                        for (String k : keys) {
                            builder.addQueryParameter(k, params.get(k));
                        }
                        return chain.proceed(request.newBuilder().url(builder.build()).build());
                    } else {
                        RequestBody originalBody = request.body();
                        if (originalBody == null) {
                            return chain.proceed(request);
                        }
                        if (originalBody instanceof FormBody) {
                            FormBody oldFromBody = (FormBody) originalBody;
                            FormBody.Builder builder = new FormBody.Builder();
                            for (int i = 0; i < ((FormBody) originalBody).size(); i++) {
                                builder.addEncoded(oldFromBody.encodedName(i), oldFromBody.encodedValue(i));
                            }
                            Set<String> keys = params.keySet();
                            for (String k : keys) {
                                builder.addEncoded(k, params.get(k));
                            }
                            FormBody newFormBody = builder.build();
                            if (request.method().equalsIgnoreCase("POST")) {
                                return chain.proceed(request.newBuilder().post(newFormBody).build());
                            }else{
                                return chain.proceed(request);
                            }
                        } else if (originalBody instanceof MultipartBody) {
                            MultipartBody oldBodyMultipart = (MultipartBody) originalBody;
                            List<MultipartBody.Part> oldPartList = oldBodyMultipart.parts();
                            MultipartBody.Builder builder = new MultipartBody.Builder();
                            builder.setType(oldBodyMultipart.type());
                            for (int i = 0; i < oldPartList.size(); i++) {
                                builder.addPart(oldPartList.get(i));
                            }
                            Set<String> keys = params.keySet();
                            for (String k : keys) {
                                builder.addFormDataPart(k, params.get(k));
                            }
                            MultipartBody newBody = builder.build();
                            if (request.method().equalsIgnoreCase("POST")) {
                                return chain.proceed(request.newBuilder().post(newBody).build());
                            }else {
                                return chain.proceed(request);
                            }
                        }else {
                            Buffer buffer = new Buffer();
                            originalBody.writeTo(buffer);
                            Charset charset = Charset.forName("UTF-8");
                            MediaType contentType = originalBody.contentType();
                            if (contentType != null) {
                                charset = contentType.charset(charset);
                                if(charset != null){
                                    //读取原请求参数内容
                                   String requestParams = buffer.readString(charset);
                                    try {
                                        //重新拼凑请求体
                                        JSONObject jsonObject = new JSONObject(requestParams);
                                        for (Map.Entry<String, String> entry : params.entrySet()) {
                                            jsonObject.put(entry.getKey(), entry.getValue());
                                        }
                                        RequestBody newBody = RequestBody.create(originalBody.contentType(), jsonObject.toString());
                                        if (request.method().equalsIgnoreCase("POST")) {
                                            return chain.proceed(request.newBuilder().post(newBody).build());
                                        } else {
                                            return chain.proceed(request);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        return chain.proceed(request);
                                    }
                                }
                            }
                        }
                    }
                }
                return chain.proceed(request);
            }
        };
    }

    /**
     * 获得HTTP 网络缓存的拦截器
     *
     * @return
     */
    public Interceptor getHttpCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // 无网络时，始终使用本地Cache
                if (!NetCheckUtil.checkNet(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetCheckUtil.checkNet(context)) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    return response.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    return response.newBuilder()
                            //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
    }

    /**
     * 获得HTTP 本地缓存的拦截器
     *
     * @return
     */
    public Interceptor getAppCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // 无网络时，始终使用本地Cache
                if (!NetCheckUtil.checkNet(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetCheckUtil.checkNet(context)) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    return response.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    return response.newBuilder()
                            //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
    }

    /**
     * 添加监听HTTP 状态code的拦截器
     */
    public Interceptor getHttpCodeInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                int code = response.code();
                if (code == otherDevicesLoginCode()) {
                    if(isSingleLogin() && !TextUtils.isEmpty(otherDevicesLoginAction())){
                        Intent intent = new Intent();
                        intent.setAction(otherDevicesLoginAction());
                        context.sendBroadcast(intent);
                    }
                }else if(code == unLoginCode() && !TextUtils.isEmpty(unLoginAction())){
                    Intent intent = new Intent();
                    intent.setAction(unLoginAction());
                    context.sendBroadcast(intent);
                }else if(code == accountFrozenCode() && !TextUtils.isEmpty(accountFrozenAction())){
                    Intent intent = new Intent();
                    intent.setAction(accountFrozenAction());
                    context.sendBroadcast(intent);
                }
                return response;
            }
        };
    }
    /**
     * 获取baseurl
     *
     * @return
     */
    protected abstract String getBaseUrl();

    /**
     * 获取全局的请求头
     *
     * @return
     */
    protected abstract HashMap<String, String> headers();

    /**
     * 获取全局的请求参数
     *
     * @return
     */
    protected abstract HashMap<String, String> params();

    /**
     * 其它设备登录的http状态码
     *
     * @return
     */
    protected abstract int otherDevicesLoginCode();

    /**
     * 其它设备登录的广播action
     *
     * @return
     */
    protected abstract String otherDevicesLoginAction();

    /**
     * 是否单点登录
     *
     * @return
     */
    protected abstract boolean isSingleLogin();

    /**
     * ssl证书名称(配置证书的时候需要将证书名称保存在参数共享中，glide也需要  键值 sslName)
     */
    protected abstract String sslName();

    /**
     * 未登录的code
     * @return
     */
    protected abstract int unLoginCode();

    /**
     * 未登录的action
     */

    protected abstract String unLoginAction();

    /**
     * 账户被冻结
     */
     protected abstract int accountFrozenCode();

    /**
     * 账户被冻结的action
     */
    protected abstract String accountFrozenAction();
    /**
     * 获取log打印实例
     * @return
     */
    protected abstract Interceptor getLogInterceptor();
}
