package com.ccys.qyuilib.slideback.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 包名：com.qinyang.qyuilib.slideback.view
 * 创建人：秦洋
 * 创建时间：2019/3/10
 * 处理事件拦截的Layout
 */
public class SlideBackInterceptLayout extends FrameLayout {
    private float sideSlideLength = 0; // 边缘滑动响应距离

    public SlideBackInterceptLayout(Context context) {
        this(context, null);
    }

    public SlideBackInterceptLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideBackInterceptLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return ev.getAction() == MotionEvent.ACTION_DOWN && ev.getRawX() <= sideSlideLength;
    }

    public void setSideSlideLength(float sideSlideLength) {
        this.sideSlideLength = sideSlideLength;
    }
}
