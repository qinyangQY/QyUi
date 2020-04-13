package com.ccys.qyuilib.interfaces;

import com.ccys.qyuilib.adapter.QyRecyclerViewHolder;

public interface OnRecyclerViewMultiItemBindView<T> {
    void setMultiItemBindViewHolder(QyRecyclerViewHolder viewHolder,T bean,int layoutType,int position);
}
