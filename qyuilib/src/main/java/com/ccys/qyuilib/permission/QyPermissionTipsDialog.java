package com.ccys.qyuilib.permission;

import android.view.View;

import com.ccys.qyuilib.R;
import com.ccys.qyuilib.dialog.QyBaseFragmentDialog;
import com.ccys.qyuilib.dialog.QyViewHolder;

/**
 * @ProjectName: QyUi
 * @Package: com.ccys.qyuilib.permission
 * @ClassName: QyPermissionTipsDialog
 * @描述: 引导用户打开设置权限界面
 * @作者: 秦洋
 * @日期: 2019-12-04 09:29
 */
public class QyPermissionTipsDialog extends QyBaseFragmentDialog {
     private ClickCallBackLisener clickCallBackLisener;
    protected QyPermissionTipsDialog(ClickCallBackLisener clickCallBackLisener) {
        super(R.layout.permission_tips_dialog_layout, 0, 0);
        this.clickCallBackLisener = clickCallBackLisener;
    }

    @Override
    public void onBindView(QyViewHolder viewHolder) {
        setCancelable(false);
        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if(R.id.tv_cancel == view.getId()){
                    clickCallBackLisener.cancel();
                }else if(R.id.tv_confirm == view.getId()){
                    clickCallBackLisener.confirm();
                }
            }
        },R.id.tv_cancel,R.id.tv_confirm);
    }
    public interface ClickCallBackLisener{
        void cancel();
        void confirm();
    }
}
