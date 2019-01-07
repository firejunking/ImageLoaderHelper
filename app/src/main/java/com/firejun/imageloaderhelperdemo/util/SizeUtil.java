package com.firejun.imageloaderhelperdemo.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

public class SizeUtil {

    public static int getScreenWidthInPx(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
