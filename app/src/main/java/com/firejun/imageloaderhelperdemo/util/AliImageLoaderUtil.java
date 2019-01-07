package com.firejun.imageloaderhelperdemo.util;

import android.widget.ImageView;

import com.firejun.ImageLoaderhelper.ImageLoaderUtil;

public class AliImageLoaderUtil {

    public static void loadImage(String imgPath, int placeholder, ImageView imageView) {
        ImageLoaderUtil.getInstance().loadImage(imgPath, placeholder, imageView);
    }
}
