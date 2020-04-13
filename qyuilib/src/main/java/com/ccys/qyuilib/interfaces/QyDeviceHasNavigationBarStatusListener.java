package com.ccys.qyuilib.interfaces;

/**
 * 包名：com.qinyang.qyuilib.interfaces
 * 创建人：秦洋
 * 创建时间：2019/2/23
 * 检查手机是否存在底部导航栏回掉接口
 */
public interface QyDeviceHasNavigationBarStatusListener {
    //true是存在并且显示 false 是不存在获取不显示
    void navigationBarShowStatus(boolean isShow, int navigationBarHeight);
}
