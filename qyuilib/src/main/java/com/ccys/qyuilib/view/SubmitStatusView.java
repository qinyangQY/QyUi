package com.ccys.qyuilib.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.util.QyDisplayUtil;


/**
 * 包名：com.qinyang.qyuilib.view
 * 创建人：秦洋
 * 创建时间：2019/3/9
 * 提交状态的显示view
 */
public class SubmitStatusView extends View {
    private Paint mPain;
    private int startAngle;//进度条的开始角度
    private int progressWidth;//进度条的宽度
    private int progressColor;//进度条的颜色
    private int mSize;//自己的大小
    private int defaultSize;
    private ValueAnimator progressAnimation;
    private boolean isCallBack;//服务器是否返回
    private boolean isCallBackSuccess;//网络操作是否成功
    private int hookLine1X;
    private int hookLine1Y;
    private int hookLine2X;
    private int hookLine2Y;
    private boolean drawHookLine1;//绘制对号的第一段
    private boolean drawHookLine2;//是否绘制对号的第二段
    private boolean drawForkLine1;//是否绘制叉的第一段
    private boolean drawForkLine2;//是否绘制叉的第二段
    private int forkLine1X;
    private int forkLine1Y;
    private int forkLine2X;
    private int forkLine2Y;
    private int hookStartPointX;//对号的起点
    private int hookStartPointY;
    private int hookStickPointX;//对号的拐点
    private int hookStickPointY;
    private int hookEndPointX;//对号的终点
    private int hookEndPointY;
    private int forkStartX1;
    private int forkStartY1;
    private int forkEndX1;
    private int forkEndY1;
    private int forkStartX2;
    private int forkStartY2;
    private int forkEndX2;
    private int forkEndY2;
    private AnimFinishListener finishListener;
    private int animTime;//动画执行的时间
    private Context context;
    public SubmitStatusView(Context context) {
        this(context,null);
    }

    public SubmitStatusView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SubmitStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }
   public void setFinishListener(AnimFinishListener finishListener){
        this.finishListener = finishListener;
   }
   public void removeFinishListener(){
        this.finishListener = null;
   }
    //初始化view
    private void initView(Context context,AttributeSet attrs){
        this.context = context;
        animTime = 300;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SubmitStatusView);
        progressWidth = (int) ta.getDimension(R.styleable.SubmitStatusView_qy_submit_status_progress_width, QyDisplayUtil.dp2px(context,3));
        progressColor = ta.getColor(R.styleable.SubmitStatusView_qy_submit_status_progress_color,Color.parseColor("#ffffff"));
        defaultSize = (int) ta.getDimension(R.styleable.SubmitStatusView_qy_submit_status_default_size,QyDisplayUtil.dp2px(context,60));
        ta.recycle();
        mPain = new Paint();
        progressAnimation = ValueAnimator.ofInt(0,360);
        progressAnimation.setDuration(600);
        progressAnimation.setRepeatCount(ValueAnimator.INFINITE);
        progressAnimation.setInterpolator(new LinearInterpolator());
        if(progressWidth > QyDisplayUtil.dp2px(context,10)){
            progressWidth = QyDisplayUtil.dp2px(context,10);
        }
    }
    public void setAnimTime(int time){
        this.animTime = time;
    }
    public void setProgressWidth(int progressWidth){
        if(progressWidth > 10){
            this.progressWidth = QyDisplayUtil.dp2px(context,10);
        }else {
            this.progressWidth =  QyDisplayUtil.dp2px(context,progressWidth);
        }
    }
    public void setProgressColor(int color){
        this.progressColor = color;
    }
    public void setDefaultSize(int size){
        this.defaultSize = QyDisplayUtil.dp2px(context,size);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = Math.min(getMeasureWidth(widthMeasureSpec),getMeasureHeight(heightMeasureSpec));
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(QyDisplayUtil.dp2px(context,size),MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(size,MeasureSpec.EXACTLY));
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        mSize = getMeasuredWidth();
        initHookPoint();
        initForkPoint();
    }

    private int getMeasureWidth(int widthMeasureSpec){
        int model = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        switch (model){
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                width = defaultSize+this.getPaddingLeft()+this.getPaddingRight();
                break;
            case MeasureSpec.EXACTLY:
                width = size + this.getPaddingLeft()+this.getPaddingRight();
                break;
        }
        return width;
    }
    private int getMeasureHeight(int heightMeasureSpec){
        int model = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (model){
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                height = defaultSize+this.getPaddingTop()+this.getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                height = size + this.getPaddingTop()+this.getPaddingBottom();
                break;
        }
        return height;
    }
    private void initHookPoint(){
        int offset = QyDisplayUtil.dp2px(context,5);
        int radius = (mSize - progressWidth)/2;
        hookStartPointX = (int) (mSize/2 - radius*0.3 - offset);
        hookStartPointY = (int) (mSize*0.7 - offset);
        hookStickPointX = (int) (mSize/2+radius*0.3 -offset);
        hookStickPointY = (int) (mSize*0.7 - offset);
        hookEndPointX = (int) (mSize/2+radius*0.3 - offset);
        hookEndPointY = (int) (mSize/2*0.6 - offset);
    }
    private void initForkPoint(){
        int radius = (mSize - progressWidth)/2;
        forkStartX1 = (int) (mSize/2 - radius * 0.3);
        forkStartY1 = (int) (mSize/2 - radius * 0.3);
        forkEndX1 = mSize / 2 + mSize/2 - forkStartX1;
        forkEndY1 = mSize / 2 +  mSize/2 - forkStartY1;
        forkStartX2 = forkEndX1;
        forkStartY2 = forkStartY1;
        forkEndX2 = forkStartX1;
        forkEndY2 = forkEndY1;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isCallBack){//服务器返回了进度消失
            drawCircle(canvas);
            if(isCallBackSuccess){//成功 对号
                drawHook(canvas);
            }else {//失败 叉
                drawFork(canvas);
            }
        }else {//服务器没有返回执行进度动画
            drawProgess(canvas);
        }
    }

    //绘制进度条
     private void drawProgess(Canvas canvas){
        mPain.reset();
        mPain.setAntiAlias(true);
        mPain.setStyle(Paint.Style.STROKE);
        mPain.setStrokeWidth(progressWidth);
        mPain.setColor(progressColor);
         mPain.setStrokeCap(Paint.Cap.ROUND);
        RectF rectF = new RectF(progressWidth,progressWidth,mSize - progressWidth,mSize - progressWidth);
        canvas.drawArc(rectF,startAngle,270,false,mPain);
     }

     //绘制圆
     private void drawCircle(Canvas canvas){
         mPain.reset();
         mPain.setAntiAlias(true);
         mPain.setStyle(Paint.Style.STROKE);
         mPain.setStrokeWidth(progressWidth);
         mPain.setStrokeCap(Paint.Cap.ROUND);
         mPain.setColor(progressColor);
         canvas.drawCircle(mSize/2,mSize/2,mSize/2 - progressWidth,mPain);
     }
     //绘制对号
     private void drawHook(Canvas canvas){
         mPain.reset();
         mPain.setAntiAlias(true);
         mPain.setStyle(Paint.Style.STROKE);
         mPain.setStrokeWidth(progressWidth);
         mPain.setColor(progressColor);
         mPain.setStrokeCap(Paint.Cap.ROUND);
         canvas.rotate(30,mSize/2,mSize/2);
         CornerPathEffect cornerPathEffect = new CornerPathEffect(300);
         mPain.setPathEffect(cornerPathEffect);
         if(drawHookLine1){
             canvas.drawLine(hookStartPointX,hookStartPointY,hookLine1X,hookLine1Y,mPain);
         }
         if(drawHookLine2){
             canvas.drawLine(hookStickPointX,hookStickPointY,hookLine2X,hookLine2Y,mPain);
         }
     }
     //绘制叉
     private void drawFork(Canvas canvas){
         mPain.reset();
         mPain.setAntiAlias(true);
         mPain.setStyle(Paint.Style.STROKE);
         mPain.setStrokeWidth(progressWidth);
         mPain.setColor(progressColor);
         mPain.setStrokeCap(Paint.Cap.ROUND);
         if(drawForkLine1){
             canvas.drawLine(forkStartX1,forkStartY1,forkLine1X,forkLine1Y,mPain);
         }
         if(drawForkLine2){
             canvas.drawLine(forkStartX2,forkStartY2,forkLine2X,forkLine2Y,mPain);
         }
     }
     //提交数据开始执行进度条
     public void startAnimator(){
         isCallBack = false;
         if(!progressAnimation.isStarted()){
             startProgressAnimator();
         }
     }
     //网络返回
     public void callBack(boolean isSuccess){
        this.isCallBack = true;
        this.isCallBackSuccess = isSuccess;
        if(progressAnimation != null){
            progressAnimation.cancel();
        }
     }
     //启动进度条动画
     private void startProgressAnimator(){
        progressAnimation.addUpdateListener(new MyUpdateListener(1));
        progressAnimation.addListener(new MyAnimatorStatus(1));
        progressAnimation.start();
     }
     //启动绘制对号的动画
     private void startHookAnimator(){
        ValueAnimator animatorLine1 = new ValueAnimator();
         PropertyValuesHolder xValue1 = PropertyValuesHolder.ofInt("x",hookStartPointX,hookStickPointX);
         PropertyValuesHolder yValue1 = PropertyValuesHolder.ofInt("y",hookStartPointY,hookStickPointY);
         animatorLine1.addUpdateListener(new MyUpdateListener(2));
         animatorLine1.addListener(new MyAnimatorStatus(2));
         animatorLine1.setDuration(animTime);
         animatorLine1.setValues(xValue1,yValue1);
         ValueAnimator animatorLine2 = new ValueAnimator();
         PropertyValuesHolder xValue2 = PropertyValuesHolder.ofInt("x",hookStickPointX,hookEndPointX);
         PropertyValuesHolder yValue2 = PropertyValuesHolder.ofInt("y",hookStickPointY,hookEndPointY);
         animatorLine2.addUpdateListener(new MyUpdateListener(3));
         animatorLine2.addListener(new MyAnimatorStatus(3));
         animatorLine2.setValues(xValue2,yValue2);
         animatorLine2.setDuration(animTime);
         AnimatorSet animatorSet = new AnimatorSet();
         animatorSet.play(animatorLine1).before(animatorLine2);
         animatorSet.start();
     }
     //启动绘制叉的动画
    private void startForkAnimator(){
        ValueAnimator animatorLine1 = new ValueAnimator();
        PropertyValuesHolder xValue1 = PropertyValuesHolder.ofInt("x",forkStartX1,forkEndX1);
        PropertyValuesHolder yValue1 = PropertyValuesHolder.ofInt("y",forkStartY1,forkEndY1);
        animatorLine1.addUpdateListener(new MyUpdateListener(4));
        animatorLine1.addListener(new MyAnimatorStatus(4));
        animatorLine1.setDuration(animTime);
        animatorLine1.setValues(xValue1,yValue1);
        ValueAnimator animatorLine2 = new ValueAnimator();
        PropertyValuesHolder xValue2 = PropertyValuesHolder.ofInt("x",forkStartX2,forkEndX2);
        PropertyValuesHolder yValue2 = PropertyValuesHolder.ofInt("y",forkStartY2,forkEndY2);
        animatorLine2.addUpdateListener(new MyUpdateListener(5));
        animatorLine2.addListener(new MyAnimatorStatus(5));
        animatorLine2.setValues(xValue2,yValue2);
        animatorLine2.setDuration(animTime);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorLine1).before(animatorLine2);
        animatorSet.start();
    }
    public void cleanAnimator(){
        this.clearAnimation();
        progressAnimation.removeAllListeners();
        progressAnimation.cancel();
        InitAnimator();
    }
     private class MyUpdateListener implements ValueAnimator.AnimatorUpdateListener{
        private int type;
        public MyUpdateListener(int type){
            this.type = type;
        }
         @Override
         public void onAnimationUpdate(ValueAnimator animation) {
             if(SubmitStatusView.this.getVisibility() != VISIBLE){
                 animation.removeAllListeners();
                 animation.cancel();
                 SubmitStatusView.this.clearAnimation();
                 InitAnimator();
             }
             if(type == 1){
                 int value = (int) animation.getAnimatedValue();
                 startAngle = value;
                 invalidate();
             }else if(type == 2){
                  drawHookLine1 = true;
                  drawHookLine2 = false;
                  hookLine1X = (int) animation.getAnimatedValue("x");
                  hookLine1Y = (int) animation.getAnimatedValue("y");
                  invalidate();
             }else if(type == 3){
                 drawHookLine1 = true;
                 drawHookLine2 = true;
                 hookLine2X = (int) animation.getAnimatedValue("x");
                 hookLine2Y = (int) animation.getAnimatedValue("y");
                 invalidate();
             }else if(type == 4){
                 drawForkLine1 = true;
                 drawForkLine2 = false;
                 forkLine1X = (int) animation.getAnimatedValue("x");
                 forkLine1Y = (int) animation.getAnimatedValue("y");
                 invalidate();
             }else if(type == 5){
                 drawForkLine1 = true;
                 drawForkLine2 = true;
                 forkLine2X = (int) animation.getAnimatedValue("x");
                 forkLine2Y = (int) animation.getAnimatedValue("y");
                 invalidate();
             }
         }
     }
     private class MyAnimatorStatus extends AnimatorListenerAdapter{
         private int type;
         public MyAnimatorStatus(int type){
             this.type = type;
         }
         @Override
         public void onAnimationCancel(Animator animation) {
             super.onAnimationCancel(animation);
             if(type == 1){
                if(isCallBackSuccess){
                    startHookAnimator();
                    animation.removeAllListeners();
                }else {
                    startForkAnimator();
                    animation.removeAllListeners();
                }
             }
         }

         @Override
         public void onAnimationEnd(Animator animation) {
             super.onAnimationEnd(animation);
             if(type == 1){

             } else if (type == 2) {
                 animation.removeAllListeners();
             }else if (type == 3) {
                 animation.removeAllListeners();
                 if(finishListener != null){
                     InitAnimator();
                     finishListener.onFinish(true);
                 }
             }else if(type == 4){
                 animation.removeAllListeners();
             }else if(type == 5){
                 animation.removeAllListeners();
                 if(finishListener != null){
                     InitAnimator();
                     finishListener.onFinish(false);
                 }
             }
         }
     }
     //将动画全部至于初始状态
     private void InitAnimator(){
         isCallBack = false;//服务器是否返回
         isCallBackSuccess = false;//网络操作是否成功
         hookLine1X = 0;
         hookLine1Y = 0;
         hookLine2X = 0;
         hookLine2Y = 0;
         drawHookLine1 = false;//绘制对号的第一段
         drawHookLine2 = false;//是否绘制对号的第二段
         drawForkLine1 = false;//是否绘制叉的第一段
         drawForkLine2 = false;//是否绘制叉的第二段
         forkLine1X = 0;
         forkLine1Y = 0;
         forkLine2X = 0;
         forkLine2Y = 0;
         invalidate();
     }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if(visibility != VISIBLE){
            this.clearAnimation();
            progressAnimation.removeAllListeners();
            progressAnimation.cancel();
            InitAnimator();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.clearAnimation();
        progressAnimation.removeAllListeners();
        progressAnimation.cancel();
        InitAnimator();
    }
    public interface AnimFinishListener{
        void onFinish(boolean status);
    }
}
