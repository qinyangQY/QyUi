package com.ccys.qyuilib.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ccys.qyuilib.image.ImageLoad;
import com.ccys.qyuilib.util.ViewSizeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.qinyang.qyuilib.adapter
 * 创建人：秦洋
 * 创建时间：2019/3/11
 */
public abstract class QyPageViewAdapter<T> extends PagerAdapter {
    private List<T> data;
    private List<View> viewList;
    private Context context;
    private int layoutId;
    private boolean isChangedView;//触发数据和view一起更新
    public QyPageViewAdapter(Context context, int layoutId){
        this.data = new ArrayList<>();
        this.viewList = new ArrayList<>();
        this.context = context;
        this.layoutId = layoutId;
    }
    public void setData(List<T> data){
        if(data == null)return;
        this.viewList.clear();
        this.data.clear();
        this.data.addAll(data);
        for(int i = 0;i < this.data.size(); i++){
           View itemView = LayoutInflater.from(context).inflate(layoutId,null,false);
           this.viewList.add(itemView);
        }
        this.notifyDataSetChanged();
    }
    public void setChangedView(boolean isChangedView){
        this.isChangedView = isChangedView;
    }
    public List<View> getViewList(){
        return this.viewList;
    }
    public void addData(T data){
        if(data == null)return;
        this.data.add(data);
        this.viewList.add(LayoutInflater.from(context).inflate(layoutId,null,false));
        this.notifyDataSetChanged();
    }
    public List<T> getData(){
        return this.data;
    }
    public void addData(List<T> data){
        if(data == null || data.size() <= 0)return;
        this.data.addAll(data);
        for(int i = 0;i < this.data.size(); i++){
            View itemView = LayoutInflater.from(context).inflate(layoutId,null,false);
            this.viewList.add(itemView);
        }
        this.notifyDataSetChanged();
    }
    public View getItemView(int position){
        if(viewList.size() > 0){
            if(position >= 0 && position < this.viewList.size()){
                return viewList.get(position);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if(isChangedView){
            return POSITION_NONE;
        }else {
            return super.getItemPosition(object);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(position >= getCount())position = 0;
        View childView = viewList.get(position);
        ViewGroup parent = (ViewGroup) childView.getParent();
        if(parent != null){
            parent.removeView(childView);
        }
        bindView(new ViewHolder(childView),this.data.get(position),position);
        container.addView(childView);
        return childView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(position >= getCount())return;
         container.removeView(viewList.get(position));
    }
    protected abstract void bindView(ViewHolder viewHolder,T t,int position);
    public class ViewHolder{
        private View itemView;
        public ViewHolder(View itemView){
            this.itemView = itemView;
        }
        public <V extends View> V getItemView(){
            return (V) itemView;
        }
        public void setImageView(int viewId, String url, int placeholder, int error) {
            View view = itemView.findViewById(viewId);
            if (view != null && view instanceof ImageView) {
                ImageLoad.showImage(context, (ImageView) view,placeholder, error,url);
            }
        }

        public void setImageView(int viewId, String url, int placeholder) {
            View view = itemView.findViewById(viewId);
            if (view != null && view instanceof ImageView) {
                ImageLoad.showImage(context, (ImageView) view,placeholder, placeholder,url);
            }
        }
        public void setImageView(int viewId, String url) {
            View view = itemView.findViewById(viewId);
            if (view != null && view instanceof ImageView) {
                ImageLoad.showImage(context, (ImageView) view,url);
            }
        }
        public void setImageView(int viewId, int drawableId){
            View view = itemView.findViewById(viewId);
            if (view != null && view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableId);
            }
        }
        public void setVisible(int viewId,int Visible){
            View view = itemView.findViewById(viewId);
            if(view != null){
                view.setVisibility(Visible);
            }
        }
        public void setText(int viewId, String content) {
            View view = itemView.findViewById(viewId);
            if (view != null && view instanceof TextView) {
                ((TextView) view).setText(TextUtils.isEmpty(content) ? "" : content);
            }
        }
        public <V extends View> V getView(int viewId){
            return itemView.findViewById(viewId);
        }
        public void setText(int viewId, String content, String defaultContent) {
            View view = itemView.findViewById(viewId);
            if (view != null && view instanceof TextView) {
                ((TextView) view).setText(TextUtils.isEmpty(content) ? defaultContent : content);
            }
        }
        /**
         * 动态设置view的高度屏幕适配
         * @param viewId
         * @param widthDiffer
         * @param widtnDivide
         * @param expectWidth
         * @param expectHeight
         */
        public void setViewHeight(int viewId,int widthDiffer, int widtnDivide, int expectWidth, int expectHeight){
            View view = itemView.findViewById(viewId);
            ViewSizeUtil.setViewHeight(context,view,widthDiffer,widtnDivide,expectWidth,expectHeight);
        }
        /**
         * 动态设置view的高度屏幕适配
         * @param viewId
         * @param widthDiffer
         * @param widtnDivide
         * @param expectWidth
         * @param expectHeight
         */
        public void setViewSize(int viewId,int widthDiffer, int widtnDivide, int expectWidth, int expectHeight){
            View view = itemView.findViewById(viewId);
            ViewSizeUtil.setViewSize(context,view,widthDiffer,widtnDivide,expectWidth,expectHeight);
        }
        public void setItemOnCliclLisener(View.OnClickListener onClickLisener) {
            this.itemView.setOnClickListener(onClickLisener);
        }
        public void setItemOnLongCliclLisener(View.OnLongClickListener onClickLisener) {
            this.itemView.setOnLongClickListener(onClickLisener);
        }
        public void setItemOnLongCliclLisener(int viewId,View.OnLongClickListener onClickLisener){
            this.itemView.findViewById(viewId).setOnLongClickListener(onClickLisener);
        }
        public void setItemOnClickLisener(int viewId, View.OnClickListener onClickListener){
            this.itemView.findViewById(viewId).setOnClickListener(onClickListener);
        }
    }
}
