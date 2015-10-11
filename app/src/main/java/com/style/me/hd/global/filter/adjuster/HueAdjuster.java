package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class HueAdjuster extends AdjusterHelper<GPUImageHueFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setHue(range(percentage, 0.0f, 360.0f));
    }
}