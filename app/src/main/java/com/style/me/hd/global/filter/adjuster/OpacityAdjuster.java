package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class OpacityAdjuster extends AdjusterHelper<GPUImageOpacityFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setOpacity(range(percentage, 0.0f, 1.0f));
    }
}
