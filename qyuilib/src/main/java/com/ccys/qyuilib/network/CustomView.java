package com.ccys.qyuilib.network;

import com.ccys.qyuilib.util.LogUtil;
import io.reactivex.Observer;
import retrofit2.HttpException;

public abstract class CustomView<T> implements Observer<T> {
    private QyNetworkManager manager;
    public CustomView(QyNetworkManager manager){
        this.manager = manager;
    }

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
                authError(he.code(),e);
            }else {
                otherError(e);
            }
        }else {
            otherError(e);
        }
    }

    @Override
    public void onComplete() {
       //正常执行完毕
    }

    /**
     * 授权错误
     */
    public abstract void authError(int code,Throwable e);

    /**
     * 其它错误
     */
    public abstract void otherError(Throwable e);
}
