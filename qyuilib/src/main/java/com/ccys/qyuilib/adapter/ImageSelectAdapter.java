package com.ccys.qyuilib.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.activity.ShowImageActivity;
import com.ccys.qyuilib.interfaces.OnItemViewTypeLayout;
import com.ccys.qyuilib.interfaces.OnRecyclerViewItemBindView;
import com.ccys.qyuilib.interfaces.OnRecyclerViewMultiItemBindView;
import com.ccys.qyuilib.permission.QyPermissionCallBackLisener;
import com.ccys.qyuilib.permission.QyPermissionUtil;
import com.ccys.qyuilib.util.ImageSelectUtil;
import com.ccys.qyuilib.util.QyDisplayUtil;
import com.ccys.qyuilib.util.QyScreenUtil;
import com.ccys.qyuilib.util.ToastUtils;
import com.ccys.qyuilib.view.QyImageView;
import com.ccys.qyuilib.view.QyRecyclerView;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import java.util.ArrayList;
import java.util.List;

public class ImageSelectAdapter extends QyRecyclerviewAdapter<String> implements OnRecyclerViewMultiItemBindView<String>, OnResultCallbackListener {
    private int maxCount;//最大的选择数量
    private List<String> allImageList;//所有图片的集合
    private List<String> localImageList;//本地图片集合
    private float sizeRatio = 1.0f;//item的宽度和高度的比例
    private int deleteIconResId;//删除按钮的图标
    private int selectIconResId;//选择图片按钮的图片
    private int pading;//item之间的间距
    private int itemWidth;//item的宽度
    private int itemHeight;//item的高度
    private int rowCunt;//一行显示多少个item
    private Context context;
    private FragmentManager fm;
    private boolean isShowImage = true;//是否可以查看大图
    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_BTN = 2;
    private boolean isShowDelBtn = true;
    private boolean isCircular;
    private int radius;
    private UpdateImageListener updateImageListener;
    public ImageSelectAdapter(final Context context, FragmentManager fm, final QyRecyclerView recycler, final int rowCunt, int maxCount) {
        super(context,new MyOnItemViewTypeLayout());
        this.fm = fm;
        this.context = context;
        this.maxCount = maxCount;
        this.rowCunt = rowCunt;
        this.allImageList = new ArrayList<>();
        this.localImageList = new ArrayList<>();
        this.deleteIconResId = R.drawable.delete_icon;
        this.selectIconResId = R.drawable.select_icon;
        this.setOnRecyclerViewMultiItemBindView(this);
        radius = QyDisplayUtil.dp2px(context,6);
        this.pading = QyDisplayUtil.dp2px(context,7);
        recycler.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if(recycler.getMeasuredWidth() > 0){
                    itemWidth = recycler.getMeasuredWidth() / rowCunt;
                    itemHeight = (int) (itemWidth * sizeRatio);
                    notifyDataSetChanged();
                    recycler.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return false;
            }
        });
    }
    public ImageSelectAdapter(Context context, FragmentManager fm, final QyRecyclerView recycler, final int rowCunt, int maxCount, final float sizeRatio) {
        super(context,new MyOnItemViewTypeLayout());
        this.fm = fm;
        this.context = context;
        this.maxCount = maxCount;
        this.rowCunt = rowCunt;
        this.allImageList = new ArrayList<>();
        this.localImageList = new ArrayList<>();
        this.deleteIconResId = R.drawable.delete_icon;
        this.selectIconResId = R.drawable.select_icon;
        this.sizeRatio = sizeRatio;
        this.setOnRecyclerViewMultiItemBindView(this);
        this.pading = QyDisplayUtil.dp2px(context,7);
        radius = QyDisplayUtil.dp2px(context,6);
        recycler.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if(recycler.getMeasuredWidth() > 0){
                    itemWidth = recycler.getMeasuredWidth() / rowCunt;
                    itemHeight = (int) (itemWidth * sizeRatio);
                    notifyDataSetChanged();
                    recycler.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return false;
            }
        });
    }
    public void setUpdateImageListener(UpdateImageListener updateImageListener){
        this.updateImageListener = updateImageListener;
    }
   public void setBtnIcon(int deleteIconResId,int selectIconResId){
        this.deleteIconResId = deleteIconResId;
        this.selectIconResId = selectIconResId;
   }
   public void setMaxCount(int maxCount){
        this.maxCount = maxCount;
   }
    public void setShpe(boolean isCircular,int radius){
        this.isCircular = isCircular;
        this.radius = radius;
    }
    public void setShowImage(boolean isShowImage){
        this.isShowImage = isShowImage;
    }
    public void setShowDelBtn(boolean isShowDelBtn){
        this.isShowDelBtn = isShowDelBtn;
    }
    public void setSizeRatio(final QyRecyclerView recycler, final float sizeRatio, int pading){
        this.pading = QyDisplayUtil.dp2px(context,pading);
        this.sizeRatio = sizeRatio;
        recycler.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if(recycler.getMeasuredWidth() > 0){
                    itemWidth = recycler.getMeasuredWidth() / rowCunt;
                    itemHeight = (int) (itemWidth * sizeRatio);
                    notifyDataSetChanged();
                    recycler.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return false;
            }
        });
    }

    public void setRadius(int radius) {
        this.radius = QyDisplayUtil.dp2px(context,radius);
    }

    @Override
    public void onResult(List<LocalMedia> result) {
          if(result != null && result.size() > 0){
              for (LocalMedia lm:result){
                  allImageList.add(lm.getCompressPath());
                  localImageList.add(lm.getCompressPath());
              }
              getData().clear();
              getData().addAll(allImageList);
              if(allImageList.size() < maxCount){
                  getData().add("添加按钮");
              }
              notifyDataSetChanged();
              if(updateImageListener != null){
                  updateImageListener.updateImage(localImageList);
              }
          }
    }


    @Override
    public void setData(List<String> list) {
        List<String> tempList = new ArrayList<>();
        tempList.addAll(list);
        allImageList.clear();
        allImageList.addAll(list);
           for (String path:list){
               if(!path.contains("http://") && !path.contains("https://")){
                   localImageList.add(path);
               }
           }
           if(list.size() < maxCount){
               tempList.add("添加按钮");
           }
          super.setData(tempList);
    }

    @Override
    public int getItemCount() {
        if(getData().size() ==0){
            getData().add("添加按钮");
        }
        return super.getItemCount();
    }

    @Override
    public void setMultiItemBindViewHolder(QyRecyclerViewHolder holder, String bean, int layoutType, final int position) {
        RelativeLayout root_view = (RelativeLayout) holder.getView(R.id.root_view);
        root_view.getLayoutParams().width = itemWidth;
        root_view.getLayoutParams().height = itemHeight;
         root_view.setPadding(pading,pading,pading,pading);
        QyImageView item_content = (QyImageView) holder.getView(R.id.item_content);
        if(isShowDelBtn){
           RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) item_content.getLayoutParams();
           lp.topMargin = QyDisplayUtil.dp2px(context,10);
            lp.rightMargin = QyDisplayUtil.dp2px(context,10);
        }else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) item_content.getLayoutParams();
            lp.topMargin = 0;
            lp.rightMargin = 0;
        }
        item_content.setShape(isCircular,radius);
            switch (layoutType){
                case VIEW_TYPE_BTN://添加按钮
                     holder.setImage(R.id.item_content,selectIconResId);
                    holder.setOnClickListener(R.id.item_content, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int count = maxCount - allImageList.size();
                            ImageSelectUtil.selectMultipleImage((Activity) context,fm,count,1,true,ImageSelectAdapter.this);
                        }
                    });
                    break;
                case VIEW_TYPE_IMAGE://显示图片
                     holder.setImage(R.id.delete_btn,deleteIconResId);
                     holder.setImage(R.id.item_content,bean);
                     if(isShowDelBtn){
                         holder.setVisibility(R.id.delete_btn,View.VISIBLE);
                     }else {
                         holder.setVisibility(R.id.delete_btn,View.GONE);
                     }
                     holder.setOnClickListener(R.id.delete_btn, new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             String path = getData().get(position);
                             allImageList.remove(path);
                             if(localImageList.contains(path)){
                                 localImageList.remove(path);
                             }
                             getData().remove(position);
                             if(allImageList.size() < maxCount){
                                 if(!getData().contains("添加按钮")){
                                     getData().add(getData().size(),"添加按钮");
                                 }
                             }
                             notifyDataSetChanged();
                             if(updateImageListener != null){
                                 updateImageListener.updateImage(localImageList);
                             }
                         }
                     });
                     if(isShowImage){
                         holder.setOnClickListener(R.id.item_content, new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 ShowImageActivity.Show(context,allImageList,position);
                             }
                         });
                     }
                    break;
            }
    }

    public static class MyOnItemViewTypeLayout implements OnItemViewTypeLayout<String>{

        @Override
        public int[] setItemViewType(String s, int position) {
            int[] views = new int[2];
            if(s.equals("添加按钮")){
                views[0] = VIEW_TYPE_BTN;
                views[1] = R.layout.image_add_item_layout;
            }else {
                views[0] = VIEW_TYPE_IMAGE;
                views[1] = R.layout.image_select_item_layout;
            }
            return views;
        }
    }
    public List<String> getLocalImageList(){
        return localImageList;
    }
    public List<String> getAllImageList(){
        return allImageList;
    }
    public interface UpdateImageListener{
        void updateImage(List<String> imageList);
    }
}
