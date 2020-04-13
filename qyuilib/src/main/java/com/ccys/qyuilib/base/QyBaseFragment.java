package com.ccys.qyuilib.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.interfaces.ResultActivityCallBackListener;
import com.ccys.qyuilib.loadstatus.OnRetryListener;
import com.ccys.qyuilib.loadstatus.QyStatusLayoutManage;
import com.ccys.qyuilib.loadstatus.QySubmitCallBackListener;
import com.ccys.qyuilib.util.QyAppUtil;
import com.ccys.qyuilib.util.QyStatusBarUtil;

/**
 * @ProjectName: ZhenMei
 * @Package: com.ccys.qyuilib.base
 * @ClassName: QyBaseFragment
 * @描述: java类作用描述
 * @作者: 秦洋
 * @日期: 2019-12-12 09:47
 */
public abstract class QyBaseFragment extends Fragment implements OnRetryListener {
    protected View contentView;
    protected ResultActivityCallBackListener activityCallBackListener;
    protected QyStatusLayoutManage statusLayoutManage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(contentView == null){
            if(getContentLayoutId() != 0){
                statusLayoutManage = getBuilder().contentView(getContentLayoutId()).build();
                contentView = statusLayoutManage.getRootView().getContentView();
            }
        }
        initView(savedInstanceState,contentView);
        addListener(savedInstanceState);
        initData(savedInstanceState);
        return statusLayoutManage.getRootView();
    }
    protected QyStatusLayoutManage.Builder getBuilder(){
        QyStatusLayoutManage.Builder builder = new QyStatusLayoutManage.Builder(getActivity());
        builder.submitVs(R.layout.default_submit_layout);
        builder.submitTextTipId(R.id.tv_submit_tip);
        builder.submitViewId(R.id.submit_view);
        builder.emptyDataView(R.layout.default_empty_layout);
        builder.emptyDataIconImageId(R.id.im_tip_image);
        builder.emptyDataTextTipId(R.id.tv_tip_txt);
        builder.loadingView(R.layout.default_loading_layout);
        builder.aviViewId(R.id.avi_view);
        builder.errorView(R.layout.default_error_layout);
        builder.errorIconImageId(R.id.im_tip_image);
        builder.errorTextTipId(R.id.tv_tip_txt);
        builder.netWorkErrorView(R.layout.default_network_layout);
        builder.onRetryListener(this);
        return builder;
    }
    protected void setOffsetView(View view){
        statusLayoutManage.setOffsetView(view);
    }
    protected void setOffsetView(View view,boolean isOffsetStatusBar){
        statusLayoutManage.setOffsetView(view,isOffsetStatusBar);
    }
    protected void showSubmit(){
        statusLayoutManage.showSubmit();
    }
    protected void showSubmit(QySubmitCallBackListener submitCallBackListener){
        statusLayoutManage.showSubmit(submitCallBackListener);
    }
    protected void closeSubmit(boolean isSuccess){
        statusLayoutManage.closeSubmit(isSuccess);
    }
    protected void showLoading(){
        statusLayoutManage.showLoading();
    }
    protected void showContent(){
        statusLayoutManage.showContent();
    }
    protected void showEmptyData(){
        statusLayoutManage.showEmptyData();
    }
    protected void showEmptyData(int iconImage, String textTip){
        statusLayoutManage.showEmptyData(iconImage,textTip);
    }
    protected void showNetWorkError(){
        statusLayoutManage.showNetWorkError();
    }
    protected void showError(){
        statusLayoutManage.showError();
    }
    protected void showError(int iconImage, String textTip){
        statusLayoutManage.showError(iconImage,textTip);
    }
    /**
     * @method  获取布局文件
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    protected abstract int getContentLayoutId();
    /**
     * @method  初始化view
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState,View contentView);

    /**
     * @method  给view添加监听
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    protected void addListener(@Nullable Bundle savedInstanceState){

    }

    /**
     * @method  初始化数据
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    protected void initData(@Nullable Bundle savedInstanceState){

    }

    //设置状态栏的样式(不需要view占据状态栏)
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setStateBarStyle(int colorRes, boolean isBlack) {
        QyStatusBarUtil.setStateBarBgColor(getActivity(), colorRes, isBlack);
    }
    //设置状态栏的样式(不需要view占据状态栏)
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setStateBarStyle(String color, boolean isBlack) {
        QyStatusBarUtil.setStateBarBgColor(getActivity(), color, isBlack);
    }
    //设置状态栏的样式(不需要view占据状态栏)
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setStateBarTranslucent(boolean isBlack) {
        QyStatusBarUtil.setStateBarTranslucent(getActivity(), isBlack);
    }
    //设置状态栏的样式(不需要view占据状态栏)
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setStateBarTranslucent(View view, boolean isBlack) {
        QyStatusBarUtil.setStateBarTranslucent(getActivity(), view,isBlack);
    }
    //设置状态栏的字体颜色
    protected void setStatusBarTextStyle(boolean isBlack) {
        QyStatusBarUtil.setStateBarTextColor(getActivity(), isBlack);
    }
    /**
     * 通过Class跳转界面
     **/
    protected void mystartActivity(Class<?> cls) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivity(intent);
    }
    /**
     * 通过Class跳转界面
     **/
    protected void mystartActivity(Class<?> cls, ActivityOptions activityOptions) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivity(intent,activityOptions.toBundle());
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void mystartActivity(Class<?> cls, Bundle bundle) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void mystartActivity(Class<?> cls, ActivityOptions activityOptions,Bundle bundle) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent,activityOptions.toBundle());
    }
    /**
     * 通过Class跳转界面
     **/
    protected void mystartActivityForResult(Class<?> cls, int code) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivityForResult(intent,code,null);
    }
    /**
     * 通过Class跳转界面
     **/
    protected void mystartActivityForResult(Class<?> cls, int code, ResultActivityCallBackListener activityCallBackListener) {
        if(!QyAppUtil.isFastDoubleClick())return;
        this.activityCallBackListener = activityCallBackListener;
        this.activityCallBackListener.setRequestCode(code);
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivityForResult(intent,code,null);
    }
    /**
     * 通过Class跳转界面
     **/
    protected void mystartActivityForResult(Class<?> cls, ActivityOptions activityOptions, int code, ResultActivityCallBackListener activityCallBackListener) {
        if(!QyAppUtil.isFastDoubleClick())return;
        this.activityCallBackListener = activityCallBackListener;
        this.activityCallBackListener.setRequestCode(code);
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivityForResult(intent,code,activityOptions.toBundle());
    }
    /**
     * 通过Class跳转界面
     **/
    protected void mystartActivityForResult(Class<?> cls, ActivityOptions activityOptions,int code) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivityForResult(intent,code,activityOptions.toBundle());
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void mystartActivityForResult(Class<?> cls, Bundle bundle, int code, ResultActivityCallBackListener activityCallBackListener) {
        if(!QyAppUtil.isFastDoubleClick())return;
        this.activityCallBackListener = activityCallBackListener;
        this.activityCallBackListener.setRequestCode(code);
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code);
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void mystartActivityForResult(Class<?> cls, Bundle bundle, int code) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code);
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void mystartActivityForResult(Class<?> cls,ActivityOptions activityOptions, Bundle bundle, int code) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code,activityOptions.toBundle());
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void mystartActivityForResult(Class<?> cls, ActivityOptions activityOptions, Bundle bundle, int code, ResultActivityCallBackListener activityCallBackListener) {
        if(!QyAppUtil.isFastDoubleClick())return;
        this.activityCallBackListener = activityCallBackListener;
        this.activityCallBackListener.setRequestCode(code);
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code,activityOptions.toBundle());
    }
    /**
     * 通过Action跳转界面
     **/
    protected void mystartActivity(String action, Uri uri) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent(action, uri);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(activityCallBackListener != null){
            if(requestCode == activityCallBackListener.getCode()){
                //将界面的数据返回到回调方法里
                activityCallBackListener.callBack(requestCode,resultCode,data);
            }
        }
    }

}
