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

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.module.GlobalConfigModule;

import java.util.Set;


/**
 * ================================================
 * 用于缓存框架中所必需的组件,开发者可通过 {@link GlobalConfigModule.Builder#cacheFactory(Factory)} 为框架提供缓存策略
 * 开发者也可以用于自己日常中的使用
 *
 * @see GlobalConfigModule#provideCacheFactory(Application)
 * @see LruCache
 * Created by JessYan on 25/09/2017 16:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Cache<K, V> {
    interface Factory {

        /**
         * Returns a new cache
         *
         * @param type 框架中需要缓存的模块类型
         * @return
         */
        @NonNull
        Cache build(CacheType type);
    }

    /**
     * 返回当前缓存已占用的总 size
     *
     * @return
     */
    int size();

    /**
     * 返回当前缓存所能允许的最大 size
     *
     * @return
     */
    int getMaxSize();

    /**
     * 返回这个 {@code key} 在缓存中对应的 {@code value}, 如果返回 {@code null} 说明这个 {@code key} 没有对应的 {@code value}
     *
     * @param key
     * @return
     */
    @Nullable
    V get(K key);

    /**
     * 将 {@code key} 和 {@code value} 以条目的形式加入缓存,如果这个 {@code key} 在缓存中已经有对应的 {@code value}
     * 则此 {@code value} 被新的 {@code value} 替换并返回,如果为 {@code null} 说明是一个新条目
     *
     * @param key
     * @param value
     * @return
     */
    @Nullable
    V put(K key, V value);

    /**
     * 移除缓存中这个 {@code key} 所对应的条目,并返回所移除条目的 value
     * 如果返回为 {@code null} 则有可能时因为这个 {@code key} 对应的 value 为 {@code null} 或条目不存在
     *
     * @param key
     * @return
     */
    @Nullable
    V remove(K key);

    /**
     * 如果这个 {@code key} 在缓存中有对应的 value 并且不为 {@code null}, 则返回 {@code true}
     *
     * @param key
     * @return
     */
    boolean containsKey(K key);

    /**
     * 返回当前缓存中含有的所有 {@code key}
     *
     * @return
     */
    Set<K> keySet();

    /**
     * 清除缓存中所有的内容
     */
    void clear();
}
