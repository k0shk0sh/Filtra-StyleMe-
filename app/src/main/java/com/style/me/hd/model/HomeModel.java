package com.style.me.hd.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.style.me.hd.global.filter.adjuster.FilterAdjuster;

/**
 * Created by Kosh on 10/10/2015. copyrights are reserved
 */
public interface HomeModel {

    Context getContext();

    void onFilterApplied(Bitmap bitmap);

    void onBackgroundApplied(int color);

    void onFrameSelected(Drawable drawable);

    void onError(String cause);

    void showProgress();

    void hideProgress();

    void setBitmap(Bitmap bitmap);

    Bitmap getBitmap();

    ImageView getImageView();

    void showSeekBar(FilterAdjuster helper);

    void hideSeekBar();

    View cardHolder();
}
