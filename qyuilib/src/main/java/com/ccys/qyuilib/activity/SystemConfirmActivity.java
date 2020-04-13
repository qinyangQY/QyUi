package com.ccys.qyuilib.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ccys.qyuilib.R;

public abstract class SystemConfirmActivity extends AppCompatActivity implements View.OnClickListener {
   private TextView tv_dialog_title;
   private TextView tv_content;
   private TextView tv_confirm;
   private String title;
   private String content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_confirm_dialog_layout);
        overridePendingTransition(0, 0);
        setFinishOnTouchOutside(false);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        tv_dialog_title = findViewById(R.id.tv_dialog_title);
        tv_content = findViewById(R.id.tv_content);
        tv_confirm = findViewById(R.id.tv_confirm);
        if(!TextUtils.isEmpty(title)){
            tv_dialog_title.setText(title);
        }
        tv_content.setText(content);
        tv_confirm.setOnClickListener(this);
    }
    public static void show(Context context, Class<?> cls,String title, String content){
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("content",content);
        Intent intent = new Intent(context,cls);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void show(Context context, Class<?> cls,String content){
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        Intent intent = new Intent(context,cls);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(0, 0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.tv_confirm){
           confirm();
       }
    }
    public abstract void confirm();
}
