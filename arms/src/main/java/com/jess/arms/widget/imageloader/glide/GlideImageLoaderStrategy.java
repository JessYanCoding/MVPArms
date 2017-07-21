package com.jess.arms.widget.imageloader.glide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.widget.imageloader.BaseImageLoaderStrategy;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jess on 8/5/16 16:28
 * contact with jess.yan.effort@gmail.com
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {

    @Override
    public void loadImage(Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new IllegalStateException("Context is required");
        if (config == null) throw new IllegalStateException("GlideImageConfig is required");
        if (TextUtils.isEmpty(config.getUrl())) throw new IllegalStateException("Url is required");
        if (config.getImageView() == null) throw new IllegalStateException("Imageview is required");


        RequestManager manager;

        manager = Glide.with(ctx);//如果context是activity则自动使用Activity的生命周期

        DrawableRequestBuilder<String> requestBuilder = manager.load(config.getUrl())
                .crossFade()
                .centerCrop();

        switch (config.getCacheStrategy()) {//缓存策略
            case 0:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
                break;
            case 3:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.RESULT);
                break;
        }

        if (config.getTransformation() != null) {//glide用它来改变图形的形状
            requestBuilder.transform(config.getTransformation());
        }


        if (config.getPlaceholder() != 0)//设置占位符
            requestBuilder.placeholder(config.getPlaceholder());

        if (config.getErrorPic() != 0)//设置错误的图片
            requestBuilder.error(config.getErrorPic());

        requestBuilder
                .into(config.getImageView());
    }

    @Override
    public void clear(final Context ctx, GlideImageConfig config) {
        if (ctx == null) throw new IllegalStateException("Context is required");
        if (config == null) throw new IllegalStateException("GlideImageConfig is required");

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                Glide.clear(imageView);
            }
        }

        if (config.getTargets() != null && config.getTargets().length > 0) {//取消在执行的任务并且释放资源
            for (Target target : config.getTargets())
                Glide.clear(target);
        }


        if (config.isClearDiskCache()) {//清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            Glide.get(ctx).clearDiskCache();
                        }
                    });
        }

        if (config.isClearMemory()) {//清除内存缓存
            Glide.get(ctx).clearMemory();
        }


    }
}
