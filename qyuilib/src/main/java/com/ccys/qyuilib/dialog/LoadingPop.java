package com.ccys.qyuilib.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.wang.avi.AVLoadingIndicatorView;

public class LoadingPop extends QyBasePopWindow {
    private Context context;
    private AVLoadingIndicatorView mAVLoadingIndicatorView;
    private View rootView;
     public LoadingPop(Context context){
         super(context);
         this.context = context;
         setOutsideTouchable(true);
         setFocusable(true);
         setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
     }
     public void setContentLayout(int layoutId,int aviViewId,View rootView){
         setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
         setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
         LayoutInflater inflater = LayoutInflater.from(context);
         View contentView = inflater.inflate(layoutId,null);
         this.rootView = rootView;
         setContentView(contentView);
         if(aviViewId != 0){
             this.mAVLoadingIndicatorView = contentView.findViewById(aviViewId);
         }

     }
    public void showLoading(final View anchor){
         if(!isShowing()){
             if(anchor.getMeasuredHeight() > 0){
                 setHeight(rootView.getMeasuredHeight());
                 showAsDropDown(anchor);
             }else {
                 anchor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                     @Override
                     public void onGlobalLayout() {
                         if(anchor.getMeasuredHeight() > 0){
                             setHeight(rootView.getMeasuredHeight());
                             showAsDropDown(anchor);
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
                setHeight(rootView.getMeasuredHeight());
                showAtLocation(parent,gravity,x,y);
            }else {
                parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(parent.getMeasuredHeight() > 0){
                            setHeight(rootView.getMeasuredHeight());
                            showAtLocation(parent,gravity,x,y);
                            parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
    }
    public void closeLoading(){
         if(isShowing()){
             dismiss();
         }
    }
    @Override
    public void showAsDropDown(View anchor) {
        if(mAVLoadingIndicatorView != null){
            mAVLoadingIndicatorView.smoothToShow();
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        if(mAVLoadingIndicatorView != null){
            mAVLoadingIndicatorView.smoothToShow();
        }
    }

    @Override
    public void dismiss() {
        if(mAVLoadingIndicatorView != null){
            mAVLoadingIndicatorView.smoothToHide();
        }
        super.dismiss();
    }
}
