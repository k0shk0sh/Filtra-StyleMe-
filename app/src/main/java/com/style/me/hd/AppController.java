package com.style.me.hd;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.style.me.hd.global.helper.FileHelper;

import de.greenrobot.event.EventBus;

/**
 * Created by Kosh on 10/10/2015. copyrights are reserved
 */
public class AppController extends Application {

    private static AppController controller;

    private ImageLoader imageLoader;

    private final int MAX_AGE = 1200000;//20minutes

    @Override public void onCreate() {
        super.onCreate();
        FileHelper.initFolderName(getString(R.string.app_name));
        FileHelper.initPrivateFolder(this, getString(R.string.app_name));
        controller = this;
    }

    public static AppController getController() {
        return controller;
    }

    public EventBus eventBus() {
        return EventBus.getDefault();
    }

    public ImageLoader getImageLoader() {
        if (imageLoader == null) imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .writeDebugLogs()
                    .diskCache(new LimitedAgeDiskCache(getCacheDir(), MAX_AGE))//for the sake of consider image exif
                    .threadPriority(Thread.MAX_PRIORITY)
                    .defaultDisplayImageOptions(getDisplayOptions())
                    .denyCacheImageMultipleSizesInMemory()
                    .build();
            ImageLoader.getInstance().init(config);
        }
        return imageLoader;
    }

    public DisplayImageOptions getDisplayOptions() {
        return new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
    }

    @Override public void onLowMemory() {
        getImageLoader().clearMemoryCache();
        super.onLowMemory();
    }

    @Override public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == Activity.TRIM_MEMORY_BACKGROUND || level == Activity.TRIM_MEMORY_RUNNING_LOW) {
            getImageLoader().clearMemoryCache();
            Log.e("AppController:Clearing", "Memory Cleared " + level);
        }
    }
}
