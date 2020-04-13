package com.ccys.qyuilib.slideback.util;

import android.app.Activity;

import java.util.WeakHashMap;

/**
 * 包名：com.qinyang.qyuilib.slideback.util
 * 创建人：秦洋
 * 创建时间：2019/3/10
 * 侧滑返回的使用类
 */
public class SlideBackUtil {
    // 使用WeakHashMap防止内存泄漏
    private static WeakHashMap<Activity,SlideBackManager> map = new WeakHashMap<>();
    public static boolean isNeedSlideBack = true;

    /**
     * 注册
     *
     * @param activity 目标Act
     * @param callBack 回调
     */
    public static void register(Activity activity, SlideBackCallBack callBack) {
        if(!isNeedSlideBack)return;
        register(activity, false, callBack);
    }

    /**
     * 注册
     *
     * @param activity   目标Act
     * @param haveScroll 页面是否有滑动
     * @param callBack   回调
     */
    public static void register(Activity activity, boolean haveScroll, SlideBackCallBack callBack) {
        if(!isNeedSlideBack)return;
        map.put(activity, new SlideBackManager().register(activity, haveScroll, callBack));
    }

    /**
     * 注销
     *
     * @param activity 目标Act
     */
    public static void unregister(Activity activity) {
        if(!isNeedSlideBack)return;
        SlideBackManager slideBack = map.get(activity);
        if (null != slideBack) {
            slideBack.unregister();
        }
        map.remove(activity);
    }
}
