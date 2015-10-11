package com.style.me.hd.model;

import android.content.Intent;

import com.style.me.hd.global.filter.FilterModel;

import java.io.File;

/**
 * Created by Kosh on 10/10/2015. copyrights are reserved
 */
public interface PresenterModel {

    void onActivityForResult(int requestCode, int resultCode, Intent data, File file);

    void onFilterClick(FilterModel filter);

    void onFrameClick(int color);

    void onBakcgroundClick(int color);
}
