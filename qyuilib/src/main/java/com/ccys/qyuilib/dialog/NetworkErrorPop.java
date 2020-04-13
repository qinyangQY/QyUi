package com.ccys.qyuilib.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.ccys.qyuilib.loadstatus.OnNetworkListener;

public class NetworkErrorPop extends QyBasePopWindow {
    private Context context;
     public NetworkErrorPop(Context context){
         super(context);
         this.context = context;
         setOutsideTouchable(false);
         setFocusable(false);
         setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
     }
     public void setContentLayout(int layoutId, final OnNetworkListener onNetworkListener){
         setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
         setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
         LayoutInflater inflater = LayoutInflater.from(context);
         View contentView = inflater.inflate(layoutId,null);
         setContentView(contentView);
         contentView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                if(onNetworkListener != null){
                    onNetworkListener.onNetwork();
                }else {
                    context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                }
             }
         });
     }
    public void showNetworkError(final View anchor){
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
    public void showNetworkError(final View parent, final int gravity, final int x, final int y){
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
    public void closeNetworkError(){
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
