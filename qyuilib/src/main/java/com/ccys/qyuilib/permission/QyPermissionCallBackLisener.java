package com.ccys.qyuilib.permission;

/**
 * @ProjectName: QyUi
 * @Package: com.ccys.qyuilib.permission
 * @ClassName: QyPermissionCallBackLisener
 * @描述: 动态权限的状态回掉
 * @作者: 秦洋
 * @日期: 2019-12-03 18:02
 */
public interface QyPermissionCallBackLisener {
    /**
     * @method  用户拒绝了权限
     * @description 描述一下方法的作用
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
        void denied();
        /**
         * @method  用户同意了权限
         * @description 描述一下方法的作用
         * @date:
         * @作者: 秦洋
         * @参数
         * @return
         */
        void granted();
}
