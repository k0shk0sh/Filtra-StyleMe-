package com.style.me.hd.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;

import com.style.me.hd.R;
import com.style.me.hd.global.filter.FilterModel;
import com.style.me.hd.global.filter.adjuster.FilterAdjuster;
import com.style.me.hd.global.helper.BitmapHelper;
import com.style.me.hd.model.HomeModel;
import com.style.me.hd.model.PresenterModel;

import java.io.File;

import jp.co.cyberagent.android.gpuimage.GPUImage;

/**
 * Created by Kosh on 10/10/2015. copyrights are reserved
 */
public class HomePresenter implements PresenterModel {

    public static final int GALLERY_INTENT = 2005;
    public static final int CAMERA_INTENT = 2006;
    private GPUImage gpuImage;

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
    public void onFilterClick(FilterModel filter) {
        Bitmap bitmap = homeModel.getBitmap();
        if (bitmap == null) {
            homeModel.onError(homeModel.getContext().getString(R.string.no_image));
            return;
        }
        GPUImage gpuFilter = getGpuImage();
        gpuFilter.setImage(homeModel.getBitmap());
        if (filter.isAdjustable()) {
            gpuFilter.setFilter(filter.getFilter());
            homeModel.showSeekBar(new FilterAdjuster(filter.getFilter()));
        } else {
            gpuFilter.setFilter(filter.getFilter());
            homeModel.hideSeekBar();
        }
        homeModel.onFilterApplied(gpuFilter.getBitmapWithFilterApplied());

    }

    @Override
    public void onFrameClick(int color) {
        final GradientDrawable dr = new GradientDrawable();
        dr.setStroke(20, color);
        homeModel.onFrameSelected(dr);
    }

    @Override
    public void onBakcgroundClick(int color) {
        homeModel.onBackgroundApplied(color);

    }

    public GPUImage getGpuImage() {
        if (gpuImage == null) gpuImage = new GPUImage(homeModel.getContext());
        return gpuImage;
    }
}
