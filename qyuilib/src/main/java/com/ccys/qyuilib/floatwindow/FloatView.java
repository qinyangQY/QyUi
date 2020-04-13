package com.ccys.qyuilib.floatwindow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.adapter.QyRecyclerViewHolder;
import com.ccys.qyuilib.adapter.QyRecyclerviewAdapter;
import com.ccys.qyuilib.interfaces.OnItemViewTypeLayout;
import com.ccys.qyuilib.interfaces.OnRecyclerViewMultiItemBindView;
import com.ccys.qyuilib.util.GsonUtil;
import com.ccys.qyuilib.util.QyDisplayUtil;
import com.ccys.qyuilib.util.QyScreenUtil;
import com.ccys.qyuilib.view.QyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FloatView extends FrameLayout implements View.OnClickListener, OnRecyclerViewMultiItemBindView<LogEntity>, OnItemViewTypeLayout<LogEntity> {
    private Context mContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams layoutParams;
    private int displayWidth;
    private int displayHeight;
    private int statusBarHeight;
    private View contentView;
    private RelativeLayout reContent;//放大后的内容布局
    private RelativeLayout reSmall;//初始化的圆形布局
    private QyRecyclerView recycler;

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private TextView tv_close;
    private float downX;
    private float downY;
    private boolean isMove;//判断是不是移动事件
    private boolean isOpen;//是否是展开放大的状态

    //window的大小
    private int minWidth;
    private int minHeight;
    //window的大小
    private int maxWidth;
    private int maxHeight;

    private TextView tv_clean;

    private boolean isAnimatorRun;//动画是否在运行

    private QyRecyclerviewAdapter<LogEntity> adapter;


    public FloatView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }
   private void initView(Context context){
       mContext = context;
        displayWidth = QyScreenUtil.getScreenSize()[0];
        displayHeight = QyScreenUtil.getScreenSize()[1];
        statusBarHeight = QyScreenUtil.getStatusBarHeight(context);
        maxWidth = displayWidth;
        maxHeight = (int) ((displayHeight - statusBarHeight) * 0.9);
        minWidth = QyDisplayUtil.dp2px(context,60);
        minHeight = QyDisplayUtil.dp2px(context,60);
       LayoutInflater inflater = LayoutInflater.from(context);
       contentView = inflater.inflate(R.layout.float_window_layout,this,false);
       tv_clean = contentView.findViewById(R.id.tv_clean);
       FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
       contentView.setLayoutParams(lp);
       reContent = contentView.findViewById(R.id.reContent);
       reSmall = contentView.findViewById(R.id.reSmall);
       recycler = contentView.findViewById(R.id.recycler);
       reContent.setVisibility(INVISIBLE);
       reSmall.setVisibility(VISIBLE);
       tv_close = contentView.findViewById(R.id.tv_close);
       this.removeAllViews();
       this.addView(contentView);
       tv_close.setOnClickListener(this);
       tv_clean.setOnClickListener(this);
       isOpen = false;
       adapter = new QyRecyclerviewAdapter<LogEntity>(context,this);
       recycler.setAdapter(adapter);
       adapter.setOnRecyclerViewMultiItemBindView(this);
       LinearLayoutManager lm = (LinearLayoutManager) recycler.getLayoutManager();
       lm.setStackFromEnd(true);
       lm.setReverseLayout(true);
   }
    public void setWindowManager(WindowManager mWindowManager) {
        this.mWindowManager = mWindowManager;
    }

    public void setWindowLayoutParams(WindowManager.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
        this.layoutParams.x = displayWidth;
    }
    public WindowManager.LayoutParams getWindowLayoutParams(){
        return layoutParams;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getRawX(); // 触摸点相对屏幕的x坐标
        y = event.getRawY() - statusBarHeight; // 触摸点相对于屏幕的y坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = event.getX();// 触摸点在View内的相对x坐标
                mTouchStartY = event.getY();// 触摸点在View内的相对Y坐标
                downX = event.getRawX();
                downY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                isMove = isMove();
                if(isMove){
                    updateViewPosition(); //  跟新window布局参数
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isMove){
                    isMove = false;
                    if (layoutParams.x <= 0) {  //窗口贴附在左边
                        layoutParams.x = Math.abs(layoutParams.x) <= displayWidth / 2 ? -displayWidth : displayWidth;
                    } else {  //窗口贴附在右边
                        layoutParams.x = layoutParams.x <= displayWidth / 2 ? displayWidth : -displayWidth;
                    }
                    layoutParams.y = (int) (y - displayHeight / 2);// 跟新y坐标
                    mWindowManager.updateViewLayout(this, layoutParams);
                }else {//点击
                    if(!isAnimatorRun){
                        updateOpenStatus();
                    }
                }
                break;
        }
        return true;
    }
    private void updateOpenStatus(){
        if(isOpen){
            isOpen = false;
             closeAnimation();
        }else {
            isOpen = true;
            openAnimation();
        }
    }

    /**
     * 展开的动画
     */
    private void openAnimation(){
        isAnimatorRun = true;
        reSmall.setVisibility(INVISIBLE);
        layoutParams.width = maxWidth;
        layoutParams.height = maxHeight;
        layoutParams.x = 0;
        layoutParams.y = 0;
        mWindowManager.updateViewLayout(FloatView.this, layoutParams);
        new Handler(mContext.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                reContent.setVisibility(VISIBLE);
                //圆形动画
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) reContent.getLayoutParams();
                int centerX = (maxWidth - (lp.getMarginEnd() * 2)) / 2;
                int centerY = (maxHeight - (lp.getMarginEnd() * 2)) / 2;
                final Animator circularAnimator =  ViewAnimationUtils.createCircularReveal(reContent,centerX,centerY,
                        (float) Math.hypot(minWidth,minHeight) / 2,(float) Math.hypot(maxWidth, maxHeight) /2);
                circularAnimator.setDuration(500);
                circularAnimator.start();
                circularAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isAnimatorRun = false;
                        circularAnimator.removeAllListeners();
                    }
                });
            }
        },200);
    }
    private void closeAnimation(){
        //圆形动画
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) reContent.getLayoutParams();
        int centerX = (maxWidth - (lp.getMarginEnd() * 2)) / 2;
        int centerY = (maxHeight - (lp.getMarginEnd() * 2)) / 2;
        final Animator circularAnimator =  ViewAnimationUtils.createCircularReveal(reContent,centerX,centerY,
                (float) Math.hypot(maxWidth, maxHeight) /2,0);
        circularAnimator.setDuration(500);
        circularAnimator.start();
        isAnimatorRun = true;
        circularAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimatorRun = false;
                reContent.setVisibility(INVISIBLE);
                layoutParams.width = minWidth;
                layoutParams.height = minHeight;
                layoutParams.x = displayWidth;
                layoutParams.y = 0;
                mWindowManager.updateViewLayout(FloatView.this, layoutParams);
                new Handler(mContext.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reSmall.setVisibility(VISIBLE);
                    }
                },200);
                circularAnimator.removeAllListeners();
            }
        });
    }
    /**
     * 判断是点击还是移动
     * @return
     */
    private boolean isMove(){
        float dx = Math.abs(downX - x);
        float dy = Math.abs(downY - y);
        if(dx < 50 && dy < 50){
            return false;
        }else {
            return true;
        }
    }
    private void updateViewPosition() {
        layoutParams.gravity = Gravity.NO_GRAVITY;
        //更新浮动窗口位置参数
        int dx = (int) (mTouchStartX - x);
        int dy = (int) (y-displayHeight / 2);
        if (Math.abs(dx) > displayWidth / 2) {
            layoutParams.x = Math.abs(dx) - displayWidth / 2;
        } else {
            layoutParams.x = -(displayWidth / 2 - Math.abs(dx));
        }
        layoutParams.y = dy;
        mWindowManager.updateViewLayout(this, layoutParams);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_close){
            updateOpenStatus();
        }else if(v.getId() == R.id.tv_clean){
          adapter.cleanData();
        }
    }
    public void addData(LogEntity logEntity){
        adapter.addData(logEntity);
    }
    @Override
    public void setMultiItemBindViewHolder(final QyRecyclerViewHolder holder, final LogEntity bean, int layoutType, int position) {
        switch (layoutType){
            case 1://请求参数相关
                 if(TextUtils.isEmpty(bean.getUrl())){
                     holder.setVisibility(R.id.tv_url,View.GONE);
                 }else {
                     holder.setVisibility(R.id.tv_url,View.VISIBLE);
                     holder.setText(R.id.tv_url,bean.getUrl());
                 }
                if(TextUtils.isEmpty(bean.getHeaders())){
                    holder.setVisibility(R.id.tv_header,View.GONE);
                }else {
                    holder.setVisibility(R.id.tv_header,View.VISIBLE);
                    holder.setText(R.id.tv_header,bean.getHeaders());
                }
                if(TextUtils.isEmpty(bean.getParams())){
                    holder.setVisibility(R.id.tv_params,View.GONE);
                }else {
                    holder.setVisibility(R.id.tv_params,View.VISIBLE);
                    holder.setText(R.id.tv_params,bean.getParams());
                }
                break;
            case 2://请求完成后台响应
                 if(bean.getType() == LogEntity.Type.SUCCESS){
                     holder.setVisibility(R.id.tv_success,View.VISIBLE);
                     holder.setVisibility(R.id.tv_error,View.GONE);
                     TextView tv_success = (TextView) holder.getView(R.id.tv_success);
                     tv_success.setText(Html.fromHtml(GsonUtil.formatJson(bean.getContent())));
                     holder.setVisibility(R.id.tv_open,View.VISIBLE);
                 }else {
                     holder.setVisibility(R.id.tv_success,View.GONE);
                     holder.setVisibility(R.id.tv_error,View.VISIBLE);
                     holder.setText(R.id.tv_error,bean.getContent());
                 }
                if(bean.isOpen()){
                    holder.setText(R.id.tv_open,"折叠");
                      if(bean.getType() == LogEntity.Type.SUCCESS){
                          holder.setVisibility(R.id.tv_success,View.VISIBLE);
                      }else {
                          holder.setVisibility(R.id.tv_error,View.VISIBLE);
                      }
                }else {
                    holder.setText(R.id.tv_open,"展开");
                    if(bean.getType() == LogEntity.Type.SUCCESS){
                        holder.setVisibility(R.id.tv_success,View.GONE);
                    }else {
                        holder.setVisibility(R.id.tv_error,View.GONE);
                    }
                }
                holder.setOnClickListener(R.id.tv_open, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(bean.isOpen()){
                            adapter.getData().get(holder.getLayoutPosition()).setOpen(false);
                        }else {
                            adapter.getData().get(holder.getLayoutPosition()).setOpen(true);
                        }
                        adapter.notifyItemChanged(holder.getLayoutPosition());
                    }
                });
                break;
        }
    }

    @Override
    public int[] setItemViewType(LogEntity logEntity, int position) {
        int[] viewType = new int[2];
        if(logEntity.getType() == LogEntity.Type.REQUEST){
            viewType[0] = 1;
            viewType[1] = R.layout.float_window_heade_layout;
        }else {
            viewType[0] = 2;
            viewType[1] = R.layout.float_window_content_layout;
        }
        return viewType;
    }
}
