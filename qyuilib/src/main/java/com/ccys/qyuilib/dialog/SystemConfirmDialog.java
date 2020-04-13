package com.ccys.qyuilib.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.ccys.qyuilib.R;

public class SystemConfirmDialog extends QyBaseDialog{
    private String title;
    private String content;
    private OnEventListener onEventListener;
    public SystemConfirmDialog(Context context,String title,String content,OnEventListener onEventListener) {
        super(context, R.style.qy_normal_dialog,R.layout.system_confirm_dialog_layout);
        this.title = title;
        this.content = content;
        this.onEventListener = onEventListener;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
    public static void Show(Context context,String content,OnEventListener onEventListener){
        SystemConfirmDialog dialog = new SystemConfirmDialog(context,null,content,onEventListener);
        dialog.setGravity(Gravity.CENTER);
        dialog.show();
    }
     public static void Show(Context context,String title,String content,OnEventListener onEventListener){
         SystemConfirmDialog dialog = new SystemConfirmDialog(context,title,content,onEventListener);
         dialog.setGravity(Gravity.CENTER);
         dialog.show();
     }
    @Override
    public void onBindView(QyViewHolder holder) {
         if(!TextUtils.isEmpty(title)){
            holder.setText(R.id.tv_dialog_title,title);
         }
        holder.setText(R.id.tv_content,content);
         holder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dismiss();
                 onEventListener.onClick();
             }
         });
    }
    public interface OnEventListener{
        void onClick();
    }
}
