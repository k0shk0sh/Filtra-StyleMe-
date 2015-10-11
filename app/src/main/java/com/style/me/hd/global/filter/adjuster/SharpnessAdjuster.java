package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class SharpnessAdjuster extends AdjusterHelper<GPUImageSharpenFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setSharpness(range(percentage, -4.0f, 4.0f));
    }
}