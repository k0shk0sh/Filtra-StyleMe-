package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class BrightnessAdjuster extends AdjusterHelper<GPUImageBrightnessFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setBrightness(range(percentage, -1.0f, 1.0f));
    }
}