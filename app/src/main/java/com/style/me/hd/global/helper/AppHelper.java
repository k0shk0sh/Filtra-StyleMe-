package com.style.me.hd.global.helper;

import android.os.Build;

/**
 * Created by Kosh on 10/10/2015. copyrights are reserved
 */
public class AppHelper {

    public static boolean isLollipopOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isBelowLollipop() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

}
