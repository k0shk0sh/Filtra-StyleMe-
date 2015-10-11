package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class GammaAdjuster extends AdjusterHelper<GPUImageGammaFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setGamma(range(percentage, 0.0f, 3.0f));
    }
}