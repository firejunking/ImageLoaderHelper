package com.firejun.imageloaderhelper.listener;

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
