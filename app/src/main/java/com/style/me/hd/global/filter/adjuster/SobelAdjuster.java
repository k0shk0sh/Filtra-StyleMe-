package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class SobelAdjuster extends AdjusterHelper<GPUImageSobelEdgeDetection> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
    }
}