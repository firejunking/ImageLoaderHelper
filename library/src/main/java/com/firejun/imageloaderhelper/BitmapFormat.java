package com.firejun.imageloaderhelper;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BitmapFormat {

    /**
     * {@link android.graphics.Bitmap.Config#ARGB_8888}
     */
    public static final int ARGB_8888 = 1;

    /**
     * {@link android.graphics.Bitmap.Config#RGB_565}
     */
    public static final int RGB_565 = 0;

    @IntDef({ARGB_8888, RGB_565})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BitmapFormatType {
    }

}
