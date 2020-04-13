package com.ccys.qyui.network;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface HomeInterface {
    @GET("shopHomeApi/homeData")
    Observable<ResponseBody> homeData();
    @PUT("userApi/feedbackSave")
    Observable<ResponseBody> feedBack(@QueryMap HashMap<String,String> params);
    @FormUrlEncoded
    @POST("shopHomeApi/shopLogin")
    Observable<ResponseBody> login(@FieldMap HashMap<String,String> params);
    @POST("user/auth")
    Observable<ResponseBody> loginPost(@Body HashMap<String,String> params);

    //上传文件
    @Multipart
    @POST("common/updateFile")
    Observable<ResponseBody> uploadFile(@Part MultipartBody.Part body);

    //上传多个文件
    @POST("common/updateFile")
    Observable<ResponseBody> multipartUploadFile(@Body RequestBody body);
}
