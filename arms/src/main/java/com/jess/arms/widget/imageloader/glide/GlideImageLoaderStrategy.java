package com.jess.arms.widget.imageloader.glide;

import android.app.Activity;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jess.arms.widget.imageloader.BaseImageLoaderStrategy;

/**
 * Created by jess on 8/5/16 16:28
 * contact with jess.yan.effort@gmail.com
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {
    @Override
    public void loadImage(Context ctx, GlideImageConfig config) {
        RequestManager manager = null;
        if (ctx instanceof Activity)//如果是activity则可以使用Activity的生命周期
            manager = Glide.with((Activity) ctx);
        else
            manager = Glide.with(ctx);

        manager.load(config.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(config.getImageView());
    }
}
