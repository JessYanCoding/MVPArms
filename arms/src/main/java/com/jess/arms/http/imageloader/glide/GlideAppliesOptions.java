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
