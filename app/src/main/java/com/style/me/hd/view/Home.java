package com.style.me.hd.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.style.me.hd.R;
import com.style.me.hd.global.adapter.FiltersAdapter;
import com.style.me.hd.global.filter.FilterModel;
import com.style.me.hd.global.filter.adjuster.FilterAdjuster;
import com.style.me.hd.global.helper.BitmapHelper;
import com.style.me.hd.global.helper.FileHelper;
import com.style.me.hd.global.helper.InputHelper;
import com.style.me.hd.global.helper.ViewHelper;
import com.style.me.hd.model.HomeModel;
import com.style.me.hd.presenter.HomePresenter;
import com.style.me.hd.widgets.MetaballView;
import com.style.me.hd.widgets.SquareImageView;
import com.style.me.hd.widgets.ZoomableImage;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity implements HomeModel {

    private HomePresenter homePresenter;
    private File file;
    private Bitmap bitmap;
    private FilterAdjuster helper;

    @Bind(R.id.frameImage)
    SquareImageView frameImage;
    @Bind(R.id.progressHolder)
    LinearLayout progressHolder;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.metaball)
    MetaballView metaball;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.cardHolder)
    CardView cardHolder;
    @Bind(R.id.zoomImage)
    ZoomableImage zoomImage;
    @Bind(R.id.cancel)
    FloatingActionButton cancel;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.recyclerHolder)
    LinearLayout recyclerHolder;
    @Bind(R.id.seek)
    DiscreteSeekBar seek;
    @Bind(R.id.frames)
    FloatingActionButton frames;
    @Bind(R.id.gallery)
    FloatingActionButton gallery;
    @Bind(R.id.camera)
    FloatingActionButton camera;
    @Bind(R.id.filter)
    FloatingActionButton filter;
    @Bind(R.id.optionsHolder)
    LinearLayout optionsHolder;
    private FiltersAdapter commonAdapter;

    @OnClick(R.id.filter)
    void onFilter() {
        if (!recyclerHolder.isShown())
            ViewHelper.animateVisibility(true, recyclerHolder);
        else
            ViewHelper.animateVisibility(false, recyclerHolder);
    }

    @OnClick(R.id.frames)
    void onFrame() {
        if (!recyclerHolder.isShown())
            ViewHelper.animateVisibility(true, recyclerHolder);
        else
            ViewHelper.animateVisibility(false, recyclerHolder);

    }

    @OnClick(R.id.cancel)
    void onCancel() {
        ViewHelper.animateVisibility(false, recyclerHolder);
    }

    @OnClick(R.id.camera)
    void onCamera() {
        file = FileHelper.generateFile();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(takePictureIntent, HomePresenter.CAMERA_INTENT);
        } else {
            onError(getString(R.string.no_camera));
        }
    }

    @OnClick(R.id.gallery)
    void onGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), HomePresenter.GALLERY_INTENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        metaball.setPaintMode(0);
        homePresenter = new HomePresenter(this);
        FilterModel filter = new FilterModel(this);
        commonAdapter = new FiltersAdapter(homePresenter, filter.getFilters());
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(commonAdapter);
        seek.setOnProgressChangeListener(onFilterAdjusted);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            BitmapHelper.saveBitmap(cardHolder);
            return true;
        } else if (item.getItemId() == R.id.share) {
            String path = BitmapHelper.saveBitmap(cardHolder);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
            return true;
        } else if (item.getItemId() == R.id.removeFilter) {
            if (getBitmap() != null) {
                zoomImage.setImageBitmap(getBitmap());
                homePresenter.getGpuImage().deleteImage();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onFilterApplied(Bitmap bitmap) {
        zoomImage.setImageBitmap(bitmap);
    }

    @Override
    public void onBackgroundApplied(int color) {
        cardHolder.setCardBackgroundColor(color);
    }

    @Override
    public void onFrameSelected(int resId) {

    }

    @Override
    public void onError(String cause) {
        Toast.makeText(this, InputHelper.isEmpty(cause) ? getString(R.string.general_error_message) : cause, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        ViewHelper.animateTranslateY(-appbar.getHeight(), false, appbar);
        ViewHelper.animateTranslateY(optionsHolder.getHeight(), false, optionsHolder);
        ViewHelper.animateVisibility(true, progressHolder);
    }

    @Override
    public void hideProgress() {
        ViewHelper.animateTranslateY(0, false, appbar);
        ViewHelper.animateTranslateY(0, false, optionsHolder);
        ViewHelper.animateVisibility(false, progressHolder);

    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public ImageView getImageView() {
        return zoomImage;
    }

    @Override
    public void showSeekBar(FilterAdjuster helper) {
        this.helper = helper;
        ViewHelper.animateVisibility(true, seek);
    }

    @Override
    public void hideSeekBar() {
        ViewHelper.animateVisibility(false, seek);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        homePresenter.onActivityForResult(requestCode, resultCode, data, file);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private DiscreteSeekBar.OnProgressChangeListener onFilterAdjusted = new DiscreteSeekBar.OnProgressChangeListener() {
        @Override
        public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
            if (helper != null) helper.adjust(value);
            homePresenter.getGpuImage().requestRender();
            onFilterApplied(homePresenter.getGpuImage().getBitmapWithFilterApplied());
        }

        @Override
        public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

        }
    };
}
