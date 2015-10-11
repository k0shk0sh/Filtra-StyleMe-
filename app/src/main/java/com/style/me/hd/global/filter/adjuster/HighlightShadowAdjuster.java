package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class HighlightShadowAdjuster extends AdjusterHelper<GPUImageHighlightShadowFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setShadows(range(percentage, 0.0f, 1.0f));
        getFilter().setHighlights(range(percentage, 0.0f, 1.0f));
    }
}
