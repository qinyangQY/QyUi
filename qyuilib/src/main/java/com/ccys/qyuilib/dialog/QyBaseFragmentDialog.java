package com.ccys.qyuilib.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.ccys.qyuilib.R;

/**
 * @ProjectName: QyUi
 * @Package: com.ccys.qyuilib.dialog
 * @ClassName: QyBaseFragmentDialog
 * @描述: 对话框的基类
 * @作者: 秦洋
 * @日期: 2019-12-03 13:28
 */
public abstract class QyBaseFragmentDialog extends DialogFragment {
    protected int layoutId;
    protected int style;
    protected int themeId;
    protected int animationId;
    protected QyBaseFragmentDialog(int layoutId, int style, int themeId){
        this.layoutId = layoutId;
        this.style = style;
        this.themeId = themeId;
    }
    protected QyBaseFragmentDialog(int layoutId, int style, int themeId, int animationId){
        this.layoutId = layoutId;
        this.style = style;
        this.themeId = themeId;
        this.animationId = animationId;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.style == 0){
            this.style = DialogFragment.STYLE_NO_TITLE;
        }
        if(this.themeId == 0){
            this.themeId = R.style.qy_normal_dialog;
        }
        setStyle(this.style,this.themeId);
        if(animationId != 0){
            getDialog().getWindow().setWindowAnimations(animationId);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        setWindowParams(window);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layoutId == 0){
            onBindView(null);
            return super.onCreateView(inflater, container, savedInstanceState);
        }else {
            View contentView = inflater.inflate(layoutId,container,false);
            QyViewHolder viewHolder = new QyViewHolder(getActivity(),contentView);
            onBindView(viewHolder);
            return contentView;
        }
    }
    /**
     * @method
     * @description 将数据绑定到视图
     * @date:
     * @作者: 秦洋
     * @参数
     * @return
     */
    public abstract void onBindView(QyViewHolder viewHolder);

    /**
     * 设置window的一些参数
     * @param window
     */
    protected void setWindowParams(Window window){}
}
