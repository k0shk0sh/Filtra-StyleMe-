package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class SaturationAdjuster extends AdjusterHelper<GPUImageSaturationFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setSaturation(range(percentage, 0.0f, 2.0f));
    }
}
