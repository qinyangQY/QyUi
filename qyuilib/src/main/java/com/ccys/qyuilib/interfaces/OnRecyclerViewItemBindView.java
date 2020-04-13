package com.ccys.qyuilib.interfaces;

import com.ccys.qyuilib.adapter.QyRecyclerViewHolder;

public interface OnRecyclerViewItemBindView<T> {
   void setItemBindViewHolder(QyRecyclerViewHolder viewHolder,T bean,int position);
}
