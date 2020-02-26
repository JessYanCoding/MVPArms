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
package com.jess.arms.integration.cache;

import android.app.Application;

import androidx.annotation.Nullable;

import com.jess.arms.di.module.GlobalConfigModule;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * ================================================
 * LRU 即 Least Recently Used,最近最少使用,也就是说,当缓存满了,会优先淘汰那些最近最不常访问的数据
 * 此种缓存策略为框架默认提供,可自行实现其他缓存策略,如磁盘缓存,为框架或开发者提供缓存的功能
 *
 * @see GlobalConfigModule#provideCacheFactory(Application)
 * @see Cache
 * Created by JessYan on 25/09/2017 16:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class LruCache<K, V> implements Cache<K, V> {
    private final LinkedHashMap<K, V> cache = new LinkedHashMap<>(100, 0.75f, true);
    private final int initialMaxSize;
    private int maxSize;
    private int currentSize = 0;

    /**
     * Constructor for LruCache.
     *
     * @param size 这个缓存的最大 size,这个 size 所使用的单位必须和 {@link #getItemSize(Object)} 所使用的单位一致.
     */
    public LruCache(int size) {
        this.initialMaxSize = size;
        this.maxSize = size;
    }

    /**
     * 设置一个系数应用于当时构造函数中所传入的 size, 从而得到一个新的 {@link #maxSize}
     * 并会立即调用 {@link #evict} 开始清除满足条件的条目
     *
     * @param multiplier 系数
     */
    public synchronized void setSizeMultiplier(float multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
        maxSize = Math.round(initialMaxSize * multiplier);
        evict();
    }

    /**
     * 返回每个 {@code item} 所占用的 size,默认为1,这个 size 的单位必须和构造函数所传入的 size 一致
     * 子类可以重写这个方法以适应不同的单位,比如说 bytes
     *
     * @param item 每个 {@code item} 所占用的 size
     * @return 单个 item 的 {@code size}
     */
    protected int getItemSize(V item) {
        return 1;
    }

    /**
     * 当缓存中有被驱逐的条目时,会回调此方法,默认空实现,子类可以重写这个方法
     *
     * @param key   被驱逐条目的 {@code key}
     * @param value 被驱逐条目的 {@code value}
     */
    protected void onItemEvicted(K key, V value) {
        // optional override
    }

    /**
     * 返回当前缓存所能允许的最大 size
     *
     * @return {@code maxSize}
     */
    @Override
    public synchronized int getMaxSize() {
        return maxSize;
    }

    /**
     * 返回当前缓存已占用的总 size
     *
     * @return {@code size}
     */
    @Override
    public synchronized int size() {
        return currentSize;
    }

    /**
     * 如果这个 {@code key} 在缓存中有对应的 {@code value} 并且不为 {@code null},则返回 true
     *
     * @param key 用来映射的 {@code key}
     * @return {@code true} 为在容器中含有这个 {@code key}, 否则为 {@code false}
     */
    @Override
    public synchronized boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    /**
     * 返回当前缓存中含有的所有 {@code key}
     *
     * @return {@code keySet}
     */
    @Override
    public synchronized Set<K> keySet() {
        return cache.keySet();
    }

    /**
     * 返回这个 {@code key} 在缓存中对应的 {@code value}, 如果返回 {@code null} 说明这个 {@code key} 没有对应的 {@code value}
     *
     * @param key 用来映射的 {@code key}
     * @return {@code value}
     */
    @Override
    @Nullable
    public synchronized V get(K key) {
        return cache.get(key);
    }

    /**
     * 将 {@code key} 和 {@code value} 以条目的形式加入缓存,如果这个 {@code key} 在缓存中已经有对应的 {@code value}
     * 则此 {@code value} 被新的 {@code value} 替换并返回,如果为 {@code null} 说明是一个新条目
     * <p>
     * 如果 {@link #getItemSize} 返回的 size 大于或等于缓存所能允许的最大 size, 则不能向缓存中添加此条目
     * 此时会回调 {@link #onItemEvicted(Object, Object)} 通知此方法当前被驱逐的条目
     *
     * @param key   通过这个 {@code key} 添加条目
     * @param value 需要添加的 {@code value}
     * @return 如果这个 {@code key} 在容器中已经储存有 {@code value}, 则返回之前的 {@code value} 否则返回 {@code null}
     */
    @Override
    @Nullable
    public synchronized V put(K key, V value) {
        final int itemSize = getItemSize(value);
        if (itemSize >= maxSize) {
            onItemEvicted(key, value);
            return null;
        }

        final V result = cache.put(key, value);
        if (value != null) {
            currentSize += getItemSize(value);
        }
        if (result != null) {
            currentSize -= getItemSize(result);
        }
        evict();

        return result;
    }

    /**
     * 移除缓存中这个 {@code key} 所对应的条目,并返回所移除条目的 {@code value}
     * 如果返回为 {@code null} 则有可能时因为这个 {@code key} 对应的 {@code value} 为 {@code null} 或条目不存在
     *
     * @param key 使用这个 {@code key} 移除对应的条目
     * @return 如果这个 {@code key} 在容器中已经储存有 {@code value} 并且删除成功则返回删除的 {@code value}, 否则返回 {@code null}
     */
    @Override
    @Nullable
    public synchronized V remove(K key) {
        final V value = cache.remove(key);
        if (value != null) {
            currentSize -= getItemSize(value);
        }
        return value;
    }

    /**
     * 清除缓存中所有的内容
     */
    @Override
    public void clear() {
        trimToSize(0);
    }

    /**
     * 当指定的 size 小于当前缓存已占用的总 size 时,会开始清除缓存中最近最少使用的条目
     *
     * @param size {@code size}
     */
    protected synchronized void trimToSize(int size) {
        Map.Entry<K, V> last;
        while (currentSize > size) {
            last = cache.entrySet().iterator().next();
            final V toRemove = last.getValue();
            currentSize -= getItemSize(toRemove);
            final K key = last.getKey();
            cache.remove(key);
            onItemEvicted(key, toRemove);
        }
    }

    /**
     * 当缓存中已占用的总 size 大于所能允许的最大 size ,会使用  {@link #trimToSize(int)} 开始清除满足条件的条目
     */
    private void evict() {
        trimToSize(maxSize);
    }
}

