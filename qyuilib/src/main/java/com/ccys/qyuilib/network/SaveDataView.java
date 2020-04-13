package com.ccys.qyuilib.network;

import android.widget.Toast;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.loadstatus.QyStatusLayoutManage;
import com.ccys.qyuilib.loadstatus.QySubmitCallBackListener;
import com.ccys.qyuilib.util.LogUtil;
import com.ccys.qyuilib.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class SaveDataView<T> implements Observer<T> {
    private QyNetworkManager manager;
    private QyStatusLayoutManage statusLayoutManage;
    public SaveDataView(QyNetworkManager manager, QyStatusLayoutManage statusLayoutManage){
        this.manager = manager;
        this.statusLayoutManage = statusLayoutManage;
    }

    @Override
    public void onSubscribe(Disposable d) {
        statusLayoutManage.showSubmit(new QySubmitCallBackListener() {
            @Override
            public void submitCallBack(boolean isSuccess) {
                submitStatus(isSuccess);
            }
        });
    }

    /**
     * 提交状态的回调
     * @param isSuccess
     */
    public abstract void submitStatus(boolean isSuccess);
    @Override
    public void onError(Throwable e) {
        /**
         * 将错误信息打印出来
         */
        LogUtil.sendContentLog("","",e.toString());
        if(e instanceof HttpException){
            HttpException he = (HttpException) e;
            if(he.code() == manager.otherDevicesLoginCode() ||
             he.code() == manager.accountFrozenCode() ||
             he.code() == manager.unLoginCode()){
                statusLayoutManage.closeSubmit(false);
            }else {
                ToastUtils.showToast(R.string.network_error_tip, Toast.LENGTH_LONG);
                statusLayoutManage.closeSubmit(false);
            }
        }else {
            ToastUtils.showToast(R.string.network_error_tip, Toast.LENGTH_LONG);
            statusLayoutManage.closeSubmit(false);
        }
    }
    @Override
    public void onComplete() {
       //正常执行完毕
    }
}
