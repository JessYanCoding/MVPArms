/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.http.imageloader.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.jess.arms.http.imageloader.BaseImageLoaderStrategy;

/**
 * ================================================
 * 如果你想具有配置 @{@link Glide} 的权利,则需要让 {@link BaseImageLoaderStrategy}
 * 的实现类也必须实现 {@link GlideAppliesOptions}
 * <p>
 * Created by JessYan on 13/08/2017 22:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface GlideAppliesOptions {

    /**
     * 配置 @{@link Glide} 的自定义参数,此方法在 @{@link Glide} 初始化时执行(@{@link Glide} 在第一次被调用时初始化),只会执行一次
     *
     * @param context
     * @param builder {@link GlideBuilder} 此类被用来创建 Glide
     */
    void applyGlideOptions(@NonNull Context context, @NonNull GlideBuilder builder);

    /**
     * 注册{@link Glide}的组件，参考{@link com.bumptech.glide.module.LibraryGlideModule}
     *
     * @param context  Android context
     * @param glide    {@link Glide}
     * @param registry {@link Registry}
     */
    void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry);
}
