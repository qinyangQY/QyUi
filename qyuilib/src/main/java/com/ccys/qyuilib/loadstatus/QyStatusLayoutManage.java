package com.ccys.qyuilib.loadstatus;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;

public class QyStatusLayoutManage {
    private final QyStatusFrameLayout rootFrameLayout;
    final Context context;
    final ViewStub netWorkErrorVs;
    final int netWorkErrorRetryViewId;
    final ViewStub emptyDataVs;
    final int emptyDataRetryViewId;
    final ViewStub errorVs;
    final int errorRetryViewId;
    final int loadingLayoutResId;
    final ViewStub submitVs;
    final int submitTextTipId;
    final int contentLayoutResId;
    final int retryViewId;
    final int emptyDataIconImageId;
    final int emptyDataTextTipId;
    final int errorIconImageId;
    final int errorTextTipId;
    final int aviViewId;
    final int submitViewId;
    final QyViewStubLayout errorLayout;
    final QyViewStubLayout emptyDataLayout;
    final QySubmitCallBackListener submitCallBackListener;
    final OnRetryListener onRetryListener;
    final OnNetworkListener onNetworkListener;

    private QyStatusLayoutManage(QyStatusLayoutManage.Builder builder, boolean wrapContent) {
        this.context = builder.context;
        this.netWorkErrorVs = builder.netWorkErrorVs;
        this.netWorkErrorRetryViewId = builder.netWorkErrorRetryViewId;
        this.emptyDataVs = builder.emptyDataVs;
        this.emptyDataRetryViewId = builder.emptyDataRetryViewId;
        this.errorVs = builder.errorVs;
        this.errorRetryViewId = builder.errorRetryViewId;
        this.loadingLayoutResId = builder.loadingLayoutResId;
        this.submitVs = builder.submitVs;
        this.submitTextTipId = builder.submitTextTipId;
        this.contentLayoutResId = builder.contentLayoutResId;
        this.retryViewId = builder.retryViewId;
        this.emptyDataIconImageId = builder.emptyDataIconImageId;
        this.emptyDataTextTipId = builder.emptyDataTextTipId;
        this.errorIconImageId = builder.errorIconImageId;
        this.errorTextTipId = builder.errorTextTipId;
        this.errorLayout = builder.errorLayout;
        this.emptyDataLayout = builder.emptyDataLayout;
        this.onRetryListener = builder.onRetryListener;
        this.onNetworkListener = builder.onNetworkListener;
        this.aviViewId = builder.aviViewId;
        this.submitViewId = builder.submitViewId;
        this.submitCallBackListener = builder.submitCallBackListener;
        this.rootFrameLayout = new QyStatusFrameLayout(this.context);
        ViewGroup.LayoutParams layoutParams;
        if (wrapContent) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        } else {
            layoutParams = new ViewGroup.LayoutParams(-1, -1);
        }

        this.rootFrameLayout.setLayoutParams(layoutParams);
        this.rootFrameLayout.setBackgroundColor(-1);
        this.rootFrameLayout.setStatusLayoutManage(this);
    }

    public QyStatusFrameLayout getRootView() {
        ViewGroup parent = (ViewGroup)this.rootFrameLayout.getParent();
        if (parent != null) {
            parent.removeView(this.rootFrameLayout);
        }

        return this.rootFrameLayout;
    }

    public void showSubmit() {
        this.rootFrameLayout.showSubmit();
    }

    public void showSubmit(QySubmitCallBackListener submitCallBackListener) {
        this.rootFrameLayout.showSubmit(submitCallBackListener);
    }

    public void closeSubmit(boolean isSuccess) {
        this.rootFrameLayout.closeSubmit(isSuccess);
    }

    public void showLoading() {
        this.rootFrameLayout.showLoading();
    }

    public boolean isLoading() {
        return this.rootFrameLayout.isLoading();
    }

    public boolean closeLoading() {
        return this.rootFrameLayout.closeLoading();
    }

    public void showContent() {
        this.rootFrameLayout.showContent();
    }

    public void showEmptyData() {
        this.rootFrameLayout.showEmptyData();
    }

    public void showEmptyData(int iconImage, String textTip) {
        this.rootFrameLayout.showEmptyData(iconImage, textTip);
    }

    public void showNetWorkError() {
        this.rootFrameLayout.showNetWorkError();
    }

    public void showError() {
        this.rootFrameLayout.showError();
    }

    public void showError(int iconImage, String textTip) {
        this.rootFrameLayout.showError(iconImage, textTip);
    }
    public void setOffsetView(View view){
        this.rootFrameLayout.setOffsetView(view);
    }
    public void setOffsetView(View view,boolean isOffsetStatusBar){
        this.rootFrameLayout.setOffsetView(view,isOffsetStatusBar);
    }
    public static final class Builder {
        private Context context;
        private boolean wrapContent = false;
        private ViewStub netWorkErrorVs;
        private int netWorkErrorRetryViewId;
        private ViewStub emptyDataVs;
        private int emptyDataRetryViewId;
        private ViewStub errorVs;
        private int errorRetryViewId;
        private int loadingLayoutResId;
        private int contentLayoutResId;
        private int retryViewId;
        private int emptyDataIconImageId;
        private int emptyDataTextTipId;
        private int errorIconImageId;
        private int errorTextTipId;
        private ViewStub submitVs;
        private int submitTextTipId;
        private QyViewStubLayout errorLayout;
        private QyViewStubLayout emptyDataLayout;
        private OnRetryListener onRetryListener;
        private OnNetworkListener onNetworkListener;
        private QySubmitCallBackListener submitCallBackListener;
        private int aviViewId;
        private int submitViewId;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, boolean wrapContent) {
            this.context = context;
            this.wrapContent = wrapContent;
        }

        public QyStatusLayoutManage.Builder loadingView(@LayoutRes int loadingLayoutResId) {
            this.loadingLayoutResId = loadingLayoutResId;
            return this;
        }

        public QyStatusLayoutManage.Builder submitVs(int submitViewId) {
            this.submitVs = new ViewStub(this.context);
            this.submitVs.setLayoutResource(submitViewId);
            return this;
        }

        public QyStatusLayoutManage.Builder aviViewId(int aviViewId) {
            this.aviViewId = aviViewId;
            return this;
        }

        public QyStatusLayoutManage.Builder submitCallBackListener(QySubmitCallBackListener submitCallBackListener) {
            this.submitCallBackListener = submitCallBackListener;
            return this;
        }

        public QyStatusLayoutManage.Builder netWorkErrorView(@LayoutRes int newWorkErrorId) {
            this.netWorkErrorVs = new ViewStub(this.context);
            this.netWorkErrorVs.setLayoutResource(newWorkErrorId);
            return this;
        }

        public QyStatusLayoutManage.Builder submitViewId(int submitViewId) {
            this.submitViewId = submitViewId;
            return this;
        }

        public QyStatusLayoutManage.Builder emptyDataView(@LayoutRes int noDataViewId) {
            this.emptyDataVs = new ViewStub(this.context);
            this.emptyDataVs.setLayoutResource(noDataViewId);
            return this;
        }

        public QyStatusLayoutManage.Builder errorView(@LayoutRes int errorViewId) {
            this.errorVs = new ViewStub(this.context);
            this.errorVs.setLayoutResource(errorViewId);
            return this;
        }

        public QyStatusLayoutManage.Builder contentView(@LayoutRes int contentLayoutResId) {
            this.contentLayoutResId = contentLayoutResId;
            return this;
        }

        public QyStatusLayoutManage.Builder errorLayout(QyViewStubLayout errorLayout) {
            this.errorLayout = errorLayout;
            this.errorVs = errorLayout.getLayoutVs();
            return this;
        }

        public QyStatusLayoutManage.Builder emptyDataLayout(QyViewStubLayout emptyDataLayout) {
            this.emptyDataLayout = emptyDataLayout;
            this.emptyDataVs = emptyDataLayout.getLayoutVs();
            return this;
        }

        public QyStatusLayoutManage.Builder submitTextTipId(int submitTextTipId) {
            this.submitTextTipId = submitTextTipId;
            return this;
        }

        public QyStatusLayoutManage.Builder netWorkErrorRetryViewId(int netWorkErrorRetryViewId) {
            this.netWorkErrorRetryViewId = netWorkErrorRetryViewId;
            return this;
        }

        public QyStatusLayoutManage.Builder emptyDataRetryViewId(int emptyDataRetryViewId) {
            this.emptyDataRetryViewId = emptyDataRetryViewId;
            return this;
        }

        public QyStatusLayoutManage.Builder errorRetryViewId(int errorRetryViewId) {
            this.errorRetryViewId = errorRetryViewId;
            return this;
        }

        public QyStatusLayoutManage.Builder retryViewId(int retryViewId) {
            this.retryViewId = retryViewId;
            return this;
        }

        public QyStatusLayoutManage.Builder emptyDataIconImageId(int emptyDataIconImageId) {
            this.emptyDataIconImageId = emptyDataIconImageId;
            return this;
        }

        public QyStatusLayoutManage.Builder emptyDataTextTipId(int emptyDataTextTipId) {
            this.emptyDataTextTipId = emptyDataTextTipId;
            return this;
        }

        public QyStatusLayoutManage.Builder errorIconImageId(int errorIconImageId) {
            this.errorIconImageId = errorIconImageId;
            return this;
        }

        public QyStatusLayoutManage.Builder errorTextTipId(int errorTextTipId) {
            this.errorTextTipId = errorTextTipId;
            return this;
        }

        public QyStatusLayoutManage.Builder onRetryListener(OnRetryListener onRetryListener) {
            this.onRetryListener = onRetryListener;
            return this;
        }

        public QyStatusLayoutManage.Builder onNetworkListener(OnNetworkListener onNetworkListener) {
            this.onNetworkListener = onNetworkListener;
            return this;
        }

        public QyStatusLayoutManage build() {
            return new QyStatusLayoutManage(this, this.wrapContent);
        }
    }
}
