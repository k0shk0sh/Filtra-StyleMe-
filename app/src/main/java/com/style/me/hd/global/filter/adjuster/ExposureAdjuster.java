package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class ExposureAdjuster extends AdjusterHelper<GPUImageExposureFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setExposure(range(percentage, -10.0f, 10.0f));
    }
}
