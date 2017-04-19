package com.jess.arms.widget.imageloader;

import android.content.Context;

/**
 * Created by jess on 8/5/16 15:50
 * contact with jess.yan.effort@gmail.com
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    void loadImage(Context ctx, T config);
    void clear(Context ctx, T config);
}
