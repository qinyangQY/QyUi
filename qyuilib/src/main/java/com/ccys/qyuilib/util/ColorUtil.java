package com.ccys.qyuilib.util;

/**
 * description ： TODO:类的作用
 * author : 姓名 秦洋
 * email : 邮箱 563281908@qq.com
 * date : 2019/6/1 14:49
 */
public class ColorUtil {

    /**修改颜色的透明度
     * @param alpha
     * @param baseColor
     * @return
     */
    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }
}
