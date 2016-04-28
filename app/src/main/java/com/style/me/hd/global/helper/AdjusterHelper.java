package com.style.me.hd.global.helper;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public abstract class AdjusterHelper<T extends GPUImageFilter> {
    private T filter;

    @SuppressWarnings("unchecked") public AdjusterHelper<T> filter(final GPUImageFilter filter) {
        this.filter = (T) filter;
        return this;
    }

    public T getFilter() {
        return filter;
    }

    public abstract void adjust(int percentage);

    protected float range(final int percentage, final float start, final float end) {
        return (end - start) * percentage / 100.0f + start;
    }

    protected int range(final int percentage, final int start, final int end) {
        return (end - start) * percentage / 100 + start;
    }

}