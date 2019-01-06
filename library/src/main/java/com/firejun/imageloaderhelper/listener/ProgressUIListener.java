package com.firejun.imageloaderhelper.listener;

public interface ProgressUIListener {
    void update(int bytesRead, int contentLength);
}
