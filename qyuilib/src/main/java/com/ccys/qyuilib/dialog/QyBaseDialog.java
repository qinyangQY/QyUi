package com.ccys.qyuilib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public abstract class QyBaseDialog extends Dialog {
    private QyViewHolder viewHolder;
    private Window window;
    private View contentView;
    private boolean isFullScreen;//是否全屏
    protected int gravity;//显示的位置
    protected int animationId;//动画资源id
    protected Context context;
    public QyBaseDialog(Context context, int themeResId, int layoutId) {
        super(context, themeResId);
        contentView = LayoutInflater.from(context).inflate(layoutId,null);
        viewHolder = new QyViewHolder(context,contentView);
        this.context = context;
    }
    public void setFullScreen(boolean isFullScreen){
        this.isFullScreen = isFullScreen;
    }
    public void setGravity(int gravity){
        this.gravity = gravity;
    }
    public void setAnimationId(int animationId){
        this.animationId = animationId;
    }
    public Activity getActivity(){
        if(context instanceof Activity){
            return (Activity) context;
        }else {
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = this.getWindow();
        window.setContentView(contentView);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        if(isFullScreen){
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        }else {
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        if(gravity > 0){
            lp.gravity = gravity;
        }else {
            lp.gravity = Gravity.CENTER;
        }
        window.setAttributes(lp);
        if(animationId > 0){
            window.setWindowAnimations(animationId);
        }
        onBindView(viewHolder);
        setLayoutParams(window);
    }
    //如果对窗口位置大小有特殊需求可以复写这个方法
    protected void setLayoutParams(Window window){ }
    /**
     * @method
     * @description 将数据绑定到视图
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public abstract void onBindView(QyViewHolder viewHolder);
}
