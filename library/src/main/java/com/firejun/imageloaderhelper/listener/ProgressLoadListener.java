package com.firejun.imageloaderhelper.listener;

public interface ProgressLoadListener {
    void update(int bytesRead, int contentLength);

    void onException();

    void onResourceReady();
}
