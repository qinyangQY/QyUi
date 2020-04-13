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

public class VideoSelectUtil {
    public static void selectMultipleVideo(final Activity activity, FragmentManager fm, final int maxFileSize,final int recordVideoSecond,final int maxCount, final int minCount, final boolean isShowCamera, final OnResultCallbackListener onResultCallbackListener){

        if(QyPermissionUtil.checkPermission(activity,QyPermissionUtil.camera_permissions)){
            PictureSelector.create(activity).
                    openGallery(PictureMimeType.ofVideo()).
                    minVideoSelectNum(maxCount).
                    minVideoSelectNum(minCount).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    imageSpanCount(4).
                    isNotPreviewDownload(true).
                    isCamera(isShowCamera).
                    queryMaxFileSize(maxFileSize).
                    recordVideoSecond(recordVideoSecond).
                    compress(true).
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
                            openGallery(PictureMimeType.ofVideo()).
                            minVideoSelectNum(maxCount).
                            minVideoSelectNum(minCount).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            imageSpanCount(4).
                            isNotPreviewDownload(true).
                            isCamera(isShowCamera).
                            queryMaxFileSize(maxFileSize).
                            recordVideoSecond(recordVideoSecond).
                            compress(true).
                            forResult(onResultCallbackListener);
                }
            });
        }
    }
    public static void selectMultipleVideo(final Fragment fragment, FragmentManager fm, final int maxFileSize,final int recordVideoSecond,final int maxCount, final int minCount, final boolean isShowCamera, final OnResultCallbackListener onResultCallbackListener){
        if(QyPermissionUtil.checkPermission(fragment.getActivity(),QyPermissionUtil.camera_permissions)){
            PictureSelector.create(fragment).
                    openGallery(PictureMimeType.ofVideo()).
                    minVideoSelectNum(maxCount).
                    minVideoSelectNum(minCount).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    imageSpanCount(4).
                    isNotPreviewDownload(true).
                    isCamera(isShowCamera).
                    queryMaxFileSize(maxFileSize).
                    recordVideoSecond(recordVideoSecond).
                    compress(true).
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
                            openGallery(PictureMimeType.ofVideo()).
                            minVideoSelectNum(maxCount).
                            minVideoSelectNum(minCount).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            imageSpanCount(4).
                            isNotPreviewDownload(true).
                            queryMaxFileSize(maxFileSize).
                            recordVideoSecond(recordVideoSecond).
                            isCamera(isShowCamera).
                            compress(true).
                            forResult(onResultCallbackListener);
                }
            });
        }
    }
    public static void selectSingleVideo(final Activity activity, FragmentManager fm, final int maxFileSize,final int recordVideoSecond,final boolean isShowCamera, final OnResultCallbackListener onResultCallbackListener){

        if(QyPermissionUtil.checkPermission(activity,QyPermissionUtil.camera_permissions)){
            PictureSelector.create(activity).
                    openGallery(PictureMimeType.ofVideo()).
                    imageSpanCount(4).
                    isNotPreviewDownload(true).
                    isCamera(isShowCamera).
                    compress(true).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    queryMaxFileSize(maxFileSize).
                    recordVideoSecond(recordVideoSecond).
                    selectionMode(PictureConfig.SINGLE).
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
                            openGallery(PictureMimeType.ofVideo()).
                            imageSpanCount(4).
                            isNotPreviewDownload(true).
                            isCamera(isShowCamera).
                            queryMaxFileSize(maxFileSize).
                            recordVideoSecond(recordVideoSecond).
                            compress(true).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            selectionMode(PictureConfig.SINGLE).
                            forResult(onResultCallbackListener);
                }
            });
        }
    }
    public static void selectSingleVideo(final Fragment fragment, FragmentManager fm, final int maxFileSize,final int recordVideoSecond,final boolean isShowCamera, final OnResultCallbackListener onResultCallbackListener){
        if(QyPermissionUtil.checkPermission(fragment.getActivity(),QyPermissionUtil.camera_permissions)){
            PictureSelector.create(fragment).
                    openGallery(PictureMimeType.ofVideo()).
                    imageSpanCount(4).
                    isNotPreviewDownload(true).
                    isCamera(isShowCamera).
                    queryMaxFileSize(maxFileSize).
                    recordVideoSecond(recordVideoSecond).
                    compress(true).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    selectionMode(PictureConfig.SINGLE).
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
                            openGallery(PictureMimeType.ofVideo()).
                            imageSpanCount(4).
                            isNotPreviewDownload(true).
                            queryMaxFileSize(maxFileSize).
                            recordVideoSecond(recordVideoSecond).
                            isCamera(isShowCamera).
                            compress(true).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            selectionMode(PictureConfig.SINGLE).
                            forResult(onResultCallbackListener);
                }
            });
        }

    }
    public static void openCamera(final Activity activity, FragmentManager fm, final int recordVideoSecond,final OnResultCallbackListener onResultCallbackListener){
        if(QyPermissionUtil.checkPermission(activity,QyPermissionUtil.camera_permissions)){
            PictureSelector.create(activity)
                    .openCamera(PictureMimeType.ofVideo()).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    recordVideoSecond(recordVideoSecond).
                    compress(true).
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
                            .openCamera(PictureMimeType.ofVideo()).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            recordVideoSecond(recordVideoSecond).
                            compress(true).
                            forResult(onResultCallbackListener);
                }
            });
        }

    }
    public static void openCamera(final Fragment fragment, FragmentManager fm, final int recordVideoSecond,final OnResultCallbackListener onResultCallbackListener){
        if(QyPermissionUtil.checkPermission(fragment.getActivity(),QyPermissionUtil.camera_permissions)){
            PictureSelector.create(fragment)
                    .openCamera(PictureMimeType.ofVideo()).
                    loadImageEngine(GlideEngine.createGlideEngine()).
                    recordVideoSecond(recordVideoSecond).
                    compress(true).
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
                            .openCamera(PictureMimeType.ofVideo()).
                            loadImageEngine(GlideEngine.createGlideEngine()).
                            recordVideoSecond(recordVideoSecond).
                            compress(true).
                            forResult(onResultCallbackListener);
                }
            });
        }
    }
}
