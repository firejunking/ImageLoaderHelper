package com.firejun.ImageLoaderhelper;

import android.content.Context;
import android.widget.ImageView;

import com.firejun.imageloaderhelper.BaseImageLoaderStrategy;
import com.firejun.imageloaderhelper.listener.ILoadPictureResource;
import com.firejun.imageloaderhelper.listener.ImageSaveListener;
import com.firejun.imageloaderhelper.listener.ProgressLoadListener;
import com.firejun.imageloaderhelper.listener.SourceReadyListener;

public enum ImageLoaderUtil {
    INSTANCE;
    //图片默认加载类型 以后有可能有多种类型
    public static final int PIC_DEFAULT_TYPE = 0;

    //图片默认加载策略 以后有可能有多种图片加载策略
    public static final int LOAD_STRATEGY_DEFAULT = 0;

    //持续优化更新
    private BaseImageLoaderStrategy mStrategy;

    ImageLoaderUtil() {
        mStrategy = new GlideImageLoaderStrategy();
    }

    //单例模式，节省资源
    public static ImageLoaderUtil getInstance() {
        return INSTANCE;
    }

    /**
     * 统一使用App context
     * 可能带来的问题：http://stackoverflow.com/questions/31964737/glide-image-loading-with-application-context
     */
    public void loadImage(String url, int placeholder, ImageView imageView) {
        mStrategy.loadImage(imageView.getContext(), url, placeholder, imageView);
    }

    public void loadImage(String url, ImageView imageView, int width, int height) {
        mStrategy.loadImage(imageView.getContext(), url, imageView, width, height);
    }

    public void loadImage(String url, int placeholder, ImageView imageView, int width, int height) {
        mStrategy.loadImage(imageView.getContext(), url, placeholder, imageView, width, height);
    }

    public void loadImage(String url, int placeholder, ImageView imageView, int animate, int width, int height) {
        mStrategy.loadImage(imageView.getContext(), url, placeholder, imageView, animate, width, height);
    }

    public void loadImage(String url, int placeholder, ImageView imageView, int animate, int width, int height, int decodeFormat) {
        mStrategy.loadImage(imageView.getContext(), url, placeholder, imageView, animate, width, height, decodeFormat);
    }

    public void loadGifImage(String url, int placeholder, ImageView imageView) {
        mStrategy.loadGifImage(url, placeholder, imageView);
    }

    public void loadCircleBorderImage(String url, int placeholder, ImageView imageView, float borderWidth, int borderColor, int widthPX, int heightPX) {
        mStrategy.loadCircleBorderImage(url, placeholder, imageView, borderWidth, borderColor, heightPX, widthPX);
    }

    public void loadImage(String url, ImageView imageView) {
        mStrategy.loadImage(url, imageView);
    }

    public void preloadImage(Context context, String url, int width, int height) {
        mStrategy.preloadImage(context, url);
    }

    public void loadImageWithAppCxt(String url, ImageView imageView) {
        mStrategy.loadImageWithAppCxt(url, imageView);
    }

    public void loadImageWithProgress(String url, ImageView imageView, ProgressLoadListener listener) {
        mStrategy.loadImageWithProgress(url, imageView, listener);
    }

    public void loadGifWithPrepareCall(String url, ImageView imageView, SourceReadyListener listener) {
        mStrategy.loadGifWithPrepareCall(url, imageView, listener);
    }

    public void loadImageWithPrepareCall(String url, ImageView imageView, int placeholder, int width, int height, SourceReadyListener listener) {
        mStrategy.loadImageWithPrepareCall(url, imageView, placeholder, listener);
    }

    public void loadImageSource(Context context, String url, int width, int height, ILoadPictureResource listener) {
        mStrategy.loadImageSource(context, url, listener);
    }

    /**
     * 策略模式的注入操作
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
    }

    /*
      需要展示图片加载进度的请参考 GalleryAdapter
      样例如下所示
     */

    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(final Context context) {
        mStrategy.clearImageDiskCache(context);
    }

    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache(Context context) {
        mStrategy.clearImageMemoryCache(context);
    }

    /**
     * 根据不同的内存状态，来响应不同的内存释放策略
     */
    public void trimMemory(Context context, int level) {
        mStrategy.trimMemory(context, level);
    }

    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        clearImageDiskCache(context.getApplicationContext());
        clearImageMemoryCache(context.getApplicationContext());
    }

    /**
     * 获取缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        return mStrategy.getCacheSize(context);
    }

    public void saveImage(Context context, String url, String savePath, String saveFileName, ImageSaveListener listener) {
        mStrategy.saveImage(context, url, savePath, saveFileName, listener);
    }

    /**
     * 清理内存缓存
     *
     * @param context
     */
    public void clearCacheMemory(Context context) {
        mStrategy.clearImageMemoryCache(context);
    }

    /**
     * 清理磁盘缓存
     *
     * @param context
     * @return
     */
    public void clearDiskCache(Context context) {
        mStrategy.clearImageDiskCache(context);
    }
}
