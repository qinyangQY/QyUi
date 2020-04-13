package com.ccys.qyuilib.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ccys.qyuilib.R;

public class QyAlertDialog extends QyBaseDialog {
    private String title;
    private String content;
    private OnEventListener mOnEventListener;
    public QyAlertDialog(Context context, String title, String content, OnEventListener mOnEventListener) {
        super(context, R.style.qy_normal_dialog, R.layout.qy_alert_dialog_layout);
        this.title = title;
        this.content = content;
        this.mOnEventListener = mOnEventListener;
    }
    public static void Show(Context context,String title,String content,OnEventListener mOnEventListener){
        QyAlertDialog dialog = new QyAlertDialog(context,title,content,mOnEventListener);
        dialog.show();
    }
    public static void Show(Context context,String content,OnEventListener mOnEventListener){
        QyAlertDialog dialog = new QyAlertDialog(context,null,content,mOnEventListener);
        dialog.show();
    }
    @Override
    public void onBindView(QyViewHolder holder) {
        if(!TextUtils.isEmpty(title)){
            holder.setText(R.id.tv_title,title);
        }
        holder.setText(R.id.tv_content,content);
        holder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        holder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                 if(mOnEventListener != null){
                     mOnEventListener.onEvent();
                 }
            }
        });
    }
    public interface OnEventListener{
        void onEvent();
    }
}
