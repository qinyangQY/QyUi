package com.ccys.qyuilib.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.ccys.qyuilib.loadstatus.QySubmitCallBackListener;
import com.ccys.qyuilib.view.SubmitStatusView;

public class SubmitPop extends QyBasePopWindow {
    private Context context;
    private SubmitStatusView mSubmitStatusView;
    private QySubmitCallBackListener callBackListener;
     public SubmitPop(Context context){
         super(context);
         this.context = context;
         setOutsideTouchable(false);
         setFocusable(false);
         setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
     }
     public void setContentLayout(int layoutId,int submitViewId){
         setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
         setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
         LayoutInflater inflater = LayoutInflater.from(context);
         View contentView = inflater.inflate(layoutId,null);
         setContentView(contentView);
         if(submitViewId != 0){
             this.mSubmitStatusView = contentView.findViewById(submitViewId);
         }
     }
     public void setSubmitCallBackListener(QySubmitCallBackListener callBackListener){
         this.callBackListener = callBackListener;
     }
    public void showSubmit(final View anchor){
         if(!isShowing()){
             if(anchor.getMeasuredHeight() > 0){
                 showAsDropDown(anchor);
             }else {
                 anchor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                     @Override
                     public void onGlobalLayout() {
                         if(anchor.getMeasuredHeight() > 0){
                             showAsDropDown(anchor);
                             anchor.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                         }
                     }
                 });
             }
         }
    }
    public void showSubmit(final View anchor, final QySubmitCallBackListener submitCallBackListener){
        if(!isShowing()){
            if(anchor.getMeasuredHeight() > 0){
                showAsDropDown(anchor,submitCallBackListener);
            }else {
                anchor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(anchor.getMeasuredHeight() > 0){
                            showAsDropDown(anchor,submitCallBackListener);
                            anchor.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
    }
    public void showLoading(final View parent, final int gravity, final int x, final int y){
        if(!isShowing()){
            if(parent.getMeasuredHeight() > 0){
                showAtLocation(parent,gravity,x,y);
            }else {
                parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(parent.getMeasuredHeight() > 0){
                            showAtLocation(parent,gravity,x,y);
                            parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
    }
    public void showLoading(final View parent, final int gravity, final int x, final int y,final QySubmitCallBackListener submitCallBackListener){
        if(!isShowing()){
            if(parent.getMeasuredHeight() > 0){
                showAtLocation(parent,gravity,x,y,submitCallBackListener);
            }else {
                parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(parent.getMeasuredHeight() > 0){
                            showAtLocation(parent,gravity,x,y,submitCallBackListener);
                            parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
    }
    public void closeLoading(boolean isSuccess){
         if(isShowing()){
             if(mSubmitStatusView != null){
                 mSubmitStatusView.callBack(isSuccess);
             }else {
                 dismiss();
             }
         }
    }
    public void showAsDropDown(View anchor, final QySubmitCallBackListener submitCallBackListener) {
        if(mSubmitStatusView != null){
            mSubmitStatusView.startAnimator();
            mSubmitStatusView.setFinishListener(new SubmitStatusView.AnimFinishListener() {
                @Override
                public void onFinish(boolean status) {
                    mSubmitStatusView.cleanAnimator();
                    mSubmitStatusView.removeFinishListener();
                    dismiss();
                    if (submitCallBackListener != null) {
                        submitCallBackListener.submitCallBack(status);
                    }
                }
            });
        }
        super.showAsDropDown(anchor);
    }
    public void showAsDropDown(View anchor) {
        if(mSubmitStatusView != null){
            mSubmitStatusView.startAnimator();
            mSubmitStatusView.setFinishListener(new SubmitStatusView.AnimFinishListener() {
                @Override
                public void onFinish(boolean status) {
                    mSubmitStatusView.cleanAnimator();
                    mSubmitStatusView.removeFinishListener();
                    dismiss();
                    if (callBackListener != null) {
                        callBackListener.submitCallBack(status);
                    }
                }
            });
        }
        super.showAsDropDown(anchor);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        if(mSubmitStatusView != null){
            mSubmitStatusView.startAnimator();
            mSubmitStatusView.setFinishListener(new SubmitStatusView.AnimFinishListener() {
                @Override
                public void onFinish(boolean status) {
                    mSubmitStatusView.cleanAnimator();
                    mSubmitStatusView.removeFinishListener();
                    dismiss();
                    if (callBackListener != null) {
                        callBackListener.submitCallBack(status);
                    }
                }
            });
        }
        super.showAtLocation(parent, gravity, x, y);
    }
    public void showAtLocation(View parent, int gravity, int x, int y, final QySubmitCallBackListener submitCallBackListener) {
        if(mSubmitStatusView != null){
            mSubmitStatusView.startAnimator();
            mSubmitStatusView.setFinishListener(new SubmitStatusView.AnimFinishListener() {
                @Override
                public void onFinish(boolean status) {
                    mSubmitStatusView.cleanAnimator();
                    mSubmitStatusView.removeFinishListener();
                    dismiss();
                    if (submitCallBackListener != null) {
                        submitCallBackListener.submitCallBack(status);
                    }
                }
            });
        }
        super.showAtLocation(parent, gravity, x, y);
    }
    @Override
    public void dismiss() {
        super.dismiss();
    }
}
