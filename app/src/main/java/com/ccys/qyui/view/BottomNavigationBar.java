package com.ccys.qyui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.ccys.qyui.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: QyUi
 * @Package: com.ccys.qyui.view
 * @ClassName: BottomNavigationBar
 * @描述: 底部导航栏
 * @作者: 秦洋
 * @日期: 2019-12-04 17:55
 */
public class BottomNavigationBar extends LinearLayout {
    private Context mContext;
    private OnMenuClickCallbackLisener onMenuClickCallbackLisener;
    private int lastCheckMenuPosition = -1;//上次选中的菜单
    private List<MenuBean> menuList = new ArrayList<>();
    public BottomNavigationBar(Context context) {
        super(context);
        initView(context,null,0);
    }

    public BottomNavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs,0);
    }

    public BottomNavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs,defStyleAttr);
    }
    private void initView(Context mContext, @Nullable AttributeSet attrs, int defStyleAttr){
        this.mContext = mContext;
    }
    public void setOnMenuClickCallbackLisener(OnMenuClickCallbackLisener onMenuClickCallbackLisener){
        this.onMenuClickCallbackLisener = onMenuClickCallbackLisener;
    }
    /**
     * @method  设置菜单
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public void setMenu(List<MenuBean> menus){
        if(menus == null || menus.size() <= 0){
            return;
        }
        menuList.clear();
        menuList.addAll(menus);
        this.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i = 0;i < menuList.size();i++){
            final int position = i;
            if(menuList.get(i).isLink){
                View childView = inflater.inflate(R.layout.bottom_navigation_item_layout,this,false);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
                lp.weight = 1;
                lp.gravity = Gravity.CENTER_HORIZONTAL;
                childView.setLayoutParams(lp);
                childView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onMenuClickCallbackLisener != null && lastCheckMenuPosition != position){
                            onMenuClickCallbackLisener.menuClick(menuList.get(position).getMenuName(),position);
                        }
                        updateMenuStatus(position);
                    }
                });
                ImageView menuInActive = childView.findViewById(R.id.menuInActive);
                ImageView menuActive = childView.findViewById(R.id.menuActive);
                menuInActive.setImageResource(menuList.get(position).getMenuInActiveSrc());
                menuActive.setImageResource(menuList.get(position).getMenuActiveSrc());
                CheckedTextView ct_menu_text = childView.findViewById(R.id.ct_menu_text);
                ct_menu_text.setText(menuList.get(position).getMenuName());
                this.addView(childView);
            }else {
                View childView = inflater.inflate(R.layout.bottom_navigatoin_add_item_layout,this,false);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
                lp.weight = 1;
                lp.gravity = Gravity.CENTER_HORIZONTAL;
                childView.setLayoutParams(lp);
                childView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onMenuClickCallbackLisener != null){
                            onMenuClickCallbackLisener.menuClick(menuList.get(position).getMenuName(),position);
                        }
                    }
                });
                ImageView itemContent = childView.findViewById(R.id.itemContent);
                itemContent.setImageResource(menuList.get(i).getMenuIconResId());
                this.addView(childView);
            }

        }
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(BottomNavigationBar.this.getMeasuredHeight() > 0){
                    updateMenuStatus(0);
                    BottomNavigationBar.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        if(onMenuClickCallbackLisener != null){
            onMenuClickCallbackLisener.menuClick(menuList.get(0).getMenuName(),0);
        }
    }
    /**
      *
      * @ProjectName:    ${PROJECT_NAME}
      * @Package:        ${PACKAGE_NAME}
      * @ClassName:      ${NAME}
      * @描述:       菜单点击事件的回掉
      * @作者:         秦洋
      * @日期:     ${DATE} ${TIME}
     */
    public interface OnMenuClickCallbackLisener{
        void menuClick(String meuName,int position);
    }
    /**
     * @method  点击后
     * @description 描述一下方法的作用
     * @date:  
     * @作者: 秦洋
     * @参数 
     * @return 
     */
    private void updateMenuStatus(int position){
        if(position == lastCheckMenuPosition || !menuList.get(position).isLink){
            return;
        }
        int count = this.getChildCount();
        if(position < count){
            View childView = getChildAt(position);
            CheckedTextView ct_menu_text = childView.findViewById(R.id.ct_menu_text);
            ct_menu_text.setChecked(true);
            int height = childView.findViewById(R.id.reMenuIcon).getMeasuredHeight();
            menuAnimotion(true,childView.findViewById(R.id.llMenuIconContent),-height);
        }
        if(lastCheckMenuPosition != -1){
            View childView = getChildAt(lastCheckMenuPosition);
            CheckedTextView ct_menu_text = childView.findViewById(R.id.ct_menu_text);
            ct_menu_text.setChecked(false);
            menuAnimotion(false,childView.findViewById(R.id.llMenuIconContent),0);
        }
        lastCheckMenuPosition = position;
    }
    private void menuAnimotion(boolean isCheck,View targetView,int height){
        if(isCheck){//选中
            ObjectAnimator animator = ObjectAnimator.ofFloat(targetView,"translationY",height);
            animator.setDuration(500);
            animator.start();
        }else {//没有选中
            ObjectAnimator animator = ObjectAnimator.ofFloat(targetView,"translationY",0f);
            animator.setDuration(500);
            animator.start();
        }
    }
    public static class MenuBean{
        private String menuName;
        private int menuActiveSrc;
        private int menuInActiveSrc;
        private boolean isLink;
        private int menuIconResId;

        public int getMenuIconResId() {
            return menuIconResId;
        }

        public void setMenuIconResId(int menuIconResId) {
            this.menuIconResId = menuIconResId;
        }

        public boolean isLink() {
            return isLink;
        }

        public void setLink(boolean link) {
            isLink = link;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public int getMenuActiveSrc() {
            return menuActiveSrc;
        }

        public void setMenuActiveSrc(int menuActiveSrc) {
            this.menuActiveSrc = menuActiveSrc;
        }

        public int getMenuInActiveSrc() {
            return menuInActiveSrc;
        }

        public void setMenuInActiveSrc(int menuInActiveSrc) {
            this.menuInActiveSrc = menuInActiveSrc;
        }
    }
}
