package com.y.skin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.y.skin.R;
import com.y.skin_library.SkinViewSupport;
import com.y.skin_library.util.SkinResource;

public class Item extends FrameLayout implements SkinViewSupport {
    TextView tv;

    public Item(Context context) {
        this(context,null,-1);
    }

    public Item(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public Item(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,100);
        lp.gravity = Gravity.CENTER;
        setLayoutParams(lp);
    }


    public void setPath(String path){
        tv = new TextView(getContext());
        tv.setText(path);
        tv.setTextColor(SkinResource.getInstance().getColorStateList(R.color.colorAccent));
        addView(tv);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void applySkin() {
        if(tv != null){
            tv.setTextColor(SkinResource.getInstance().getColorStateList(R.color.colorAccent));
        }
    }
}
