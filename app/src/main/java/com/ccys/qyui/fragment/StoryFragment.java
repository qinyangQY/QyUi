package com.ccys.qyui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.ccys.qyui.R;
import com.ccys.qyuilib.base.QyBaseFragment;
import com.ccys.qyuilib.loadstatus.QySubmitCallBackListener;

public class StoryFragment extends QyBaseFragment {
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_story_layout;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, View contentView) {
        showSubmit(new QySubmitCallBackListener() {
            @Override
            public void submitCallBack(boolean isSuccess) {
                showError();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeSubmit(true);
            }
        },4000);
    }

    @Override
    public void onRetry() {

    }
}
