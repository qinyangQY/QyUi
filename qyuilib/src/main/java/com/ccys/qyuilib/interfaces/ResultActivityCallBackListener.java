package com.ccys.qyuilib.interfaces;

import android.content.Intent;

/**
 * @ProjectName: ZhenMei
 * @Package: com.ccys.qyuilib.interfaces
 * @ClassName: ResultActivityCallBackListener
 * @描述: java类作用描述
 * @作者: 秦洋
 * @日期: 2019-12-12 10:44
 */
public abstract class ResultActivityCallBackListener {
    private int requestCode;
    public ResultActivityCallBackListener(int requestCode){
        this.requestCode = requestCode;
    }
    public ResultActivityCallBackListener(){
        this.requestCode = requestCode;
    }
    public void setRequestCode(int requestCode){
        this.requestCode = requestCode;
    }
    /**
     * 界面返回来呆回的数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public abstract void callBack(int requestCode, int resultCode, Intent data);
    public int getCode(){
        return requestCode;
    }
}
