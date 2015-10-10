package com.style.me.hd.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.style.me.hd.R;
import com.style.me.hd.model.HomeModel;
import com.style.me.hd.presenter.HomePresenter;
import com.style.me.hd.widgets.ZoomableImage;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity implements HomeModel {

    HomePresenter homePresenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        homePresenter = new HomePresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
