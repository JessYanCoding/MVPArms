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

import android.graphics.Bitmap;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.jess.arms.utils.FastBlur;

import java.security.MessageDigest;

/**
 * ================================================
 * 高斯模糊
 * <p>
 * Created by JessYan on 03/04/2018 15:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class BlurTransformation extends BitmapTransformation {
    public static final int DEFAULT_RADIUS = 15;
    private static final String ID = BlurTransformation.class.getName();
    private static final byte[] ID_BYTES = ID.getBytes(Key.CHARSET);
    private int mRadius;

    public BlurTransformation(@IntRange(from = 0) int radius) {
        mRadius = radius;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return FastBlur.doBlur(toTransform, mRadius, true);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BlurTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
