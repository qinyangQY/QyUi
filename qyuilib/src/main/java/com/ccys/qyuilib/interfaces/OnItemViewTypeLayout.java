package com.ccys.qyuilib.interfaces;

public interface OnItemViewTypeLayout<T> {
    //返回值是长度为2的一个数组，第一个为布局类型，第二个为布局id
    int[] setItemViewType(T t, int position);
}
