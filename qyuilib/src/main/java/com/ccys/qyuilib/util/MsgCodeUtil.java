package com.ccys.qyuilib.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by 秦洋 on 2018/5/22.
 */

public class MsgCodeUtil {
    private TextView code;
    private TimeCount timeCount;
    private OnCodeStatusLisener onCodeStatusLisener;
    private static long time1;
    private static long time2;
    public MsgCodeUtil(TextView code){
         this.code = code;
         initTimeCunt();
    }
    public void startTime(){
        timeCount.start();
        if(onCodeStatusLisener != null){
            onCodeStatusLisener.startCode();
        }
    }
    public void setOnCodeStatusLisener(OnCodeStatusLisener onCodeStatusLisener){
        this.onCodeStatusLisener = onCodeStatusLisener;
    }
    public void initTimeCunt(){
        if (System.currentTimeMillis() - time1 < 60000) {
            long b = (System.currentTimeMillis() - time1);
            long a = b / 1000 * 1000;
            timeCount = new TimeCount(time2 - a, 1000);
            timeCount.start();
        } else {
            timeCount = new TimeCount(60000, 1000);
        }
    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            code.setText("重新获取验证码");
            code.setClickable(true);
            if(onCodeStatusLisener != null){
                onCodeStatusLisener.endCode();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            code.setClickable(false);
            code.setText(millisUntilFinished / 1000 + "秒");
        }
    }
    public interface OnCodeStatusLisener{
        void startCode();
        void endCode();
    }
}
