package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class PixelationAdjuster extends AdjusterHelper<GPUImagePixelationFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setPixel(range(percentage, 1.0f, 100.0f));
    }
}