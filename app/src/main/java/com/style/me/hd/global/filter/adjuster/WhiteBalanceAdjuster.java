package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class WhiteBalanceAdjuster extends AdjusterHelper<GPUImageWhiteBalanceFilter> {
    @Override
    public void adjust(final int percentage) {
        getFilter().setTemperature(range(percentage, 2000.0f, 8000.0f));
        //getFilter().setTint(range(percentage, -100.0f, 100.0f));
    }
}
