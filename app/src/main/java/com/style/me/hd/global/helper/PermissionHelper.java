package com.style.me.hd.global.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kosh on 28 Apr 2016, 10:08 PM
 */
public class PermissionHelper {

    /**
     * be aware as it might return null (do check if the returned result is not null!)
     * <p/>
     * can be used outside of activity.
     */
    public static String declinedPermission(@NonNull Context context, @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return permission;
            }
        }
        return null;
    }

    /**
     * @return list of permissions that the user declined or not yet granted.
     */
    public static String[] declinedPermissions(@NonNull Context context, @NonNull String[] permissions) {
        List<String> permissionsNeeded = new ArrayList<String>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }
        return permissionsNeeded.toArray(new String[permissionsNeeded.size()]);
    }

    /**
     * return true if permission is granted, false otherwise.
     * <p/>
     * can be used outside of activity.
     */
    public static boolean isPermissionGranted(@NonNull Context context, @NonNull String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * return true if permission is declined, false otherwise.
     * <p/>
     * can be used outside of activity.
     */
    public static boolean isPermissionDeclined(@NonNull Context context, @NonNull String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @return true if explanation needed.
     */
    public static boolean isExplanationNeeded(@NonNull Activity context, @NonNull String permissionName) {
        return ActivityCompat.shouldShowRequestPermissionRationale(context, permissionName);
    }

    /**
     * @return true if the permission is patently denied by the user and only can be granted via settings Screen
     * <p/>
     * consider using {@link PermissionHelper#openSettingsScreen(Context)} to open settings screen
     */
    public static boolean isPermissionPermanentlyDenied(@NonNull Activity context, @NonNull String permission) {
        return isPermissionDeclined(context, permission) && !isExplanationNeeded(context, permission);
    }

    /**
     * open android settings screen for your app.
     */
    public static void openSettingsScreen(@NonNull Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.parse("package:" + context.getPackageName());
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * @return true if permission exists in the manifest, false otherwise.
     */
    public static boolean permissionExists(@NonNull Context context, @NonNull String permissionName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (packageInfo.requestedPermissions != null) {
                for (String p : packageInfo.requestedPermissions) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
