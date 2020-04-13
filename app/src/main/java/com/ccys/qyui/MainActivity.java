package com.ccys.qyui;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ccys.qyui.brodcast.TestBrodcast;
import com.ccys.qyui.fragment.IndexFragment;
import com.ccys.qyui.fragment.StoryFragment;
import com.ccys.qyui.view.BottomNavigationBar;
import com.ccys.qyuilib.base.QyBaseActivity;
import com.ccys.qyuilib.slideback.util.SlideBackUtil;
import com.ccys.qyuilib.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends QyBaseActivity {
    BottomNavigationBar mainMenu;
     private IndexFragment mIndexFragment;
     private StoryFragment mStoryFragment;
     private FragmentManager fm;
    private TestBrodcast brodcast;
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        SlideBackUtil.isNeedSlideBack = false;
        mainMenu = findViewById(R.id.mainMenu);
        fm = getSupportFragmentManager();
        showPositionFragment(0);
        brodcast = new TestBrodcast();
        registerReceiver(brodcast,new IntentFilter(AppUtil.getAppPackageName()+"test"));
    }

    @Override
    protected void addListener(@Nullable Bundle savedInstanceState) {
        super.addListener(savedInstanceState);
        mainMenu.setOnMenuClickCallbackLisener(new BottomNavigationBar.OnMenuClickCallbackLisener() {
            @Override
            public void menuClick(String meuName, int position) {
                 switch (meuName){
                     case "发现":
                         showPositionFragment(0);
                         break;
                     case "故事":
                         showPositionFragment(1);
                         break;
                     case "添加":
                         showPositionFragment(0);
                         break;
                     case "诠音":
                         showPositionFragment(1);
                         break;
                     case "我的":
                         showPositionFragment(0);
                         break;
                 }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppUtil.BackHomeApp(this);
           return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        List<BottomNavigationBar.MenuBean> menus = new ArrayList<>();
        for (int i = 0;i < 5;i++){
            BottomNavigationBar.MenuBean bean = new BottomNavigationBar.MenuBean();
            if(i == 0){
                bean.setMenuName("发现");
                bean.setMenuActiveSrc(R.drawable.main_finde_select_icon);
                bean.setMenuInActiveSrc(R.drawable.main_finde_unselect_icon);
                bean.setLink(true);
            }else if(i == 1){
                bean.setMenuName("故事");
                bean.setMenuActiveSrc(R.drawable.main_gushi_select_icon);
                bean.setMenuInActiveSrc(R.drawable.main_gushi_unselect_icon);
                bean.setLink(true);
            }else if(i == 2){
                bean.setMenuName("添加");
                bean.setMenuIconResId(R.drawable.main_menu_add_icon);
                bean.setLink(false);
            }else if(i == 3){
                bean.setMenuName("诠音");
                bean.setMenuActiveSrc(R.drawable.main_music_select_icon);
                bean.setMenuInActiveSrc(R.drawable.main_music_unselect_icon);
                bean.setLink(true);
            }else if(i == 4){
                bean.setMenuName("我的");
                bean.setMenuActiveSrc(R.drawable.main_my_select_icon);
                bean.setMenuInActiveSrc(R.drawable.main_my_unselect_icon);
                bean.setLink(true);
            }
            menus.add(bean);
        }
        mainMenu.setMenu(menus);
    }


    @Override
    public void onRetry() {

    }
    private void showPositionFragment(int position){
        FragmentTransaction ft = fm.beginTransaction();
        switch (position){
            case 0:
                hideAllFragment(ft);
                mIndexFragment = (IndexFragment) fm.findFragmentByTag("IndexFragment");
                if(mIndexFragment == null){
                    mIndexFragment = new IndexFragment();
                    ft.add(R.id.reContent,mIndexFragment,"IndexFragment");
                }else {
                    ft.show(mIndexFragment);
                }
                break;
            case 1:
                hideAllFragment(ft);
                mStoryFragment = (StoryFragment) fm.findFragmentByTag("StoryFragment");
                if(mStoryFragment == null){
                    mStoryFragment = new StoryFragment();
                    ft.add(R.id.reContent,mStoryFragment,"StoryFragment");
                }else {
                    ft.show(mStoryFragment);
                }
                break;
        }
        ft.commit();
    }
    private void hideAllFragment(FragmentTransaction ft){
         if(mIndexFragment != null){
             ft.hide(mIndexFragment);
         }
        if(mStoryFragment != null){
            ft.hide(mStoryFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(brodcast);
    }
 //    private HttpLoggingInterceptor getLogInterceptor(){
//        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new LoggingInterceptor());
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
//        return logInterceptor;
//    }
}
