package com.style.me.hd.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.MenuItem;

import com.style.me.hd.R;
import com.style.me.hd.global.filter.FilterModel;
import com.style.me.hd.global.filter.adjuster.FilterAdjuster;
import com.style.me.hd.global.helper.BitmapHelper;
import com.style.me.hd.global.helper.InputHelper;
import com.style.me.hd.global.helper.ViewHelper;
import com.style.me.hd.model.HomeModel;
import com.style.me.hd.model.PresenterModel;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;

import jp.co.cyberagent.android.gpuimage.GPUImage;

/**
 * Created by Kosh on 10/10/2015. copyrights are reserved
 */
public class HomePresenter implements PresenterModel {

    public static final int GALLERY_INTENT = 2005;
    public static final int CAMERA_INTENT = 2006;
    private GPUImage gpuImage;
    private FilterAdjuster helper;

    private HomeModel homeModel;

    public HomePresenter(HomeModel homeModel) {
        this.homeModel = homeModel;
    }

    @Override public void onActivityForResult(int requestCode, int resultCode, Intent data, File file) {
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

    @Override public void onFilterClick(FilterModel filter) {
        Bitmap bitmap = homeModel.getBitmap();
        if (bitmap == null) {
            homeModel.onError(homeModel.getContext().getString(R.string.no_image));
            return;
        }
        GPUImage gpuFilter = getGpuImage();
        gpuFilter.setImage(homeModel.getBitmap());
        if (filter.isAdjustable()) {
            gpuFilter.setFilter(filter.getFilter());
            helper = new FilterAdjuster(filter.getFilter());
            homeModel.showSeekBar(helper);
        } else {
            gpuFilter.setFilter(filter.getFilter());
            homeModel.hideSeekBar();
        }
        homeModel.onFilterApplied(gpuFilter.getBitmapWithFilterApplied());

    }

    @Override public void onFrameClick(int color) {
        final GradientDrawable dr = new GradientDrawable();
        dr.setStroke(20, color);
        homeModel.onFrameSelected(dr);
    }

    @Override public void onBackgroundColor(int color) {
        homeModel.onBackgroundApplied(color);

    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        homeModel.hideSeekBar();
        if (item.getItemId() == R.id.save) {
            if (homeModel.getBitmap() != null) {
                String path = BitmapHelper.saveBitmap(homeModel.cardHolder());
                if (!InputHelper.isEmpty(path)) {
                    homeModel.onError(homeModel.getContext().getString(R.string.image_saved));
                } else {
                    homeModel.onError(homeModel.getContext().getString(R.string.error_save));
                }
            } else {
                homeModel.onError(homeModel.getContext().getString(R.string.no_image));
            }
            return true;
        } else if (item.getItemId() == R.id.share) {
            if (homeModel.getBitmap() != null) {
                String path = BitmapHelper.saveBitmap(homeModel.cardHolder());
                if (!InputHelper.isEmpty(path)) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
                    shareIntent.setType("image/*");
                    homeModel.getContext().startActivity(Intent.createChooser(shareIntent, homeModel.getContext().getString(R.string.share_to)));
                    homeModel.onError(homeModel.getContext().getString(R.string.image_saved));
                } else {
                    homeModel.onError(homeModel.getContext().getString(R.string.error_save));
                }
            } else {
                homeModel.onError(homeModel.getContext().getString(R.string.no_image));
            }
            return true;
        } else if (item.getItemId() == R.id.removeFilter) {
            if (homeModel.getBitmap() != null) {
                homeModel.setBitmap(homeModel.getBitmap());
                getGpuImage().deleteImage();
            } else {
                homeModel.onError(homeModel.getContext().getString(R.string.no_image));
            }
            return true;
        } else if (item.getItemId() == R.id.removeBackground) {
            homeModel.onBackgroundApplied(0);
            return true;
        } else if (item.getItemId() == R.id.removeFrame) {
            homeModel.onFrameSelected(null);
            return true;
        }
        return false;
    }

    public GPUImage getGpuImage() {
        if (gpuImage == null) gpuImage = new GPUImage(homeModel.getContext());
        return gpuImage;
    }

    public DiscreteSeekBar.OnProgressChangeListener onFilterAdjusted = new DiscreteSeekBar.OnProgressChangeListener() {
        @Override public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
            if (getGpuImage().getBitmapWithFilterApplied() != null) {
                if (helper != null) helper.adjust(value);
                getGpuImage().requestRender();
                homeModel.onFilterApplied(getGpuImage().getBitmapWithFilterApplied());
            } else {
                ViewHelper.animateVisibility(fromUser, seekBar);
            }
        }

        @Override public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}

        @Override public void onStopTrackingTouch(DiscreteSeekBar seekBar) {}
    };
}
