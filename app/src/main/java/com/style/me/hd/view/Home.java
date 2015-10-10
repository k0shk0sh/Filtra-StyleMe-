package com.style.me.hd.view;

import android.app.ProgressDialog;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.style.me.hd.R;
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

    @Bind(R.id.frameImage)
    SquareImageView frameImage;
    @Bind(R.id.progressHolder)
    LinearLayout progressHolder;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.metaball)
    MetaballView metaball;
    private HomePresenter homePresenter;
    private ProgressDialog progressDialog;
    private File file;
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
        ViewHelper.animateVisibility(true, recyclerHolder);
    }

    @OnClick(R.id.frames)
    void onFrame() {
        ViewHelper.animateVisibility(true, recyclerHolder);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onFilterApplied(Bitmap bitmap) {

    }

    @Override
    public void onBackgroundApplied(int color) {

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
//        if (progressDialog != null && progressDialog.isShowing()) return;
//        if (progressDialog == null) progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage(getString(R.string.in_progress));
//        progressDialog.show();
    }

    @Override
    public void hideProgress() {
//        if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        ViewHelper.animateTranslateY(0, false, appbar);
        ViewHelper.animateTranslateY(0, false, optionsHolder);
        ViewHelper.animateVisibility(false, progressHolder);

    }

    @Override
    public ImageView getImageView() {
        return zoomImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        homePresenter.onActivityForResult(requestCode, resultCode, data, file);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
