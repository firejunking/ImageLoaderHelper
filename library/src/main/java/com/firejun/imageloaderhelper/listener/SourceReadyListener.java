package com.firejun.imageloaderhelper.listener;

import android.graphics.drawable.Drawable;

public interface SourceReadyListener {
    void onResourceReady(Drawable resource);

    void onException(Exception e);
}
