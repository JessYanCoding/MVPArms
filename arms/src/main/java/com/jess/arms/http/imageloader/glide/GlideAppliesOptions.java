package com.jess.arms.http.imageloader.glide;

import android.content.Context;
import com.bumptech.glide.GlideBuilder;
import com.jess.arms.http.imageloader.BaseImageLoaderStrategy;

/**
 * ================================================
 * 如果你想具有配置 Glide 的权利,则实现 {@link BaseImageLoaderStrategy}
 * 的实现类也必须实现 {@link GlideAppliesOptions}
 *
 * Created by JessYan on 13/08/2017 22:02
 * Contact with jess.yan.effort@gmail.com
 * Follow me on https://github.com/JessYanCoding
 * ================================================
 */

public interface GlideAppliesOptions {

    /**
     * 配置 Glide 的自定义参数,此方法在 Glide 初始化时执行(Glide 在第一次被调用时初始化),只会执行一次
     *
     * @param context
     * @param builder {@link GlideBuilder} 此类被用来创建 Glide
     */
    void applyGlideOptions(Context context, GlideBuilder builder);
}
