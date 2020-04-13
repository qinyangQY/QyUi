package com.ccys.qyuilib.util;

import android.app.Activity;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ccys.qyuilib.image.GlideEngine;
import com.ccys.qyuilib.permission.QyPermissionCallBackLisener;
import com.ccys.qyuilib.permission.QyPermissionUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.listener.OnResultCallbackListener;

public class ImageSelectUtil {
    public static void selectMultipleImage(final Activity activity, FragmentManager fm, final int maxCount, final int minCount, final boolean isShowCamera, final OnResultCallbackListener onResultCallbackListener){
               if(QyPermissionUtil.checkPermission(activity,QyPermissionUtil.camera_permissions)){
                   PictureSelector.create(activity).
                           openGallery(PictureMimeType.ofImage()).
                           maxSelectNum(maxCount).
                           minSelectNum(minCount).
                           compress(true).
                           loadImageEngine(GlideEngine.createGlideEngine()).
                           imageSpanCount(4).
                           isNotPreviewDownload(true).
                           isCamera(isShowCamera).
                           enableCrop(false).
                           forResult(onResultCallbackListener);
               }else {
                   QyPermissionUtil.requestPermission(activity,fm,QyPermissionUtil.camera_permissions,new QyPermissionCallBackLisener(){

                       @Override
                       public void denied() {
                            ToastUtils.showToast("没有获取到相机权限", Toast.LENGTH_LONG);
                       }

                       @Override
                       public void granted() {
                           PictureSelector.create(activity).
                                   openGallery(PictureMimeType.ofImage()).
                                   maxSelectNum(maxCount).
                                   minSelectNum(minCount).
                                   compress(true).
                                   loadImageEngine(GlideEngine.createGlideEngine()).
                                   imageSpanCount(4).
                                   isNotPreviewDownload(true).
                                   isCamera(isShowCamera).
                                   enableCrop(false).
                                   forResult(onResultCallbackListener);
                       }
                   });
               }
    }
    public static void selectMultipleImage(final Fragment fragment, FragmentManager fm, final int maxCount, final int minCount, final boolean isShowCamera, final OnResultCallbackListener onResultCallbackListener){

        if(QyPermissionUtil.checkPermission(fragment.getActivity(),QyPermissionUtil.camera_permissions)){
            PictureSelector.create(fragment).
                    openGallery(PictureMimeType.ofImage()).
                    maxSelectNum(maxCount).
                    minSelectNum(minCount).
                    compress(true).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    imageSpanCount(4).
                    isNotPreviewDownload(true).
                    isCamera(isShowCamera).
                    enableCrop(false).
                    forResult(onResultCallbackListener);
        }else {
            QyPermissionUtil.requestPermission(fragment.getActivity(),fm,QyPermissionUtil.camera_permissions,new QyPermissionCallBackLisener(){

                @Override
                public void denied() {
                    ToastUtils.showToast("没有获取到相机权限", Toast.LENGTH_LONG);
                }

                @Override
                public void granted() {
                    PictureSelector.create(fragment).
                            openGallery(PictureMimeType.ofImage()).
                            maxSelectNum(maxCount).
                            minSelectNum(minCount).
                            compress(true).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            imageSpanCount(4).
                            isNotPreviewDownload(true).
                            isCamera(isShowCamera).
                            enableCrop(false).
                            forResult(onResultCallbackListener);
                }
            });
        }
    }
    public static void selectSingleImage(final Activity activity, FragmentManager fm, final boolean isShowCamera, final boolean isCrop, final OnResultCallbackListener onResultCallbackListener){

        if(QyPermissionUtil.checkPermission(activity,QyPermissionUtil.camera_permissions)){
            PictureSelector.create(activity).
                    openGallery(PictureMimeType.ofImage()).
                    imageSpanCount(4).
                    isNotPreviewDownload(true).
                    isCamera(isShowCamera).
                    compress(true).
                    selectionMode(PictureConfig.SINGLE).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    enableCrop(isCrop).
                    isDragFrame(true).
                    rotateEnabled(true). // 裁剪是否可旋转图片 true or false
                    scaleEnabled(true).
                    withAspectRatio(1,1).// 裁剪是否可放大缩小图片
                    forResult(onResultCallbackListener);
        }else {
            QyPermissionUtil.requestPermission(activity,fm,QyPermissionUtil.camera_permissions,new QyPermissionCallBackLisener(){

                @Override
                public void denied() {
                    ToastUtils.showToast("没有获取到相机权限", Toast.LENGTH_LONG);
                }

                @Override
                public void granted() {
                    PictureSelector.create(activity).
                            openGallery(PictureMimeType.ofImage()).
                            imageSpanCount(4).
                            isNotPreviewDownload(true).
                            isCamera(isShowCamera).
                            compress(true).
                            selectionMode(PictureConfig.SINGLE).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            enableCrop(isCrop).
                            isDragFrame(true).
                            rotateEnabled(true). // 裁剪是否可旋转图片 true or false
                            scaleEnabled(true).
                            withAspectRatio(1,1).// 裁剪是否可放大缩小图片
                            forResult(onResultCallbackListener);
                }
            });
        }
    }
    public static void selectSingleImage(final Fragment fragment, FragmentManager fm, final boolean isShowCamera, final boolean isCrop, final OnResultCallbackListener onResultCallbackListener){
        if(QyPermissionUtil.checkPermission(fragment.getActivity(),QyPermissionUtil.camera_permissions)){
            PictureSelector.create(fragment).
                    openGallery(PictureMimeType.ofImage()).
                    imageSpanCount(4).
                    isNotPreviewDownload(true).
                    isCamera(isShowCamera).
                    compress(true).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    selectionMode(PictureConfig.SINGLE).
                    enableCrop(isCrop).
                    isDragFrame(true).
                    rotateEnabled(true). // 裁剪是否可旋转图片 true or false
                    scaleEnabled(true).
                    withAspectRatio(1,1).// 裁剪是否可放大缩小图片
                    forResult(onResultCallbackListener);
        }else {
            QyPermissionUtil.requestPermission(fragment.getActivity(),fm,QyPermissionUtil.camera_permissions,new QyPermissionCallBackLisener(){

                @Override
                public void denied() {
                    ToastUtils.showToast("没有获取到相机权限", Toast.LENGTH_LONG);
                }

                @Override
                public void granted() {
                    PictureSelector.create(fragment).
                            openGallery(PictureMimeType.ofImage()).
                            imageSpanCount(4).
                            isNotPreviewDownload(true).
                            isCamera(isShowCamera).
                            compress(true).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            selectionMode(PictureConfig.SINGLE).
                            enableCrop(isCrop).
                            isDragFrame(true).
                            rotateEnabled(true). // 裁剪是否可旋转图片 true or false
                            scaleEnabled(true).
                            withAspectRatio(1,1).// 裁剪是否可放大缩小图片
                            forResult(onResultCallbackListener);
                }
            });
        }
    }
    public static void openCamera(final Activity activity, FragmentManager fm, final boolean isCrop, final OnResultCallbackListener onResultCallbackListener){
        if(QyPermissionUtil.checkPermission(activity,QyPermissionUtil.camera_permissions)){
            PictureSelector.create(activity)
                    .openCamera(PictureMimeType.ofImage()).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    enableCrop(isCrop).
                    compress(true).
                    isDragFrame(true).
                    rotateEnabled(true). // 裁剪是否可旋转图片 true or false
                    scaleEnabled(true).
                    withAspectRatio(1,1).// 裁剪是否可放大缩小图片
                    forResult(onResultCallbackListener);
        }else {
            QyPermissionUtil.requestPermission(activity,fm,QyPermissionUtil.camera_permissions,new QyPermissionCallBackLisener(){

                @Override
                public void denied() {
                    ToastUtils.showToast("没有获取到相机权限", Toast.LENGTH_LONG);
                }

                @Override
                public void granted() {
                    PictureSelector.create(activity)
                            .openCamera(PictureMimeType.ofImage()).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            enableCrop(isCrop).
                            compress(true).
                            isDragFrame(true).
                            rotateEnabled(true). // 裁剪是否可旋转图片 true or false
                            scaleEnabled(true).
                            withAspectRatio(1,1).// 裁剪是否可放大缩小图片
                            forResult(onResultCallbackListener);
                }
            });
        }
    }
    public static void openCamera(final Fragment fragment, FragmentManager fm, final boolean isCrop, final OnResultCallbackListener onResultCallbackListener){
        if(QyPermissionUtil.checkPermission(fragment.getActivity(),QyPermissionUtil.camera_permissions)){
            PictureSelector.create(fragment)
                    .openCamera(PictureMimeType.ofImage()).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    enableCrop(isCrop).
                    compress(true).
                    isDragFrame(true).
                    rotateEnabled(true). // 裁剪是否可旋转图片 true or false
                    scaleEnabled(true).
                    withAspectRatio(1,1).// 裁剪是否可放大缩小图片
                    forResult(onResultCallbackListener);
        }else {
            QyPermissionUtil.requestPermission(fragment.getActivity(),fm,QyPermissionUtil.camera_permissions,new QyPermissionCallBackLisener(){

                @Override
                public void denied() {
                    ToastUtils.showToast("没有获取到相机权限", Toast.LENGTH_LONG);
                }

                @Override
                public void granted() {
                    PictureSelector.create(fragment)
                            .openCamera(PictureMimeType.ofImage()).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            enableCrop(isCrop).
                            compress(true).
                            isDragFrame(true).
                            rotateEnabled(true). // 裁剪是否可旋转图片 true or false
                            scaleEnabled(true).
                            withAspectRatio(1,1).// 裁剪是否可放大缩小图片
                            forResult(onResultCallbackListener);
                }
            });
        }
    }
}
