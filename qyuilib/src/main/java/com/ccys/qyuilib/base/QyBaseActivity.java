package com.ccys.qyuilib.base;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.floatwindow.FloatWindowManager;
import com.ccys.qyuilib.interfaces.ResultActivityCallBackListener;
import com.ccys.qyuilib.loadstatus.OnRetryListener;
import com.ccys.qyuilib.loadstatus.QyStatusLayoutManage;
import com.ccys.qyuilib.loadstatus.QySubmitCallBackListener;
import com.ccys.qyuilib.service.FloatWindowService;
import com.ccys.qyuilib.slideback.util.SlideBackCallBack;
import com.ccys.qyuilib.slideback.util.SlideBackUtil;
import com.ccys.qyuilib.util.QyAppManagerUtil;
import com.ccys.qyuilib.util.QyAppUtil;
import com.ccys.qyuilib.util.QyStatusBarUtil;
import com.ccys.qyuilib.util.SharedUtils;

/**
 * @ProjectName: ZhenMei
 * @Package: com.ccys.qyuilib.base
 * @ClassName: QyBaseActivity
 * @描述: java类作用描述
 * @作者: 秦洋
 * @日期: 2019-12-12 09:46
 */
public abstract class QyBaseActivity extends AppCompatActivity implements OnRetryListener, SlideBackCallBack{
    protected QyAppManagerUtil appManager;
    protected QyBaseApplication application;
    protected ResultActivityCallBackListener activityCallBackListener;
    protected QyStatusLayoutManage statusLayoutManage;
    protected boolean isCreateFloatWindow;//是否允许创建悬浮窗
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreateFloatWindow = true;
        application = (QyBaseApplication) getApplication();
        appManager = application.getAppManager();
        appManager.addActivity(this);
        SlideBackUtil.isNeedSlideBack = true;
        if(getContentLayoutId() != 0){
            statusLayoutManage = getBuilder().contentView(getContentLayoutId()).build();
            setContentView(statusLayoutManage.getRootView());
        }
        findViewById(savedInstanceState);
        initView(savedInstanceState);
        addListener(savedInstanceState);
        initData(savedInstanceState);
        //侧滑返回
        SlideBackUtil.register(this,true,this);
//        if(application.isDebug){
//            //如果悬浮窗没有创建在创建
//            if(!application.isCreateFloatWindow && isCreateFloatWindow){
//                application.isCreateFloatWindow = true;
//                createFloatService();
//            }
//        }
    }
    private void createFloatService(){
       Intent intent = new Intent(this, FloatWindowService.class);
       bindService(intent, new ServiceConnection() {
           @Override
           public void onServiceConnected(ComponentName name, IBinder service) {
               FloatWindowService.FloatBind bind = (FloatWindowService.FloatBind) service;
               bind.checkFloatPermission(QyBaseActivity.this,getSupportFragmentManager());
           }

           @Override
           public void onServiceDisconnected(ComponentName name) {

           }
       },BIND_AUTO_CREATE);
       startService(intent);
    }
    protected QyStatusLayoutManage.Builder getBuilder(){
        QyStatusLayoutManage.Builder builder = new QyStatusLayoutManage.Builder(this);
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
    protected void showSubmit(){
        statusLayoutManage.showSubmit();
    }
    protected void showSubmit(QySubmitCallBackListener submitCallBackListener){
        statusLayoutManage.showSubmit(submitCallBackListener);
    }
    protected void setOffsetView(View view){
        statusLayoutManage.setOffsetView(view);
    }
    protected void setOffsetView(View view,boolean isOffsetStatusBar){
        statusLayoutManage.setOffsetView(view,isOffsetStatusBar);
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
    protected abstract void initView(@Nullable Bundle savedInstanceState);
    /**
     * @description 查找view
     * @param
     * @return
     * @author 秦洋
     * @time 2019/12/14 20:46
     */
    protected void findViewById(@Nullable Bundle savedInstanceState){}
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        appManager.finishActivity(this);
    }
    //设置状态栏的样式(不需要view占据状态栏)
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setStateBarStyle(int colorRes, boolean isBlack) {
        QyStatusBarUtil.setStateBarBgColor(this, colorRes, isBlack);
    }
    //设置状态栏的样式(不需要view占据状态栏)
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setStateBarStyle(String color, boolean isBlack) {
        QyStatusBarUtil.setStateBarBgColor(this, color, isBlack);
    }
    //设置状态栏的样式(不需要view占据状态栏)
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setStateBarTranslucent(boolean isBlack) {
        QyStatusBarUtil.setStateBarTranslucent(this, isBlack);
    }
    //设置状态栏的样式(不需要view占据状态栏)
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void setStateBarTranslucent(View view,boolean isBlack) {
        QyStatusBarUtil.setStateBarTranslucent(this, view,isBlack);
    }
    //设置状态栏的字体颜色
    protected void setStatusBarTextStyle(boolean isBlack) {
        QyStatusBarUtil.setStateBarTextColor(this, isBlack);
    }
    /**
     * 通过Class跳转界面
     **/
    protected void mystartActivity(Class<?> cls) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }
    /**
     * 通过Class跳转界面
     **/
    protected void mystartActivity(Class<?> cls, ActivityOptions activityOptions) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent,activityOptions.toBundle());
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void mystartActivity(Class<?> cls, Bundle bundle) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
        startActivityForResult(intent,code,activityOptions.toBundle());
    }
    /**
     * 通过Class跳转界面
     **/
    protected void mystartActivityForResult(Class<?> cls, ActivityOptions activityOptions,int code) {
        if(!QyAppUtil.isFastDoubleClick())return;
        Intent intent = new Intent();
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
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
    // 设置字体大小不随手机设置而改变
    @Override
    public Resources getResources() {
        float fontScale = (float) SharedUtils.getParam("fontScale",1.0f);
        Resources resource = super.getResources();
        Configuration configuration = resource.getConfiguration();
        configuration.fontScale = fontScale;// 设置字体的缩放比例
        resource.updateConfiguration(configuration, resource.getDisplayMetrics());
        return resource;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(activityCallBackListener != null){
            if(requestCode == activityCallBackListener.getCode()){
                //将界面的数据返回到回调方法里
                activityCallBackListener.callBack(requestCode,resultCode,data);
            }
        }
    }
    //侧滑返回的事件回掉
    @Override
    public void onSlideBack() {
        onBackPressed();
    }

}
