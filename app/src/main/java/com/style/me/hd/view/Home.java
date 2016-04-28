package com.style.me.hd.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;
import com.style.me.hd.R;
import com.style.me.hd.global.adapter.FiltersAdapter;
import com.style.me.hd.global.adapter.FramesAdapter;
import com.style.me.hd.global.filter.FilterModel;
import com.style.me.hd.global.filter.adjuster.FilterAdjuster;
import com.style.me.hd.global.helper.AnimUtil;
import com.style.me.hd.global.helper.FileHelper;
import com.style.me.hd.global.helper.InputHelper;
import com.style.me.hd.global.helper.PermissionHelper;
import com.style.me.hd.global.helper.ViewHelper;
import com.style.me.hd.model.HomeModel;
import com.style.me.hd.presenter.HomePresenter;
import com.style.me.hd.widgets.MetaballView;
import com.style.me.hd.widgets.SquareImageView;
import com.style.me.hd.widgets.ZoomableImage;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Home extends AppCompatActivity implements HomeModel, ShakeDetector.Listener, EasyPermissions.PermissionCallbacks {

    private HomePresenter homePresenter;
    private File file;
    private Bitmap bitmap;
    private FiltersAdapter commonAdapter;
    private FramesAdapter framesAdapter;
    private ShakeDetector shakeDetector;
    private SensorManager sensorManager;
    @Bind(R.id.frameImage) SquareImageView frameImage;
    @Bind(R.id.progressHolder) LinearLayout progressHolder;
    @Bind(R.id.appbar) AppBarLayout appbar;
    @Bind(R.id.metaball) MetaballView metaball;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.cardHolder) View cardHolder;
    @Bind(R.id.zoomImage) ZoomableImage zoomImage;
    @Bind(R.id.recycler) RecyclerView recycler;
    @Bind(R.id.recyclerHolder) LinearLayout recyclerHolder;
    @Bind(R.id.seek) DiscreteSeekBar seek;
    @Bind(R.id.optionsHolder) LinearLayout optionsHolder;
    @Bind(R.id.frames) FloatingActionButton frames;
    @Bind(R.id.gallery) FloatingActionButton gallery;
    @Bind(R.id.camera) FloatingActionButton camera;
    @Bind(R.id.filter) FloatingActionButton filter;
    @Bind(R.id.cancel) FloatingActionButton cancel;
    @Bind(R.id.nestedScroll) NestedScrollView nestedScrollView;
    @Bind(R.id.coordinator) CoordinatorLayout coordinator;

    @OnClick(R.id.filter) void onFilter() {
        hideSeekBar();
        if (recycler.getAdapter() == null) {
            recycler.setAdapter(commonAdapter);
        }
        if (recycler.getAdapter() instanceof FramesAdapter) {
            recycler.setAdapter(commonAdapter);
        }
        scrollBottom();
        AnimUtil.circularRevealFromTop(recyclerHolder, filter, true);
    }

    @OnClick(R.id.frames) void onFrame() {
        hideSeekBar();
        if (recycler.getAdapter() == null) {
            recycler.setAdapter(framesAdapter);
        }
        if (recycler.getAdapter() instanceof FiltersAdapter) {
            recycler.setAdapter(framesAdapter);
        }
        scrollBottom();
        AnimUtil.circularRevealFromTop(recyclerHolder, frames, true);
    }

    @OnClick(R.id.cancel) void onCancel() {
        AnimUtil.circularRevealFromTop(recyclerHolder, cancel, false);
        hideSeekBar();
        scrollTop();
    }

    @AfterPermissionGranted(HomePresenter.CAMERA_INTENT)
    @OnClick(R.id.camera) void onCamera() {
        hideSeekBar();
        if (PermissionHelper.isPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            file = FileHelper.generateFile();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(takePictureIntent, HomePresenter.CAMERA_INTENT);
            } else {
                onError(getString(R.string.no_camera));
            }
        } else {
            EasyPermissions.requestPermissions(this, "Permission Required",
                    HomePresenter.CAMERA_INTENT, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(HomePresenter.GALLERY_INTENT)
    @OnClick(R.id.gallery) void onGallery() {
        hideSeekBar();
        if (PermissionHelper.isPermissionGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Image"), HomePresenter.GALLERY_INTENT);
        } else {
            EasyPermissions.requestPermissions(this, "Permission Required",
                    HomePresenter.GALLERY_INTENT, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        metaball.setPaintMode(0);
        FilterModel filter = new FilterModel(this);
        commonAdapter = new FiltersAdapter(presenter(), filter.getFilters());
        framesAdapter = new FramesAdapter(presenter(), getResources().getStringArray(R.array.colors));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setHasFixedSize(true);
        seek.setOnProgressChangeListener(presenter().onFilterAdjusted);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(this);
        zoomImage.setNestedScrollView(nestedScrollView);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return presenter().onOptionsItemSelected(item);
    }

    @Override protected void onDestroy() {
        if (!bitmap.isRecycled()) bitmap.recycle();
        if (presenter().getGpuImage() != null) presenter().getGpuImage().deleteImage();
        super.onDestroy();
    }

    @Override protected void onPause() {
        super.onPause();
        shakeDetector.stop();
    }

    @Override protected void onResume() {
        super.onResume();
        shakeDetector.start(sensorManager);
    }

    @Override public Context getContext() {
        return this;
    }

    @Override public void onFilterApplied(Bitmap bitmap) {
        zoomImage.setImageBitmap(bitmap);
    }

    @Override public void onBackgroundApplied(int color) {
        AnimUtil.animateColorChange(cardHolder, color, null);
    }

    @Override public void onFrameSelected(Drawable drawable) {
        frameImage.setImageDrawable(drawable);
    }

    @Override public void onError(String cause) {
        Toast.makeText(this, InputHelper.isEmpty(cause) ? getString(R.string.general_error_message) : cause, Toast.LENGTH_LONG).show();
    }

    @Override public void showProgress() {
        ViewHelper.animateTranslateY(-appbar.getHeight(), false, appbar);
        ViewHelper.animateTranslateY(optionsHolder.getHeight(), false, optionsHolder);
        ViewHelper.animateVisibility(true, progressHolder);
    }

    @Override public void hideProgress() {
        ViewHelper.animateTranslateY(0, false, appbar);
        ViewHelper.animateTranslateY(0, false, optionsHolder);
        ViewHelper.animateVisibility(false, progressHolder);

    }

    @Override public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override public Bitmap getBitmap() {
        return bitmap;
    }

    @Override public ImageView getImageView() {
        return zoomImage;
    }

    @Override public void showSeekBar(FilterAdjuster helper) {
        seek.setProgress(20);
        ViewHelper.animateVisibility(true, seek);
    }

    @Override public void hideSeekBar() {
        ViewHelper.animateVisibility(false, seek);
    }

    @Override public View cardHolder() {
        return cardHolder;
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter().onActivityForResult(requestCode, resultCode, data, file);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void hearShake() {
        if (getBitmap() == null) {
            onError(getString(R.string.no_image));
            return;
        }
        presenter().onFilterClick(commonAdapter.getFilterModelList().get(new Random().nextInt(commonAdapter.getItemCount() - 1)));
        presenter().onBackgroundColor(Color.parseColor(framesAdapter.getColors()[new Random().nextInt(framesAdapter.getItemCount() - 1)]));
        presenter().onFrameClick(Color.parseColor(framesAdapter.getColors()[new Random().nextInt(framesAdapter.getItemCount() - 1)]));
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == HomePresenter.CAMERA_INTENT) {
            onCamera();
        } else if (requestCode == HomePresenter.GALLERY_INTENT) {
            onGallery();
        }
    }

    @Override public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    private void scrollBottom() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            behavior.onNestedFling(coordinator, appbar, null, 0, -params.height, true);
        }
    }

    private void scrollTop() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            behavior.onNestedFling(coordinator, appbar, null, 0, params.height, true);
        }
    }

    private HomePresenter presenter() {
        if (homePresenter == null) homePresenter = new HomePresenter(this);
        return homePresenter;
    }
}
