package com.ccys.qyuilib.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ccys.qyuilib.image.ImageLoad;

/**
 * @ProjectName: QyUi
 * @Package: com.ccys.qyuilib.dialog
 * @ClassName: QyViewHolder
 * @描述: 对话框的view的缓存类
 * @作者: 秦洋
 * @日期: 2019-12-03 17:25
 */
public class QyViewHolder {
    private View contentView;
    private Context context;
    public QyViewHolder(Context context,View contentView){
        this.contentView = contentView;
        this.context = context;
    }
    public Context getContext(){
        return context;
    }
    /**
     * @method  获取view
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public View getView(int viewId){
        return contentView.findViewById(viewId);
    }
    /**
     * @method  给文本框负值
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public void setText(int viewId,String text){
        View view = getView(viewId);
        if(view != null){
            if(view instanceof TextView){
                ((TextView) view).setText(TextUtils.isEmpty(text)?"":text);
            }
        }
    }
    public void setText(int viewId,String text,String defaultText){
        View view = getView(viewId);
        if(view != null){
            if(view instanceof TextView){
                ((TextView) view).setText(TextUtils.isEmpty(text)?defaultText:text);
            }
        }
    }
    public void setImage(int viewId,String path){
        View view = getView(viewId);
        if(view != null){
            if(view instanceof ImageView){
                ImageLoad.showImage(context,(ImageView) view,path);
            }
        }
    }
    public void setImage(int viewId,String path,int colorResId){
        View view = getView(viewId);
        if(view != null){
            if(view instanceof ImageView){
                ImageLoad.showImage(context,(ImageView) view,colorResId,path);
            }
        }
    }
    public void setImage(int viewId,String path,int placeholder,int error){
        View view = getView(viewId);
        if(view != null){
            if(view instanceof ImageView){
                ImageLoad.showImage(context,(ImageView) view,placeholder,error,path);
            }
        }
    }
    public void setVisibility(int viewId,int visibility){
        View view = getView(viewId);
        if(view != null){
            view.setVisibility(visibility);
        }
    }
    /**
     * @method  给view设置点击事件
     * @description 描述一下方法的作用
     * @date:  
     * @作者: 秦洋
     * @参数 
     * @return 
     */
    public void setOnClickListener(View.OnClickListener clickListener,int ... viewIds){
         for (int viewId:viewIds){
             View view = getView(viewId);
             if(view != null){
                 view.setOnClickListener(clickListener);
             }
         }
    }
    public void setOnClickListener(int viewId,View.OnClickListener clickListener){
        View view = getView(viewId);
        if(view != null){
            view.setOnClickListener(clickListener);
        }
    }
    /**
     * @method  给view设置长按事件
     * @description 描述一下方法的作用
     * @date:  
     * @作者: 秦洋
     * @参数 
     * @return 
     */
    public void setOnLongClickListener(View.OnLongClickListener clickListener,int ... viewIds){
        for (int viewId:viewIds){
            View view = getView(viewId);
            if(view != null){
                view.setOnLongClickListener(clickListener);
            }
        }
    }
    public void setOnLongClickListener(int viewId,View.OnLongClickListener clickListener){
        View view = getView(viewId);
        if(view != null){
            view.setOnLongClickListener(clickListener);
        }
    }
}
