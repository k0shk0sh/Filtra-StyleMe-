package com.style.me.hd.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by kosh20111 on 2/19/2015. CopyRights @ Innov8tif
 */
public class BoxFrameLayout extends FrameLayout {
    public BoxFrameLayout(Context context) {
        super(context);
    }

    public BoxFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}
