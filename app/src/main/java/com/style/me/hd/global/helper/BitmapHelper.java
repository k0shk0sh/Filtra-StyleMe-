package com.style.me.hd.global.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by kosh20111 on 10/8/2015. CopyRights @ Innov8tif
 */
public class BitmapHelper {

    public static String saveBitmap(Bitmap image, String waterMark) {
        try {
            File file = FileHelper.generatePrivateFile();
            OutputStream fOut = new FileOutputStream(file);
            if (image.isMutable()) {
                addWaterMark(image, waterMark).compress(Bitmap.CompressFormat.JPEG, 70, fOut);
            } else {
                Bitmap workingBitmap = Bitmap.createBitmap(image);
                Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
                addWaterMark(mutableBitmap, waterMark).compress(Bitmap.CompressFormat.JPEG, 70, fOut);
                if (mutableBitmap != null && !mutableBitmap.isRecycled()) {
                    mutableBitmap.recycle();
                }
            }
            fOut.flush();
            fOut.close();
            Log.e("PAth", file.getPath());

            return file.getPath();
        } catch (Exception e) {
            Log.e("BitmapUtil", "SaveBitmap", e);
            return "because-android-sucks";
        }
    }

    public static String saveSignature(Bitmap image) {
        try {
            File file = FileHelper.generatePrivateFile();
            OutputStream fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e("BitmapUtil", "SaveBitmap", e);
            return "because-android-sucks";
        }
    }

    public static String saveSignature(Bitmap image, String waterMark) {
        try {
            File file = FileHelper.generatePrivateFile();
            OutputStream fOut = new FileOutputStream(file);
            if (image.isMutable()) {
                addWaterMark(image, waterMark).compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            } else {
                Bitmap workingBitmap = Bitmap.createBitmap(image);
                Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
                addWaterMark(mutableBitmap, waterMark).compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                if (mutableBitmap != null && !mutableBitmap.isRecycled()) {
                    mutableBitmap.recycle();
                }
            }
            fOut.flush();
            fOut.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e("BitmapUtil", "SaveBitmap", e);
            return "because-android-sucks";
        }
    }

    public static String saveBitmap(Bitmap image) {
        try {
            File file = FileHelper.generatePrivateFile();
            OutputStream fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            Log.e("PAth", file.getPath());
            return file.getPath();
        } catch (Exception e) {
            Log.e("BitmapUtil", "SaveBitmap", e);
            return "because-android-sucks";
        }
    }

    public static String saveBitmap(View view) {
        try {
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = view.getDrawingCache();
            File file = FileHelper.generatePrivateFile();
            OutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            view.setDrawingCacheEnabled(false);
            if (!bitmap.isRecycled()) bitmap.recycle();
            return file.getPath();
        } catch (Exception e) {
            Log.e("BitmapUtil", "SaveBitmap", e);
            return "because-android-sucks";
        }
    }

    public static String bitmapToBase64(Bitmap bmp) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, byteStream);
        byte[] b = byteStream.toByteArray();
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

    public static String fileToBase64(String path) {
        String encodedfile = null;
        FileInputStream inputStream = null;
        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            inputStream.read(bytes);
            encodedfile = Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return encodedfile;
    }

    public static Bitmap addWaterMark(Bitmap src, String watermark) {
        Canvas canvas = new Canvas(src);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#303F51B5"));
        paint.setAlpha(60);
        if (src.getWidth() >= src.getHeight()) {// the image might be taking in landscape in this case the text size will be bigger than the height
            paint.setTextSize(determineMaxTextSize(watermark, src.getHeight()));
        } else {
            paint.setTextSize(determineMaxTextSize(watermark, src.getWidth()));
        }
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight();
        canvas.rotate(-45, x, y / 2);
        canvas.drawText(watermark, x, y / 2, paint);
        return src;
    }

    private static int determineMaxTextSize(String str, float maxWidth) {
        int size = 0;
        Paint paint = new Paint();
        do {
            paint.setTextSize(++size);
        } while (paint.measureText(str) < maxWidth);

        return size;
    }

}
