package com.robl2e.thisflicks.ui.utils;

import android.content.Context;

/**
 * Created by robl2e on 9/16/17.
 */

public class UIResourceUtils {

    public static int getScreenOrientation(Context context) {
        return context.getResources().getConfiguration().orientation;
    }
}
