package com.ccys.qyuilib.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.ccys.qyuilib.R;

public class SystemTipDialog extends QyBaseDialog{
    private String title;
    private String content;
    private OnEventListener onEventListener;
    public SystemTipDialog(Context context,String title,String content,OnEventListener onEventListener) {
        super(context, R.style.qy_normal_dialog,R.layout.system_tip_dialog_layout);
        this.title = title;
        this.content = content;
        this.onEventListener = onEventListener;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
    public static void Show(Context context,String content,OnEventListener onEventListener){
        SystemTipDialog dialog = new SystemTipDialog(context,null,content,onEventListener);
        dialog.setGravity(Gravity.CENTER);
        dialog.show();
    }
   public static void Show(Context context,String title,String content,OnEventListener onEventListener){
       SystemTipDialog dialog = new SystemTipDialog(context,title,content,onEventListener);
       dialog.setGravity(Gravity.CENTER);
       dialog.show();
   }
    @Override
    public void onBindView(QyViewHolder holder) {
          if(!TextUtils.isEmpty(title)){
              holder.setText(R.id.tv_dialog_title,title);
          }
        holder.setText(R.id.tv_content,content);
          holder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  dismiss();
                  onEventListener.onCancel();
              }
          });
          holder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  dismiss();
                  onEventListener.onConfirm();
              }
          });
    }
    public interface OnEventListener{
        void onConfirm();
        void onCancel();
    }
}
