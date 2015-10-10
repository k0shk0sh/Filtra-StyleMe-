package com.style.me.hd.model;

import android.content.Intent;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by Kosh on 10/10/2015. copyrights are reserved
 */
public interface PresenterModel {

    void onActivityForResult(int requestCode, int resultCode, Intent data);

    void onFilterClick(GPUImageFilter filter);


}
