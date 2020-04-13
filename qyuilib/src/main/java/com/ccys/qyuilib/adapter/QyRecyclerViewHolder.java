package com.ccys.qyuilib.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccys.qyuilib.image.ImageLoad;
import com.ccys.qyuilib.util.ViewSizeUtil;

public class QyRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    private Context mContext;
    private View itemView;
    public QyRecyclerViewHolder(Context context,@NonNull View itemView) {
        super(itemView);
        this.mContext = context;
        this.itemView = itemView;
    }
    public void setText(int viewId,String content){
        View view = itemView.findViewById(viewId);
        if(view != null && view instanceof TextView){
            if(TextUtils.isEmpty(content)){
                ((TextView)view).setText("");
            } else {
                ((TextView)view).setText(content);
            }
        }
    }
    public void setText(int viewId,String content,String defaultContent){
        View view = itemView.findViewById(viewId);
        if(view != null && view instanceof TextView){
            if(TextUtils.isEmpty(content)){
                ((TextView)view).setText(defaultContent);
            } else {
                ((TextView)view).setText(content);
            }
        }
    }
    public void setImage(int viewId,String url){
        View view = itemView.findViewById(viewId);
        if(view != null && view instanceof ImageView){
            ImageLoad.showImage(mContext, (ImageView) view,url);
        }
    }
    public void setImage(int viewId,int resId){
        View view = itemView.findViewById(viewId);
        if(view != null && view instanceof ImageView){
            ((ImageView)view).setImageResource(resId);
        }
    }
    public void setImage(int viewId,String url,int colorResId){
        View view = itemView.findViewById(viewId);
        if(view != null && view instanceof ImageView){
            ImageLoad.showImage(mContext, (ImageView) view,colorResId,url);
        }
    }
    public void setImage(int viewId,String url,int placeholder,int error){
        View view = itemView.findViewById(viewId);
        if(view != null && view instanceof ImageView){
            ImageLoad.showImage(mContext, (ImageView) view,placeholder,error,url);
        }
    }

   public void setVisibility(int viewId,int visibility){
       View view = itemView.findViewById(viewId);
       if(view != null){
           view.setVisibility(visibility);
       }
   }
   public void setHeight(int viewId,int widthDiffer, int widtnDivide, int expectWidth, int expectHeight){
       View view = itemView.findViewById(viewId);
       if(view != null){
           ViewSizeUtil.setViewHeight(mContext,view,widthDiffer,widtnDivide,expectWidth,expectHeight);
       }
   }
   public void setSize(int viewId,int widthDiffer, int widtnDivide, int expectWidth, int expectHeight){
       View view = itemView.findViewById(viewId);
       if(view != null){
           ViewSizeUtil.setViewSize(mContext,view,widthDiffer,widtnDivide,expectWidth,expectHeight);
       }
   }
   public <V extends View> V getView(int viewId){
       View view = itemView.findViewById(viewId);
       return (V) view;
   }
   public <V extends View> V getItemView(){
        return (V) itemView;
   }
   public void setOnClickListener(int viewId, View.OnClickListener onClickListener){
       View view = itemView.findViewById(viewId);
       if(view != null){
           view.setOnClickListener(onClickListener);
       }
   }
    public void setOnClickListener(View.OnClickListener onClickListener, int... viewId) {
        int[] ids = viewId;
        for (int id : ids) {
            itemView.findViewById(id).setOnClickListener(onClickListener);
        }
    }
    public void setItemOnClickListener(View.OnClickListener onClickListener) {
        this.itemView.setOnClickListener(onClickListener);
    }
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener, int... viewId) {
        int[] ids = viewId;
        for (int id : ids) {
            itemView.findViewById(id).setOnLongClickListener(onLongClickListener);
        }
    }
    public void setOnLongClickListener(int viewId,View.OnLongClickListener onLongClickListener) {
        View view = itemView.findViewById(viewId);
        if(view != null){
            view.setOnLongClickListener(onLongClickListener);
        }
    }
    public void setItemOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        itemView.setOnLongClickListener(onLongClickListener);
    }
}
