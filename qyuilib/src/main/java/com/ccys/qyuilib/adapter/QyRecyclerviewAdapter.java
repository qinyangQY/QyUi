package com.ccys.qyuilib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccys.qyuilib.interfaces.OnItemViewTypeLayout;
import com.ccys.qyuilib.interfaces.OnRecyclerViewItemBindView;
import com.ccys.qyuilib.interfaces.OnRecyclerViewMultiItemBindView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QyRecyclerviewAdapter<T> extends RecyclerView.Adapter<QyRecyclerViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<T> mData;
    private boolean isMultiLayout;//是否是多类型布局
    private HashMap<Integer,Integer> multiLayoutMap;//储存多布局和类型
    private int singleLayoutId;//item布局的id
    private OnItemViewTypeLayout<T> onItemViewTypeLayout;
    private OnRecyclerViewItemBindView<T> onRecyclerViewItemBindView;
    private OnRecyclerViewMultiItemBindView<T> onRecyclerViewMultiItemBindView;
    public QyRecyclerviewAdapter(Context context,int singleLayoutId){
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
        this.singleLayoutId = singleLayoutId;
        this.mData = new ArrayList<>();
        this.isMultiLayout = false;
    }
    public QyRecyclerviewAdapter(Context context,OnItemViewTypeLayout<T> onItemViewTypeLayout){
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
        this.onItemViewTypeLayout = onItemViewTypeLayout;
        this.mData = new ArrayList<>();
        this.isMultiLayout = true;
        this.multiLayoutMap = new HashMap<Integer, Integer>();
    }
    public void setData(List<T> list){
        if(list == null)return;
        this.mData.clear();
        this.mData.addAll(list);
        notifyDataSetChanged();
    }
    public void cleanData(){
        if(this.mData.size() > 0){
            this.mData.clear();
            notifyDataSetChanged();
        }
    }
    public void addData(List<T> list){
        if(list == null)return;
        this.mData.addAll(list);
        notifyDataSetChanged();
    }
    public void removeItem(int position){
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0,getItemCount());
    }

    public void addData(T t){
        this.mData.add(t);
        notifyDataSetChanged();
    }
    public void addData(int position,T t){
        this.mData.add(position,t);
        this.notifyItemInserted(position);
        notifyItemRangeChanged(0,getItemCount());
    }
    public void removeItem(T t){
        mData.remove(t);
        notifyDataSetChanged();
    }
    public List<T> getData(){
        return mData;
    }
   public void setOnRecyclerViewItemBindView(OnRecyclerViewItemBindView<T> onRecyclerViewItemBindView){
        this.onRecyclerViewItemBindView = onRecyclerViewItemBindView;
   }
   public void setOnRecyclerViewMultiItemBindView(OnRecyclerViewMultiItemBindView<T> onRecyclerViewMultiItemBindView){
        this.onRecyclerViewMultiItemBindView = onRecyclerViewMultiItemBindView;
   }
    @NonNull
    @Override
    public QyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(isMultiLayout){
            if(multiLayoutMap != null && multiLayoutMap.size() > 0){
                return new QyRecyclerViewHolder<T>(mContext,inflater.inflate(multiLayoutMap.get(viewType),parent,false));
            }else {
                return null;
            }
        }else {
            if(singleLayoutId != 0){
                return new QyRecyclerViewHolder<T>(mContext,inflater.inflate(singleLayoutId,parent,false));
            }else {
                return null;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull QyRecyclerViewHolder holder, int position) {
           if(isMultiLayout){
                if(onRecyclerViewMultiItemBindView != null){
                    onRecyclerViewMultiItemBindView.setMultiItemBindViewHolder(holder,mData.get(holder.getLayoutPosition()),getItemViewType(holder.getLayoutPosition()),holder.getLayoutPosition());
                }
           }else {
               if(onRecyclerViewItemBindView != null){
                   onRecyclerViewItemBindView.setItemBindViewHolder(holder,mData.get(holder.getLayoutPosition()),holder.getLayoutPosition());
               }
           }
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiLayout){
            if(onItemViewTypeLayout != null){
                int type = onItemViewTypeLayout.setItemViewType(mData.get(position),position)[0];
                int layout = onItemViewTypeLayout.setItemViewType(mData.get(position),position)[1];
                multiLayoutMap.put(type,layout);
                return type;
            }else {
                return super.getItemViewType(position);
            }
        }else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
