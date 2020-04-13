package com.ccys.qyuilib.image;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ccys.qyuilib.R;

/**
 * @ProjectName: QyUi
 * @Package: com.ccys.qyuilib.image
 * @ClassName: ImageLoad
 * @描述: 加载图片的工具类
 * @作者: 秦洋
 * @日期: 2019-12-04 11:59
 */
public class ImageLoad {
    /**
     * @method  加载图片默认占位
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public static void showImage(Context context, ImageView imageView,String path){
        if(!((Activity)context).isDestroyed()){
            RequestOptions options = new RequestOptions();
            options.placeholder(R.color.colorPlaceholder);
            options.error(R.color.colorPlaceholder);
            Glide.with(context).load(path).apply(options).into(imageView);
        }
    }
    /**
     * @method  加载图片自定义占位色块
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public static void showImage(Context context, ImageView imageView,int colorResId,String path){
        if(!((Activity)context).isDestroyed()){
            RequestOptions options = new RequestOptions();
            options.placeholder(colorResId);
            options.error(colorResId);
            Glide.with(context).load(path).apply(options).into(imageView);
        }
    }
    /**
     * @method  加载图片自定义占位图片
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public static void showImage(Context context, ImageView imageView,int placeholder,int error,String path){
        if(!((Activity)context).isDestroyed()){
            RequestOptions options = new RequestOptions();
            options.placeholder(placeholder);
            options.error(error);
            Glide.with(context).load(path).apply(options).into(imageView);
        }
    }

}
