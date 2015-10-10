package com.style.me.hd.presenter;

import android.content.Intent;

import com.style.me.hd.model.HomeModel;
import com.style.me.hd.model.PresenterModel;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by Kosh on 10/10/2015. copyrights are reserved
 */
public class HomePresenter implements PresenterModel {

    private HomeModel homeModel;

    public HomePresenter(HomeModel homeModel) {
        this.homeModel = homeModel;
    }

    @Override
    public void onActivityForResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onFilterClick(GPUImageFilter filter) {

    }
}
