package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class MonochromeAdjuster extends AdjusterHelper<GPUImageMonochromeFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setIntensity(range(percentage, 0.0f, 1.0f));
        //getFilter().setColor(new float[]{0.6f, 0.45f, 0.3f, 1.0f});
    }
}