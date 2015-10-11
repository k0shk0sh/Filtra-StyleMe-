package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class ContrastAdjuster extends AdjusterHelper<GPUImageContrastFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setContrast(range(percentage, 0.0f, 2.0f));
    }
}