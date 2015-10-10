package com.style.me.hd.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.style.me.hd.R;
import com.style.me.hd.global.helper.BitmapHelper;
import com.style.me.hd.model.HomeModel;
import com.style.me.hd.model.PresenterModel;

import java.io.File;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by Kosh on 10/10/2015. copyrights are reserved
 */
public class HomePresenter implements PresenterModel {

    public static final int GALLERY_INTENT = 2005;
    public static final int CAMERA_INTENT = 2006;

    private HomeModel homeModel;

    public HomePresenter(HomeModel homeModel) {
        this.homeModel = homeModel;
    }

    @Override
    public void onActivityForResult(int requestCode, int resultCode, Intent data, File file) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_INTENT) {
                Uri imageUri = data.getData();
                BitmapHelper.loadImage(homeModel, imageUri);
            } else if (requestCode == CAMERA_INTENT) {
                if (file != null && file.exists()) {
                    BitmapHelper.loadImage(homeModel, file);
                } else {
                    homeModel.onError(homeModel.getContext().getString(R.string.error_loading_image));
                }
            }
        } else if (resultCode != Activity.RESULT_CANCELED) {
            homeModel.onError(homeModel.getContext().getString(R.string.error_loading_image));
        }
    }

    @Override
    public void onFilterClick(GPUImageFilter filter) {

    }
}
