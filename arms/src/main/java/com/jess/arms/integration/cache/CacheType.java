/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.integration.cache;

import android.app.ActivityManager;
import android.content.Context;

/**
 * ================================================
 * 构建 {@link Cache} 时,使用 {@link CacheType} 中声明的类型,来区分不同的模块
 * 从而为不同的模块构建不同的缓存策略
 *
 * @see Cache.Factory#build(CacheType)
 * Created by JessYan on 25/09/2017 18:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface CacheType {
    int RETROFIT_SERVICE_CACHE_TYPE_ID = 0;
    int CACHE_SERVICE_CACHE_TYPE_ID = 1;
    int EXTRAS_TYPE_ID = 2;
    /**
     * RepositoryManager 中存储 Retrofit Service 的容器
     */
    CacheType RETROFIT_SERVICE_CACHE = new CacheType() {
        private static final int MAX_SIZE = 150;
        private static final float MAX_SIZE_MULTIPLIER = 0.002f;

        @Override
        public int getCacheTypeId() {
            return RETROFIT_SERVICE_CACHE_TYPE_ID;
        }

        @Override
        public int calculateCacheSize(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int targetMemoryCacheSize = (int) (activityManager.getMemoryClass() * MAX_SIZE_MULTIPLIER * 1024);
            if (targetMemoryCacheSize >= MAX_SIZE){
                return MAX_SIZE;
            }
            return targetMemoryCacheSize;
        }
    };

    /**
     * RepositoryManager 中储存 Cache Service 的容器
     */
    CacheType CACHE_SERVICE_CACHE = new CacheType() {
        private static final int MAX_SIZE = 150;
        private static final float MAX_SIZE_MULTIPLIER = 0.002f;

        @Override
        public int getCacheTypeId() {
            return CACHE_SERVICE_CACHE_TYPE_ID;
        }

        @Override
        public int calculateCacheSize(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int targetMemoryCacheSize = (int) (activityManager.getMemoryClass() * MAX_SIZE_MULTIPLIER * 1024);
            if (targetMemoryCacheSize >= MAX_SIZE){
                return MAX_SIZE;
            }
            return targetMemoryCacheSize;
        }
    };

    /**
     * AppComponent 中的 extras
     */
    CacheType EXTRAS = new CacheType() {
        private static final int MAX_SIZE = 500;
        private static final float MAX_SIZE_MULTIPLIER = 0.005f;

        @Override
        public int getCacheTypeId() {
            return EXTRAS_TYPE_ID;
        }

        @Override
        public int calculateCacheSize(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int targetMemoryCacheSize = (int) (activityManager.getMemoryClass() * MAX_SIZE_MULTIPLIER * 1024);
            if (targetMemoryCacheSize >= MAX_SIZE){
                return MAX_SIZE;
            }
            return targetMemoryCacheSize;
        }
    };

    /**
     * 返回框架内需要缓存的模块对应的 {@code id}
     *
     * @return
     */
    int getCacheTypeId();

    /**
     * 计算对应模块需要的缓存大小
     *
     * @return
     */
    int calculateCacheSize(Context context);
}
