package com.ccys.qyuilib.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.adapter.QyPageViewAdapter;
import com.ccys.qyuilib.base.QyBaseActivity;
import com.ccys.qyuilib.slideback.util.SlideBackUtil;

import java.io.Serializable;
import java.util.List;

public class ShowImageActivity extends QyBaseActivity implements ViewPager.OnPageChangeListener {
    private TextView tv_count;
    private ViewPager vp_content;
    private PageAdapter adapter;
    private List<String> imageList;
    private int currentPosition;
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_show_image;
    }
    public static void Show(Context context,List<String> imageList,int currentPosition){
        Bundle bundle = new Bundle();
        bundle.putSerializable("imageList", (Serializable) imageList);
        bundle.putInt("currentPosition",currentPosition);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(context,ShowImageActivity.class);
        context.startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
          setStateBarStyle("#000000",false);
        SlideBackUtil.isNeedSlideBack = false;
        statusLayoutManage.showContent();
        tv_count = findViewById(R.id.tv_count);
        vp_content = findViewById(R.id.vp_content);
        adapter = new PageAdapter();
        vp_content.setAdapter(adapter);
        imageList = getIntent().getStringArrayListExtra("imageList");
        currentPosition = getIntent().getIntExtra("currentPosition",0);
        tv_count.setText((currentPosition+1)+"/"+imageList.size());
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter.setData(imageList);
        vp_content.setCurrentItem(currentPosition);
    }

    @Override
    protected void addListener(@Nullable Bundle savedInstanceState) {
        super.addListener(savedInstanceState);
        vp_content.addOnPageChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vp_content.removeOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_count.setText((position+1)+"/"+imageList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onRetry() {

    }


    public class PageAdapter extends QyPageViewAdapter<String>{

        public PageAdapter() {
            super(ShowImageActivity.this, R.layout.show_imge_item_layout);
        }

        @Override
        protected void bindView(ViewHolder holder, String path, int position) {
            holder.setImageView(R.id.item_content,path);
            holder.setItemOnClickLisener(R.id.item_content, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
}
