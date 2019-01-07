# ImageLoaderHelper

>通过阿里云的图片加载API进行加优雅的二次封装

注：如果你不是用阿里云框架加载也没关系，只要你兼容对应的API也可以使用（下文会描述）

### 效果图
![效果图](https://github.com/firejunking/ImageLoaderHelper/blob/master/images/result.png)

### 基础库 (library)
```
public interface BaseImageLoaderStrategy {
    ***
    void loadImage(Context context, String url, int placeholder, ImageView imageView, @AnimatorRes int animate, int widthPx, int heightPx);
    ***
}
```

### 基于Glide封装工具类 (glideutil)

该库基于库与glide，进行上层封装，对接基本库和glide，采用策略模式，根据各自的需要，使用glide、Picasso、Fresco。
```
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {
    ***
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
    ***
}
```

### app项目使用
 - 使用策略模式，对图片显示库进行二次封装，但是很多公司会使用阿里云、七牛等API进行图片显示，这时，无论基础库和Glide工具库都不应该跟业务有任何关联。
 - 在不同的App中根据自己使用的阿里云、七牛再次封装成静态工具类进行使用。
 - 分层为基础库 + 图片工具库 + 业务图片显示类
 - 下面只是简单使用了一部分代码，这里不会让你觉得过度封装，因为使用阿里云方案，会有很多对应的API，这样封装不会让业务与其他库有任何耦合

```
public class AliImageLoaderUtil {
    ***
    public static void loadImage(String imgPath, int placeholder, ImageView imageView) {
        ImageLoaderUtil.getInstance().loadImage(imgPath, placeholder, imageView);
    }
    ***
}
```
### 依赖使用
```
dependencies {
  // 基本库
  implementation 'com.firejun.imageloaderhelper:library:1.0.0'
  // 可以不引用，根据自身项目需要，可当作方案，自行封装
  implementation 'com.firejun.imageloaderhelper:glideutil:1.0.0'
}
```

### 联系方式
QQ:435559203