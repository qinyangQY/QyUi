package com.ccys.qyuilib.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.ccys.qyuilib.loadstatus.OnRetryListener;

public class EmptyPop extends QyBasePopWindow {
    private Context context;
     public EmptyPop(Context context){
         super(context);
         this.context = context;
         setOutsideTouchable(false);
         setFocusable(false);
         setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
     }
     public void setContentLayout(int layoutId, final OnRetryListener onRetryListener){
         setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
         setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
         LayoutInflater inflater = LayoutInflater.from(context);
         View contentView = inflater.inflate(layoutId,null);
         setContentView(contentView);
         if(onRetryListener != null){
             contentView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     onRetryListener.onRetry();
                 }
             });
         }
     }
    public void showEmpty(final View anchor){
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
    public void showEmpty(final View parent, final int gravity, final int x, final int y){
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
    public void closeEmpty(){
         if(isShowing()){
             dismiss();
         }
    }
    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
