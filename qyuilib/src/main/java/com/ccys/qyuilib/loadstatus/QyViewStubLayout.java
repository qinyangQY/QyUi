package com.ccys.qyuilib.loadstatus;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;

public class QyViewStubLayout {
    private View mContentView;
    private ViewStub mLayoutVs;

    public QyViewStubLayout() {
    }

    public void initLayout(Context context, int layoutResId) {
        this.mLayoutVs = new ViewStub(context);
        this.mLayoutVs.setLayoutResource(layoutResId);
    }

    public void setView(View contentView) {
        this.mContentView = contentView;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public ViewStub getLayoutVs() {
        return this.mLayoutVs;
    }

    public void setData(Object object) {
    }
}
