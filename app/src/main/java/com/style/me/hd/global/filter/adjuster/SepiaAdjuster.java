package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class SepiaAdjuster extends AdjusterHelper<GPUImageSepiaFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setIntensity(range(percentage, 0.0f, 2.0f));
    }
}