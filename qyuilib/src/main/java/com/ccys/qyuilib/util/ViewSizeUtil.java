package com.ccys.qyuilib.util;

import android.content.Context;
import android.view.View;

public class ViewSizeUtil {
    /**
     * 根据屏幕的宽度设置view的高度
     * @param context
     * @param view  目标view
     * @param widthDiffer 宽度的差值（屏幕宽度和期望宽度之间的差值比如内外间距）
     * @param widtnDivide 宽度被均分数目
     * @param expectWidth 期望的宽度
     * @param expectHeight 期望的高度
     */
    public static void setViewHeight(Context context, View view, int widthDiffer, int widtnDivide, int expectWidth, int expectHeight){
        float ratio = expectHeight*1.0f/expectWidth;
        int screentWidth = QyScreenUtil.getWindowSize(context)[0];
        int width = (screentWidth - widthDiffer)/widtnDivide;
        int height = (int) (ratio * width);
        view.getLayoutParams().height = height;
    }
    /**
     * 根据屏幕的宽度设置view的宽度和高度
     * @param context
     * @param view  目标view
     * @param widthDiffer 宽度的差值（屏幕宽度和期望宽度之间的差值比如内外间距）
     * @param widtnDivide 宽度被均分数目
     * @param expectWidth 期望的宽度
     * @param expectHeight 期望的高度
     */
    public static void setViewSize(Context context, View view, int widthDiffer, int widtnDivide, int expectWidth, int expectHeight){
        float ratio = expectHeight*1.0f/expectWidth;
        int screentWidth = QyScreenUtil.getWindowSize(context)[0];
        int width = (screentWidth - widthDiffer)/widtnDivide;
        int height = (int) (ratio * width);
        view.getLayoutParams().width = width;
        view.getLayoutParams().height = height;
    }
}
