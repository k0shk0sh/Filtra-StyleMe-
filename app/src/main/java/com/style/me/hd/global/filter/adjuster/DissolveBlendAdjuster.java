package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class DissolveBlendAdjuster extends AdjusterHelper<GPUImageDissolveBlendFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setMix(range(percentage, 0.0f, 1.0f));
    }
}
