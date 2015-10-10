package com.style.me.hd.widgets;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

/**
 * Created by kosh20111 on 2/19/2015. CopyRights @ Innov8tif
 */
public class BoxCardLayout extends CardView {
    public BoxCardLayout(Context context) {
        super(context);
    }

    public BoxCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}
