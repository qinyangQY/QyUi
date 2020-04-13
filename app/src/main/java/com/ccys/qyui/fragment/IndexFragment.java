package com.ccys.qyui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ccys.qyui.R;
import com.ccys.qyui.NetWorkManager;
import com.ccys.qyui.brodcast.TestBrodcast;
import com.ccys.qyui.network.HomeInterface;
import com.ccys.qyuilib.base.QyBaseFragment;
import com.ccys.qyuilib.network.CustomView;
import com.ccys.qyuilib.network.GetDataView;
import com.ccys.qyuilib.network.SaveDataView;
import com.ccys.qyuilib.util.AppUtil;
import com.ccys.qyuilib.util.ImageSelectUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class IndexFragment extends QyBaseFragment {
    private RelativeLayout re_title;
    private TextView tv_select;
    private NetWorkManager mNetWorkManager;
    private TextView tv_submit;
    private TextView tv_start;
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_index_layout;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, View contentView) {
        re_title = contentView.findViewById(R.id.re_title);
        tv_select = contentView.findViewById(R.id.tv_select);
        tv_submit = contentView.findViewById(R.id.tv_submit);
        tv_start = contentView.findViewById(R.id.tv_start);
        setOffsetView(re_title);
        mNetWorkManager = NetWorkManager.getInstance(getActivity());

        //菜篮子用户端token 55e23d40a4d1184314981007c69ea969
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getData();
    }

    private void getData(){
        HomeInterface home = mNetWorkManager.retrofit.create(HomeInterface.class);
        Observable<ResponseBody> observable = home.homeData();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new GetDataView<ResponseBody>(mNetWorkManager,statusLayoutManage) {
                     @Override
                     public void onNext(ResponseBody body) {
                         showContent();
                         try {
                             Log.v("map","onNext="+body.string());
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
                 });
   }
    @Override
    protected void addListener(@Nullable Bundle savedInstanceState) {
        super.addListener(savedInstanceState);
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectUtil.selectSingleImage(IndexFragment.this, getChildFragmentManager(), true, true, new OnResultCallbackListener() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                         HomeInterface home = mNetWorkManager.retrofit.create(HomeInterface.class);
                         List<String> flies = new ArrayList<>();
                         flies.add(result.get(0).getCutPath());
                         Observable<ResponseBody> observable = home.multipartUploadFile(getFileBody(flies));
                         observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new SaveDataView<ResponseBody>(mNetWorkManager,statusLayoutManage) {
                                     @Override
                                     public void submitStatus(boolean isSuccess) {
                                         Log.v("map","submitStatus==="+isSuccess);
                                     }

                                     @Override
                                     public void onNext(ResponseBody body) {
                                       closeSubmit(true);
                                     }
                                 });
                    }
                });
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> params = new HashMap<>();
//                params.put("account","18391002941");
//                params.put("password","123456");
                params.put("content","测试----");
                HomeInterface home = mNetWorkManager.retrofit.create(HomeInterface.class);
                Observable<ResponseBody> observable = home.feedBack(params);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CustomView<ResponseBody>(mNetWorkManager) {
                            @Override
                            public void authError(int code, Throwable e) {
                                Log.v("map","authError--"+code);
                            }

                            @Override
                            public void otherError(Throwable e) {
                                Log.v("map","otherError--"+e.toString());
                            }

                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.v("map","onSubscribe--");
                            }

                            @Override
                            public void onNext(ResponseBody body) {
                                Log.v("map","onNext--");
                            }
                        });
            }
        });
        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(AppUtil.getAppPackageName()+"test");
                getActivity().sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onRetry() {
        getData();
    }
    private RequestBody getFileBody(List<String> files){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (String path:files){
            File file = new File(path);
            builder.addFormDataPart("uploadFile",file.getName(),
                    RequestBody.create(MediaType.parse("image/*"),file));
        }
        builder.addFormDataPart("test","12345");
        Log.v("map","上传----");
        return builder.build();
    }
    private MultipartBody.Part getFilePart(String filePath){
        File file = new File(filePath);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), fileRQ);
        return part;
    }

}
