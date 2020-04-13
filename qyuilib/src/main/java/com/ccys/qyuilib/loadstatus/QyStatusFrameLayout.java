package com.ccys.qyuilib.loadstatus;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.util.QyScreenUtil;
import com.ccys.qyuilib.view.SubmitStatusView;
import com.wang.avi.AVLoadingIndicatorView;

public class QyStatusFrameLayout extends FrameLayout {
    public static final int LAYOUT_LOADING_ID = 1;
    public static final int LAYOUT_CONTENT_ID = 2;
    public static final int LAYOUT_ERROR_ID = 3;
    public static final int LAYOUT_NETWORK_ERROR_ID = 4;
    public static final int LAYOUT_EMPTY_DATA_ID = 5;
    public static final int LAYOUT_SUBMIT_ID = 6;
    private boolean isLoadEnd;
    private SparseArray<View> layoutSparseArray = new SparseArray();
    private QyStatusLayoutManage mStatusLayoutManage;
    private SubmitStatusView mSubmitStatusView;
    private AVLoadingIndicatorView mAVLoadingIndicatorView;
    private View offsetView;
    private int offsetHeight;
    private boolean isOffsetStatusBar;//是否偏离状态栏
    private int statusBarHeight;//状态栏的高度

    public QyStatusFrameLayout(@NonNull Context context) {
        super(context);
        statusBarHeight = QyScreenUtil.getStatusBarHeight(context);
    }

    public QyStatusFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        statusBarHeight = QyScreenUtil.getStatusBarHeight(context);
    }

    public QyStatusFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        statusBarHeight = QyScreenUtil.getStatusBarHeight(context);
    }

    public void setStatusLayoutManage(QyStatusLayoutManage statusLayoutManage) {
        this.mStatusLayoutManage = statusLayoutManage;
        this.addAllLayoutToRootLayout();
    }
   public void setOffsetView(View view){
        this.offsetView = view;
       if(offsetHeight <= 0){
           offsetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
               @Override
               public void onGlobalLayout() {
                   if(offsetView.getMeasuredHeight() > 0){
                       offsetHeight = offsetView.getMeasuredHeight();
                       offsetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                   }
               }
           });
       }
   }
    public void setOffsetView(View view, final boolean isOffsetStatusBar){
        this.isOffsetStatusBar = isOffsetStatusBar;
        this.offsetView = view;
        if(offsetHeight <= 0){
            offsetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if(offsetView.getMeasuredHeight() > 0){
                        if(isOffsetStatusBar){
                            offsetHeight = offsetView.getMeasuredHeight() + statusBarHeight;
                        }else {
                            offsetHeight = offsetView.getMeasuredHeight();
                        }
                        offsetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }
    public void addAllLayoutToRootLayout() {
        this.addLayoutResId(this.mStatusLayoutManage.contentLayoutResId, LAYOUT_CONTENT_ID);
        this.addLayoutResId(this.mStatusLayoutManage.loadingLayoutResId, LAYOUT_LOADING_ID);
        if (this.mStatusLayoutManage.submitVs != null) {
            this.addView(this.mStatusLayoutManage.submitVs);
        }

        if (this.mStatusLayoutManage.emptyDataVs != null) {
            this.addView(this.mStatusLayoutManage.emptyDataVs);
        }

        if (this.mStatusLayoutManage.errorVs != null) {
            this.addView(this.mStatusLayoutManage.errorVs);
        }

        if (this.mStatusLayoutManage.netWorkErrorVs != null) {
            this.addView(this.mStatusLayoutManage.netWorkErrorVs);
        }

    }

    public View getContentView() {
        return (View)this.layoutSparseArray.get(2);
    }

    public void addLayoutResId(int layoutResId, int id) {
        final View resView = LayoutInflater.from(this.mStatusLayoutManage.context).inflate(layoutResId, this, false);
        this.layoutSparseArray.put(id, resView);
        if (id == LAYOUT_LOADING_ID) {
            if (this.mStatusLayoutManage.aviViewId != 0) {
                this.mAVLoadingIndicatorView = (AVLoadingIndicatorView)resView.findViewById(this.mStatusLayoutManage.aviViewId);
                this.mAVLoadingIndicatorView.smoothToHide();
            }
            resView.setVisibility(View.GONE);
        }
        resView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });
        this.addView(resView);
        if(id == LAYOUT_LOADING_ID){
            if(offsetView != null){
                if(offsetHeight <= 0){
                    offsetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            if(offsetView.getMeasuredHeight() > 0){
                                if(isOffsetStatusBar){
                                    offsetHeight = offsetView.getMeasuredHeight() + statusBarHeight;
                                }else {
                                    offsetHeight = offsetView.getMeasuredHeight();
                                }
                                FrameLayout.LayoutParams lp = (LayoutParams) resView.getLayoutParams();
                                lp.topMargin = offsetHeight;
                                offsetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        }
                    });
                }else {
                    FrameLayout.LayoutParams lp = (LayoutParams) resView.getLayoutParams();
                    lp.topMargin = offsetHeight;
                }
            }
        }
    }

    public boolean isLoadEnd() {
        return this.isLoadEnd;
    }

    public void showSubmit(final QySubmitCallBackListener submitCallBackListener) {
        if (this.inflateLayout(LAYOUT_SUBMIT_ID) && this.layoutSparseArray.get(LAYOUT_SUBMIT_ID) != null) {
            final View view = (View)this.layoutSparseArray.get(LAYOUT_SUBMIT_ID);
            if (view != null && view.getVisibility() != VISIBLE) {
                this.isLoadEnd = false;
                this.showHideViewById(LAYOUT_SUBMIT_ID);
                if (this.mSubmitStatusView != null) {
                    this.mSubmitStatusView.startAnimator();
                    this.mSubmitStatusView.setFinishListener(new SubmitStatusView.AnimFinishListener() {
                        public void onFinish(boolean status) {
                            QyStatusFrameLayout.this.mSubmitStatusView.cleanAnimator();
                            QyStatusFrameLayout.this.mSubmitStatusView.removeFinishListener();
                            view.setVisibility(View.GONE);
                            if (submitCallBackListener != null) {
                                submitCallBackListener.submitCallBack(status);
                            }

                        }
                    });
                }
            }
        }

    }

    public void showSubmit() {
        if (this.inflateLayout(LAYOUT_SUBMIT_ID) && this.layoutSparseArray.get(LAYOUT_SUBMIT_ID) != null) {
            final View view = (View)this.layoutSparseArray.get(LAYOUT_SUBMIT_ID);
            if (view != null && view.getVisibility() != VISIBLE) {
                this.isLoadEnd = false;
                this.showHideViewById(LAYOUT_SUBMIT_ID);
                if (this.mSubmitStatusView != null) {
                    this.mSubmitStatusView.startAnimator();
                    this.mSubmitStatusView.setFinishListener(new SubmitStatusView.AnimFinishListener() {
                        public void onFinish(boolean status) {
                            QyStatusFrameLayout.this.mSubmitStatusView.cleanAnimator();
                            QyStatusFrameLayout.this.mSubmitStatusView.removeFinishListener();
                            view.setVisibility(GONE);
                            if (QyStatusFrameLayout.this.mStatusLayoutManage.submitCallBackListener != null) {
                                QyStatusFrameLayout.this.mStatusLayoutManage.submitCallBackListener.submitCallBack(status);
                            }

                        }
                    });
                }
            }
        }

    }

    public void closeSubmit(boolean isSuccess) {
        if (this.layoutSparseArray.get(LAYOUT_SUBMIT_ID) != null) {
            this.isLoadEnd = true;
            if (this.mSubmitStatusView != null) {
                this.mSubmitStatusView.callBack(isSuccess);
            } else {
                View view = (View)this.layoutSparseArray.get(LAYOUT_SUBMIT_ID);
                view.setVisibility(GONE);
            }
        }

    }

    public void showLoading() {
        if (this.layoutSparseArray.get(LAYOUT_LOADING_ID) != null) {
            View view = (View)this.layoutSparseArray.get(LAYOUT_LOADING_ID);
            if (view != null && view.getVisibility() != VISIBLE) {
                this.isLoadEnd = false;
                this.showHideViewById(LAYOUT_LOADING_ID);
                if (this.mAVLoadingIndicatorView != null) {
                    this.mAVLoadingIndicatorView.smoothToShow();
                }
            }
        }

    }

    public boolean isLoading() {
        View view = (View)this.layoutSparseArray.get(LAYOUT_LOADING_ID);
        if (view != null) {
            return view.getVisibility() == VISIBLE;
        } else {
            return false;
        }
    }

    public boolean closeLoading() {
        if (this.layoutSparseArray.get(LAYOUT_LOADING_ID) != null) {
            View view = (View)this.layoutSparseArray.get(LAYOUT_LOADING_ID);
            if (view.getVisibility() == VISIBLE) {
                if (this.mAVLoadingIndicatorView != null) {
                    this.mAVLoadingIndicatorView.smoothToHide();
                }

                view.setVisibility(GONE);
                this.isLoadEnd = true;
                return true;
            } else {
                this.isLoadEnd = true;
                return false;
            }
        } else {
            this.isLoadEnd = true;
            return false;
        }
    }

    public void showContent() {
        if (this.layoutSparseArray.get(LAYOUT_CONTENT_ID) != null) {
            this.isLoadEnd = true;
            this.closeLoading();
            this.showHideViewById(LAYOUT_CONTENT_ID);
        }

    }

    public void showEmptyData(int iconImage, String textTip) {
        if (this.inflateLayout(LAYOUT_EMPTY_DATA_ID)) {
            View view = (View)this.layoutSparseArray.get(LAYOUT_EMPTY_DATA_ID);
            if (view != null && view.getVisibility() != VISIBLE) {
                this.isLoadEnd = true;
                this.showHideViewById(LAYOUT_EMPTY_DATA_ID);
                this.emptyDataViewAddData(iconImage, textTip);
            }
        }

    }

    public void showEmptyData() {
        if (this.inflateLayout(LAYOUT_EMPTY_DATA_ID)) {
            View view = (View)this.layoutSparseArray.get(LAYOUT_EMPTY_DATA_ID);
            if (view != null && view.getVisibility() != VISIBLE) {
                this.isLoadEnd = true;
                this.showHideViewById(LAYOUT_EMPTY_DATA_ID);
                this.emptyDataViewAddData(R.drawable.view_icon_no_data, this.mStatusLayoutManage.context.getResources().getString(R.string.not_data_tip));
            }
        }

    }

    private void emptyDataViewAddData(int iconImage, String textTip) {
        if (iconImage != 0 || !TextUtils.isEmpty(textTip)) {
            View emptyDataView = (View)this.layoutSparseArray.get(LAYOUT_EMPTY_DATA_ID);
            View iconImageView = emptyDataView.findViewById(this.mStatusLayoutManage.emptyDataIconImageId);
            View textView = emptyDataView.findViewById(this.mStatusLayoutManage.emptyDataTextTipId);
            if (iconImageView != null && iconImageView instanceof ImageView) {
                ((ImageView)iconImageView).setImageResource(iconImage);
            }

            if (textView != null && textView instanceof TextView) {
                ((TextView)textView).setText(textTip);
            }

        }
    }

    public void showNetWorkError() {
        if (this.inflateLayout(LAYOUT_NETWORK_ERROR_ID)) {
            View view = (View)this.layoutSparseArray.get(LAYOUT_NETWORK_ERROR_ID);
            if (view != null && view.getVisibility() != VISIBLE) {
                this.isLoadEnd = true;
                this.showHideViewById(LAYOUT_NETWORK_ERROR_ID);
            }
        }

    }

    public void showError(int iconImage, String textTip) {
        if (this.inflateLayout(LAYOUT_ERROR_ID)) {
            View view = (View)this.layoutSparseArray.get(LAYOUT_ERROR_ID);
            if (view != null && view.getVisibility() != VISIBLE) {
                this.isLoadEnd = true;
                this.showHideViewById(LAYOUT_ERROR_ID);
                this.errorViewAddData(iconImage, textTip);
            }
        }

    }

    public void showError() {
        if (this.inflateLayout(LAYOUT_ERROR_ID)) {
            View view = (View)this.layoutSparseArray.get(LAYOUT_ERROR_ID);
            if (view != null && view.getVisibility() != VISIBLE) {
                this.isLoadEnd = true;
                this.showHideViewById(LAYOUT_ERROR_ID);
                this.errorViewAddData(R.drawable.default_error, this.mStatusLayoutManage.context.getResources().getString(R.string.error_tip));
            }
        }

    }

    private void errorViewAddData(int iconImage, String textTip) {
        if (iconImage != 0 || !TextUtils.isEmpty(textTip)) {
            View errorView = (View)this.layoutSparseArray.get(LAYOUT_ERROR_ID);
            View iconImageView = errorView.findViewById(this.mStatusLayoutManage.errorIconImageId);
            View textView = errorView.findViewById(this.mStatusLayoutManage.errorTextTipId);
            if (iconImageView != null && iconImageView instanceof ImageView) {
                ((ImageView)iconImageView).setImageResource(iconImage);
            }

            if (textView != null && textView instanceof TextView) {
                ((TextView)textView).setText(textTip);
            }

        }
    }

    public void showLayoutError(Object objects) {
        if (this.inflateLayout(LAYOUT_ERROR_ID)) {
            View view = (View)this.layoutSparseArray.get(LAYOUT_ERROR_ID);
            if (view != null && view.getVisibility() != VISIBLE) {
                this.isLoadEnd = true;
                this.showHideViewById(LAYOUT_ERROR_ID);
                QyViewStubLayout errorLayout = this.mStatusLayoutManage.errorLayout;
                if (errorLayout != null) {
                    errorLayout.setData(objects);
                }
            }
        }

    }

    private void showHideViewById(int id) {
        for(int i = 0; i < this.layoutSparseArray.size(); ++i) {
            int key = this.layoutSparseArray.keyAt(i);
            View valueView = (View)this.layoutSparseArray.valueAt(i);
            if (key == id) {
                if (valueView.getVisibility() != VISIBLE) {
                    valueView.setVisibility(VISIBLE);
                }
            } else if (valueView.getVisibility() != GONE && key != LAYOUT_CONTENT_ID) {
                if (key == LAYOUT_LOADING_ID && this.mAVLoadingIndicatorView != null) {
                    this.mAVLoadingIndicatorView.smoothToHide();
                }

                if (key == LAYOUT_SUBMIT_ID && this.mSubmitStatusView != null) {
                    this.mSubmitStatusView.removeFinishListener();
                    this.mSubmitStatusView.cleanAnimator();
                }

                valueView.setVisibility(GONE);
            }
        }

    }

    private boolean inflateLayout(int id) {
        boolean isShow = true;
        if (this.layoutSparseArray.get(id) != null) {
            return isShow;
        } else {
            final View view;
            switch(id) {
                case LAYOUT_ERROR_ID:
                    if (this.mStatusLayoutManage.errorVs != null) {
                        view = this.mStatusLayoutManage.errorVs.inflate();
                        if(offsetView != null){
                            if(offsetHeight <= 0){
                                offsetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        if(offsetView.getMeasuredHeight() > 0){
                                            if(isOffsetStatusBar){
                                                offsetHeight = offsetView.getMeasuredHeight() + statusBarHeight;
                                            }else {
                                                offsetHeight = offsetView.getMeasuredHeight();
                                            }
                                            FrameLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
                                            lp.topMargin = offsetHeight;
                                            offsetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                        }
                                    }
                                });
                            }else {
                                FrameLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
                                lp.topMargin = offsetHeight;
                            }
                        }
                        view.setVisibility(GONE);
                        if (this.mStatusLayoutManage.errorLayout != null) {
                            this.mStatusLayoutManage.errorLayout.setView(view);
                        }

                        view.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                QyStatusFrameLayout.this.mStatusLayoutManage.onRetryListener.onRetry();
                            }
                        });
                        this.layoutSparseArray.put(id, view);
                        isShow = true;
                    } else {
                        isShow = false;
                    }
                    break;
                case LAYOUT_NETWORK_ERROR_ID:
                    if (this.mStatusLayoutManage.netWorkErrorVs != null) {
                        view = this.mStatusLayoutManage.netWorkErrorVs.inflate();
                        if(offsetView != null){
                            if(offsetHeight <= 0){
                                offsetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        if(offsetView.getMeasuredHeight() > 0){
                                            if(isOffsetStatusBar){
                                                offsetHeight = offsetView.getMeasuredHeight() + statusBarHeight;
                                            }else {
                                                offsetHeight = offsetView.getMeasuredHeight();
                                            }
                                            FrameLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
                                            lp.topMargin = offsetHeight;
                                            offsetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                        }
                                    }
                                });
                            }else {
                                FrameLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
                                lp.topMargin = offsetHeight;
                            }
                        }
                        view.setVisibility(GONE);
                        view.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                if (QyStatusFrameLayout.this.mStatusLayoutManage.onNetworkListener != null) {
                                    QyStatusFrameLayout.this.mStatusLayoutManage.onNetworkListener.onNetwork();
                                } else {
                                    QyStatusFrameLayout.this.mStatusLayoutManage.onRetryListener.onRetry();
                                }

                            }
                        });
                        this.layoutSparseArray.put(id, view);
                        isShow = true;
                    } else {
                        isShow = false;
                    }
                    break;
                case LAYOUT_EMPTY_DATA_ID:
                    if (this.mStatusLayoutManage.emptyDataVs != null) {
                        view = this.mStatusLayoutManage.emptyDataVs.inflate();
                        if(offsetView != null){
                            if(offsetHeight <= 0){
                                offsetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        if(offsetView.getMeasuredHeight() > 0){
                                            if(isOffsetStatusBar){
                                                offsetHeight = offsetView.getMeasuredHeight() + statusBarHeight;
                                            }else {
                                                offsetHeight = offsetView.getMeasuredHeight();
                                            }
                                            FrameLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
                                            lp.topMargin = offsetHeight;
                                            offsetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                        }
                                    }
                                });
                            }else {
                                FrameLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
                                lp.topMargin = offsetHeight;
                            }
                        }
                        view.setVisibility(GONE);
                        if (this.mStatusLayoutManage.emptyDataLayout != null) {
                            this.mStatusLayoutManage.emptyDataLayout.setView(view);
                        }

                        view.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                QyStatusFrameLayout.this.mStatusLayoutManage.onRetryListener.onRetry();
                            }
                        });
                        this.layoutSparseArray.put(id, view);
                        isShow = true;
                    } else {
                        isShow = false;
                    }
                    break;
                case LAYOUT_SUBMIT_ID:
                    if (this.mStatusLayoutManage.submitVs != null) {
                        view = this.mStatusLayoutManage.submitVs.inflate();
                        if(offsetView != null){
                            if(offsetHeight <= 0){
                                offsetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        if(offsetView.getMeasuredHeight() > 0){
                                            if(isOffsetStatusBar){
                                                offsetHeight = offsetView.getMeasuredHeight() + statusBarHeight;
                                            }else {
                                                offsetHeight = offsetView.getMeasuredHeight();
                                            }
                                            FrameLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
                                            lp.topMargin = offsetHeight;
                                            offsetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                        }
                                    }
                                });
                            }else {
                                FrameLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
                                lp.topMargin = offsetHeight;
                            }
                        }
                        view.setVisibility(GONE);
                        this.layoutSparseArray.put(id, view);
                        if (this.mStatusLayoutManage.submitViewId != 0) {
                            this.mSubmitStatusView = (SubmitStatusView)view.findViewById(this.mStatusLayoutManage.submitViewId);
                        }

                        view.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                            }
                        });
                        isShow = true;
                    } else {
                        isShow = false;
                    }
            }

            return isShow;
        }
    }
}
