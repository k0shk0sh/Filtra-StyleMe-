package com.style.me.hd.global.helper;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by kosh20111 on 10/7/2015. CopyRights @ Innov8tif
 */
public class FileHelper {

    private static String folderName;

    private static String dataFolderName;

    public static void initFolderName(String fName) {
        folderName = Environment.getExternalStorageDirectory() + "/" + fName;
    }

    public static void initPrivateFolder(Context context, String folderName) {
        dataFolderName = context.getCacheDir().getPath() + "/" + folderName;
    }

    public static File privateFolder() {
        if (InputHelper.isEmpty(dataFolderName)) {
            throw new NullPointerException("dataFolderName is null call initPrivateFolder() first");
        }
        File file = new File(dataFolderName);
        if (!file.exists())
            file.mkdir();
        return file;
    }

    public static File generatePrivateFile() {
        if (InputHelper.isEmpty(dataFolderName)) {
            throw new NullPointerException("dataFolderName is null call initPrivateFolder() first");
        }
        File file = new File(dataFolderName, ".nomedia");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                file.mkdir();
            }
        }
        return new File(privateFolder(), generateFileName());
    }

    public static File folderName() {
        if (InputHelper.isEmpty(folderName)) {
            throw new NullPointerException("dataFolderName is null call initPrivateFolder() first");
        }
        File file = new File(folderName);
        if (!file.exists())
            file.mkdir();
        return file;
    }

    public static String getBaseFolderName() {
        return folderName;
    }

    public static String getBasePrivateFolder() {
        return dataFolderName;
    }

    public static File getFile(String path) {
        return new File(path);
    }

    private static String getJpgImagePath(String path) {
        return path + ".jpg";
    }

    public static boolean deleteFile(String path) {
        if (!InputHelper.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                return file.delete();
            } else {
                file = new File(folderName(), path);
                if (file.exists()) {
                    return file.delete();
                } else {
                    file = new File(privateFolder(), path);
                    return file.delete();
                }
            }
        }
        return false;
    }

    public static void deleteFile(List<String> paths) {
        for (String path : paths) {
            if (path != null) {
                File file = new File(folderName(), path);
                if (file.exists()) {
                    file.delete();
                } else {
                    File file1 = new File(privateFolder(), path);
                    if (file1.exists()) {
                        file1.delete();
                    }
                }
            }
        }
    }

    public static String generateFileName() {
        return getJpgImagePath("IMG-" + String.valueOf(System.currentTimeMillis()));
    }

    public static File generateFile() {
        if (InputHelper.isEmpty(folderName)) {
            throw new NullPointerException("dataFolderName is null call initPrivateFolder() first");
        }
        File file = new File(folderName, ".nomedia");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                file.mkdir();
            }
        }
        return new File(folderName(), generateFileName());
    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }
}
