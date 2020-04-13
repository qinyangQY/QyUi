package com.ccys.qyuilib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.ccys.qyuilib.R;

public class QyRecyclerView extends RecyclerView {
    private int viewType;//recyclerview的类型（横向，竖向，网格，瀑布流）
    private final int VERTICAL = 1;
    private final int HORIZONTAL = 2;
    private final int GRID = 3;
    private final int WATERFALL = 4;
    private boolean isNest;//是否有滑动嵌套条嵌套
    private int rowCount;//如果是网格的话这个参数才会有用，表示列数
    private int waterfallType;//瀑布流的方向
    public QyRecyclerView(@NonNull Context context) {
        super(context);
        initView(context,null);
    }

    public QyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public QyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }
    private void initView(Context context,AttributeSet attrs){
        this.removeAllViews();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.QyRecyclerView);
        viewType = ta.getInt(R.styleable.QyRecyclerView_viewType,1);
        isNest = ta.getBoolean(R.styleable.QyRecyclerView_isNest,false);
        rowCount = ta.getInteger(R.styleable.QyRecyclerView_rowCount,3);
        waterfallType = ta.getInt(R.styleable.QyRecyclerView_waterfallType,1);
        ta.recycle();
        switch (viewType){
            case VERTICAL:
                LinearLayoutManager vrLm = new LinearLayoutManager(context);
                vrLm.setOrientation(LinearLayoutManager.VERTICAL);
                if(isNest){
                    vrLm.setSmoothScrollbarEnabled(true);
                    vrLm.setAutoMeasureEnabled(true);
                    this.setNestedScrollingEnabled(false);
                }
                this.setLayoutManager(vrLm);
                this.setHasFixedSize(true);
                break;
            case HORIZONTAL:
                LinearLayoutManager hrLm = new LinearLayoutManager(context);
                hrLm.setOrientation(LinearLayoutManager.HORIZONTAL);
                if(isNest){
                    hrLm.setSmoothScrollbarEnabled(true);
                    hrLm.setAutoMeasureEnabled(true);
                    this.setNestedScrollingEnabled(false);
                }
                this.setLayoutManager(hrLm);
                this.setHasFixedSize(true);
                break;
            case GRID:
                GridLayoutManager grLm = new GridLayoutManager(context,rowCount);
                if(isNest){
                    grLm.setSmoothScrollbarEnabled(true);
                    grLm.setAutoMeasureEnabled(true);
                    this.setNestedScrollingEnabled(false);
                }
                this.setLayoutManager(grLm);
                this.setHasFixedSize(true);
                break;
            case WATERFALL:
                StaggeredGridLayoutManager stLm = null;
                if(waterfallType == 1){
                    stLm = new StaggeredGridLayoutManager(rowCount,StaggeredGridLayoutManager.VERTICAL);
                }else {
                    stLm = new StaggeredGridLayoutManager(rowCount,StaggeredGridLayoutManager.HORIZONTAL);
                }
                if(isNest){
                    stLm.setAutoMeasureEnabled(true);
                    this.setNestedScrollingEnabled(false);
                }
                this.setLayoutManager(stLm);
                this.setHasFixedSize(true);
                break;
        }
    }
     public int getRowCount(){
        return rowCount;
     }
}
