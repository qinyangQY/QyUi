package com.ccys.qyuilib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.ccys.qyuilib.R;

@SuppressLint("AppCompatCustomView")
public class QyImageView extends ImageView {
    private boolean isCircular;
    private int radius;
    public QyImageView(Context context) {
        super(context);
        initView(context,null);
    }

    public QyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public QyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.QyImageView);
        isCircular = ta.getBoolean(R.styleable.QyImageView_isCircular,false);
        radius = (int) ta.getDimension(R.styleable.QyImageView_radius,6);
        ta.recycle();
        setClipToOutline();
    }
    public void setShape(boolean isCircular,int radius){
        this.isCircular = isCircular;
        this.radius = radius;
        setClipToOutline();
    }
   private void setClipToOutline(){
       setClipToOutline(true);
       setOutlineProvider(new ViewOutlineProvider() {
           @Override
           public void getOutline(View view, Outline outline) {
               if(isCircular){
                   outline.setOval(0,0,view.getWidth(),view.getHeight());
               }else {
                   outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),radius);
               }
           }
       });
   }
}
