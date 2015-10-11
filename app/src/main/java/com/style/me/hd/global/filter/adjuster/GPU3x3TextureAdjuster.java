package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImage3x3TextureSamplingFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class GPU3x3TextureAdjuster extends AdjusterHelper<GPUImage3x3TextureSamplingFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
    }
}