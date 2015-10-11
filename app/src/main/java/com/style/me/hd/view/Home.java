package com.style.me.hd.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
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

import com.squareup.seismic.ShakeDetector;
import com.style.me.hd.R;
import com.style.me.hd.global.adapter.FiltersAdapter;
import com.style.me.hd.global.adapter.FramesAdapter;
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
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity implements HomeModel, ShakeDetector.Listener {

    private HomePresenter homePresenter;
    private File file;
    private Bitmap bitmap;
    private FilterAdjuster helper;
    private FiltersAdapter commonAdapter;
    private FramesAdapter framesAdapter;

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

    @OnClick(R.id.filter)
    void onFilter() {
        hideSeekBar();
        if (recycler.getAdapter() == null) {
            recycler.setAdapter(commonAdapter);
        }
        if (recycler.getAdapter() instanceof FramesAdapter) {
            recycler.setAdapter(commonAdapter);
        }
        ViewHelper.animateVisibility(true, recyclerHolder);
    }

    @OnClick(R.id.frames)
    void onFrame() {
        hideSeekBar();
        if (recycler.getAdapter() == null) {
            recycler.setAdapter(framesAdapter);
        }
        if (recycler.getAdapter() instanceof FiltersAdapter) {
            recycler.setAdapter(framesAdapter);
        }
        ViewHelper.animateVisibility(true, recyclerHolder);
    }

    @OnClick(R.id.cancel)
    void onCancel() {
        ViewHelper.animateVisibility(false, recyclerHolder);
        hideSeekBar();
    }

    @OnClick(R.id.camera)
    void onCamera() {
        hideSeekBar();
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
        hideSeekBar();
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
        framesAdapter = new FramesAdapter(homePresenter, getResources().getStringArray(R.array.colors));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setHasFixedSize(true);
        seek.setOnProgressChangeListener(onFilterAdjusted);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideSeekBar();
        if (item.getItemId() == R.id.save) {
            String path = BitmapHelper.saveBitmap(cardHolder);
            if (!InputHelper.isEmpty(path)) {
                onError("Image Saved.");
            } else {
                onError("Could not save image.");
            }
            return true;
        } else if (item.getItemId() == R.id.share) {
            String path = BitmapHelper.saveBitmap(cardHolder);
            if (!InputHelper.isEmpty(path)) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                onError("Image Saved.");
            } else {
                onError("Could not save image.");
            }
            return true;
        } else if (item.getItemId() == R.id.removeFilter) {
            if (getBitmap() != null) {
                zoomImage.setImageBitmap(getBitmap());
                homePresenter.getGpuImage().deleteImage();
            }
            return true;
        } else if (item.getItemId() == R.id.removeBackground) {
            cardHolder.setCardBackgroundColor(0);
            return true;
        } else if (item.getItemId() == R.id.removeFrame) {
            frameImage.setImageDrawable(null);
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
    public void onFrameSelected(Drawable drawable) {
        frameImage.setImageDrawable(drawable);
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
    protected void onDestroy() {
        if (!bitmap.isRecycled()) bitmap.recycle();
        if (homePresenter.getGpuImage() != null) homePresenter.getGpuImage().deleteImage();
        super.onDestroy();
    }

    @Override
    public void showSeekBar(FilterAdjuster helper) {
        this.helper = helper;
        seek.setProgress(20);
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
            if (homePresenter.getGpuImage().getBitmapWithFilterApplied() != null) {
                if (helper != null) helper.adjust(value);
                homePresenter.getGpuImage().requestRender();
                onFilterApplied(homePresenter.getGpuImage().getBitmapWithFilterApplied());
            } else {
                ViewHelper.animateVisibility(fromUser, seekBar);
            }
        }

        @Override
        public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

        }
    };

    @Override
    public void hearShake() {
        if (getBitmap() == null) {
            onError(getString(R.string.no_image));
            return;
        }
        homePresenter.onFilterClick(commonAdapter.getFilterModelList().get(new Random().nextInt(commonAdapter.getItemCount() - 1)));
        homePresenter.onBakcgroundClick(Color.parseColor(framesAdapter.getColors()[new Random().nextInt(framesAdapter.getItemCount() - 1)]));
        homePresenter.onFrameClick(Color.parseColor(framesAdapter.getColors()[new Random().nextInt(framesAdapter.getItemCount() - 1)]));
    }
}
