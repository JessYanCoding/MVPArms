/*
 * Copyright 2018 JessYan
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
package com.jess.arms.integration.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.utils.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ================================================
 * {@link IntelligentCache} 含有可将数据永久存储至内存中的存储容器 {@link #mMap}, 和当达到最大容量时可根据 LRU
 * 算法抛弃不合规数据的存储容器 {@link #mCache}
 * <p>
 * {@link IntelligentCache} 可根据您传入的 {@code key} 智能的判断您需要将数据存储至哪个存储容器, 从而针对数据
 * 的不同特性进行不同的存储优化
 * <p>
 * 调用 {@link IntelligentCache#put(Object, Object)} 方法, 使用 {@link #KEY_KEEP} + {@code key} 作为 key 传入的
 * {@code value} 可存储至 {@link #mMap} (数据永久存储至内存中, 适合比较重要的数据) 中, 否则储存至 {@link #mCache}
 * <p>
 * Created by JessYan on 12/04/2018 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class IntelligentCache<V> implements Cache<String, V> {
    public static final String KEY_KEEP = "Keep=";
    private final Map<String, V> mMap;//可将数据永久存储至内存中的存储容器
    private final Cache<String, V> mCache;//当达到最大容量时可根据 LRU 算法抛弃不合规数据的存储容器

    public IntelligentCache(int size) {
        this.mMap = new HashMap<>();
        this.mCache = new LruCache<>(size);
    }

    /**
     * 使用此方法返回的值作为 key, 可以将数据永久存储至内存中
     *
     * @param key {@code key}
     * @return Keep= + {@code key}
     */
    @NonNull
    public static String getKeyOfKeep(@NonNull String key) {
        Preconditions.checkNotNull(key, "key == null");
        return IntelligentCache.KEY_KEEP + key;
    }

    /**
     * 将 {@link #mMap} 和 {@link #mCache} 的 {@code size} 相加后返回
     *
     * @return 相加后的 {@code size}
     */
    @Override
    public synchronized int size() {
        return mMap.size() + mCache.size();
    }

    /**
     * 将 {@link #mMap} 和 {@link #mCache} 的 {@code maxSize} 相加后返回
     *
     * @return 相加后的 {@code maxSize}
     */
    @Override
    public synchronized int getMaxSize() {
        return mMap.size() + mCache.getMaxSize();
    }

    /**
     * 如果在 {@code key} 中使用 {@link #KEY_KEEP} 作为其前缀, 则操作 {@link #mMap}, 否则操作 {@link #mCache}
     *
     * @param key {@code key}
     * @return {@code value}
     */
    @Nullable
    @Override
    public synchronized V get(String key) {
        if (key.startsWith(KEY_KEEP)) {
            return mMap.get(key);
        }
        return mCache.get(key);
    }

    /**
     * 如果在 {@code key} 中使用 {@link #KEY_KEEP} 作为其前缀, 则操作 {@link #mMap}, 否则操作 {@link #mCache}
     *
     * @param key   {@code key}
     * @param value {@code value}
     * @return 如果这个 {@code key} 在容器中已经储存有 {@code value}, 则返回之前的 {@code value} 否则返回 {@code null}
     */
    @Nullable
    @Override
    public synchronized V put(String key, V value) {
        if (key.startsWith(KEY_KEEP)) {
            return mMap.put(key, value);
        }
        return mCache.put(key, value);
    }

    /**
     * 如果在 {@code key} 中使用 {@link #KEY_KEEP} 作为其前缀, 则操作 {@link #mMap}, 否则操作 {@link #mCache}
     *
     * @param key {@code key}
     * @return 如果这个 {@code key} 在容器中已经储存有 {@code value} 并且删除成功则返回删除的 {@code value}, 否则返回 {@code null}
     */
    @Nullable
    @Override
    public synchronized V remove(String key) {
        if (key.startsWith(KEY_KEEP)) {
            return mMap.remove(key);
        }
        return mCache.remove(key);
    }

    /**
     * 如果在 {@code key} 中使用 {@link #KEY_KEEP} 作为其前缀, 则操作 {@link #mMap}, 否则操作 {@link #mCache}
     *
     * @param key {@code key}
     * @return {@code true} 为在容器中含有这个 {@code key}, 否则为 {@code false}
     */
    @Override
    public synchronized boolean containsKey(String key) {
        if (key.startsWith(KEY_KEEP)) {
            return mMap.containsKey(key);
        }
        return mCache.containsKey(key);
    }

    /**
     * 将 {@link #mMap} 和 {@link #mCache} 的 {@code keySet} 合并返回
     *
     * @return 合并后的 {@code keySet}
     */
    @Override
    public synchronized Set<String> keySet() {
        Set<String> set = mCache.keySet();
        set.addAll(mMap.keySet());
        return set;
    }

    /**
     * 清空 {@link #mMap} 和 {@link #mCache} 容器
     */
    @Override
    public void clear() {
        mCache.clear();
        mMap.clear();
    }
}
