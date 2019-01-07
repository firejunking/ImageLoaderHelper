package com.firejun.ImageLoaderhelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.firejun.ImageLoaderhelper.listener.ProgressModelLoader;
import com.firejun.ImageLoaderhelper.transformation.GlideCircleTransform;
import com.firejun.imageloaderhelper.BaseImageLoaderStrategy;
import com.firejun.imageloaderhelper.BitmapFormat;
import com.firejun.imageloaderhelper.listener.ILoadPictureResource;
import com.firejun.imageloaderhelper.listener.ImageSaveListener;
import com.firejun.imageloaderhelper.listener.ProgressLoadListener;
import com.firejun.imageloaderhelper.listener.ProgressUIListener;
import com.firejun.imageloaderhelper.listener.SourceReadyListener;
import com.firejun.imageloaderhelper.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {
    @Override
    public void loadImage(String url, ImageView imageView) {
        loadNormal(imageView.getContext(), url, -1, imageView, -1, -1, -1, BitmapFormat.RGB_565);
    }

    @Override
    public void loadImageWithAppCxt(String url, ImageView imageView) {
        Glide.with(imageView.getContext().getApplicationContext()).load(url)
                .placeholder(imageView.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    @Override
    public void loadImage(String url, int placeholder, ImageView imageView) {
        loadNormal(imageView.getContext(), url, placeholder, imageView, -1, -1, -1, BitmapFormat.RGB_565);
    }

    @Override
    public void loadImage(Context context, String url, int placeholder, ImageView imageView) {
        loadNormal(context, url, placeholder, imageView, -1, -1, -1, BitmapFormat.RGB_565);
    }

    @Override
    public void loadImage(Context context, String url, ImageView imageView, int widthPx, int heightPx) {
        loadNormal(context, url, -1, imageView, -1, widthPx, heightPx, BitmapFormat.RGB_565);
    }

    @Override
    public void loadImage(Context context, String url, int placeholder, ImageView imageView, int widthPx, int heightPx) {
        loadNormal(imageView.getContext(), url, placeholder, imageView, -1, widthPx, heightPx, BitmapFormat.RGB_565);
    }

    @Override
    public void loadImage(Context context, String url, int placeholder, ImageView imageView, @AnimatorRes int animate, int widthPx, int heightPx) {
        loadNormal(imageView.getContext(), url, placeholder, imageView, animate, widthPx, heightPx, BitmapFormat.RGB_565);
    }

    @Override
    public void loadImage(Context context, String url, int placeholder, ImageView imageView, int animate, int widthPx, int heightPx, int decodeFormat) {
        loadNormal(imageView.getContext(), url, placeholder, imageView, animate, widthPx, heightPx, decodeFormat);
    }

    @Override
    public void loadCircleBorderImage(String url, int placeholder, ImageView imageView, float borderWidth, int borderColor, int heightPx, int widthPx) {
        Glide.with(imageView.getContext()).load(url).placeholder(placeholder).dontAnimate()
                .transform(new GlideCircleTransform(imageView.getContext(), borderWidth, borderColor, heightPx, widthPx))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    @Override
    public void loadGifImage(String url, int placeholder, ImageView imageView) {
        loadGif(imageView.getContext(), url, placeholder, imageView);
    }

    @Override
    public void loadImageWithProgress(String url, final ImageView imageView, final ProgressLoadListener listener) {
        Glide.with(imageView.getContext()).using(new ProgressModelLoader(new ProgressUIListener() {
            @Override
            public void update(final int bytesRead, final int contentLength) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.update(bytesRead, contentLength);
                    }
                });
            }
        })).load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        listener.onException();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        listener.onResourceReady();
                        return false;
                    }
                }).into(imageView);
    }

    @Override
    public void loadImageWithPrepareCall(String url, ImageView imageView, int placeholder, final SourceReadyListener listener) {
        Glide.with(imageView.getContext()).load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(placeholder)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        listener.onException(e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        listener.onResourceReady(resource);
                        return false;
                    }
                }).into(imageView);
    }

    @Override
    public void loadGifWithPrepareCall(String url, ImageView imageView, final SourceReadyListener listener) {
        Glide.with(imageView.getContext()).load(url).asGif()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).
                listener(new RequestListener<String, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        listener.onResourceReady(resource);
                        return false;
                    }
                }).into(imageView);
    }

    @Override
    public void preloadImage(Context context, String url) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).preload();
    }

    @Override
    public void loadImageSource(Context context, String url, @NonNull final ILoadPictureResource listener) {
        Glide.with(context).load(url)
                .asBitmap()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        listener.onResourceReady(resource);
                    }
                });
    }

    @Override
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context.getApplicationContext()).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trimMemory(Context context, int level) {
        Glide.get(context).trimMemory(level);
    }

    @Override
    public String getCacheSize(Context context) {
        try {
            return Utils.getFormatSize(Utils.getFolderSize(Glide.getPhotoCacheDir(context.getApplicationContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void saveImage(Context context, String url, String savePath, String saveFileName, ImageSaveListener listener) {
        if (!Utils.isSDCardExist() || TextUtils.isEmpty(url)) {
            listener.onSaveFail();
            return;
        }
        InputStream fromStream = null;
        OutputStream toStream = null;
        try {
            File cacheFile = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            if (cacheFile == null || !cacheFile.exists()) {
                listener.onSaveFail();
                return;
            }
            File dir = new File(savePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, saveFileName + Utils.getPicType(cacheFile.getAbsolutePath()));

            fromStream = new FileInputStream(cacheFile);
            toStream = new FileOutputStream(file);
            byte[] length = new byte[1024];
            int count;
            while ((count = fromStream.read(length)) > 0) {
                toStream.write(length, 0, count);
            }
            //用广播通知相册进行更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = getUri(file, context);
            intent.setData(uri);
            context.sendBroadcast(intent);
            listener.onSaveSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onSaveFail();
        } finally {
            if (fromStream != null) {
                try {
                    fromStream.close();
                    toStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    fromStream = null;
                    toStream = null;
                }
            }
        }
    }

    private Uri getUri(File file, Context context) {
        Uri uri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(file);
        } else {
            uri = FileProvider.getUriForFile(context, Utils.getFileProviderName(context), file);
        }
        return uri;
    }

    /**
     * load image with Glide
     */
    private void loadNormal(final Context ctx, final String url, @DrawableRes int placeHolder, ImageView imageView,
                            @AnimatorRes int animate, int width, int height, int decodeFormat) {
        if (null != imageView) {
            BitmapRequestBuilder<String, Bitmap> builder = Glide.with(ctx).
                    load(url)
                    .asBitmap()
                    .encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 100));
            if (placeHolder != -1) {
                builder.placeholder(placeHolder);
                builder.error(placeHolder);
            }
            builder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
            if (animate != -1) {
                builder.animate(animate);
            } else {
                builder.dontAnimate();
            }
            if (width > 0 && height > 0) {
                builder.override(width, height);
            }
            if (decodeFormat == BitmapFormat.ARGB_8888) {
                builder.format(DecodeFormat.PREFER_ARGB_8888);
            } else {
                builder.format(DecodeFormat.DEFAULT);
            }
            builder.into(imageView);
        }
    }

    /**
     * load image with Glide
     */
    private void loadGif(final Context ctx, String url, int placeholder, ImageView imageView) {
        final long startTime = System.currentTimeMillis();
        Glide.with(ctx).load(url).asGif()
                .placeholder(placeholder).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GifDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        })
                .into(imageView);
    }

}
