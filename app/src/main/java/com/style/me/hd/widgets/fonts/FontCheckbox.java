package com.style.me.hd.widgets.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

/**
 * Created by Kosh on 8/18/2015. copyrights are reserved
 */
public class FontCheckbox extends AppCompatCheckBox {

    public FontCheckbox(Context context) {
        super(context);
        init(null);
    }

    public FontCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FontCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (isInEditMode()) return;
        if (getTypeface() != null) {
            if (getTypeface().isBold()) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/app_font_bold.ttf");
                setTypeface(myTypeface);

            } else {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/app_font.ttf");
                setTypeface(myTypeface);
            }
        } else {
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/app_font.ttf");
            setTypeface(myTypeface);
        }
    }
}
