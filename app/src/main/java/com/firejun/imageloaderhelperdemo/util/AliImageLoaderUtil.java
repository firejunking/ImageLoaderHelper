package com.firejun.imageloaderhelperdemo.util;

import android.widget.ImageView;

import com.firejun.ImageLoaderhelper.ImageLoaderUtil;

/**
 * 这里只是简单使用了一部分代码，这里不会让你觉得过度封装，因为使用阿里云API，会有很多对应的API，这样封装不会与其他库有任何耦合
 */
public class AliImageLoaderUtil {
    public static void loadImage(String imgPath, int placeholder, ImageView imageView) {
        ImageLoaderUtil.getInstance().loadImage(imgPath, placeholder, imageView);
    }
}
