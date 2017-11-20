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

package com.jess.arms.integration.store.lifecyclemodel;

import android.app.Application;

import com.jess.arms.base.App;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.cache.LruCache;

/**
 * Class to store {@code ViewModels}.
 * <p>
 * An instance of {@code LifecycleModelStore} must be retained through configuration changes:
 * if an owner of this {@code LifecycleModelStore} is destroyed and recreated due to configuration
 * changes, new instance of an owner should still have the same old instance of
 * {@code LifecycleModelStore}.
 * <p>
 * If an owner of this {@code LifecycleModelStore} is destroyed and is not going to be recreated,
 * then it should call {@link #clear()} on this {@code LifecycleModelStore}, so {@code ViewModels} would
 * be notified that they are no longer used.
 * <p>
 * {@link LifecycleModelStores} provides a {@code LifecycleModelStore} for
 * activities and fragments.
 */
public class LifecycleModelStore {

    private Cache<String, LifecycleModel> mCache;

    public LifecycleModelStore(Application application) {
        if (application instanceof App) {
            mCache = ((App) application).getAppComponent().cacheFactory().build(CacheType.ACTIVITY_CACHE);
        } else {
            mCache = new LruCache<>(CacheType.ACTIVITY_CACHE.calculateCacheSize(application));
        }
    }

    public final void put(String key, LifecycleModel lifecycleModel) {
        LifecycleModel oldLifecycleModel = mCache.get(key);
        if (oldLifecycleModel != null) {
            oldLifecycleModel.onCleared();
        }
        mCache.put(key, lifecycleModel);
    }

    public final <T extends LifecycleModel> T get(String key) {
        return (T) mCache.get(key);
    }

    public final <T extends LifecycleModel> T remove(String key) {
        return (T) mCache.remove(key);
    }

    /**
     * Clears internal storage and notifies ViewModels that they are no longer used.
     */
    public final void clear() {
        for (String key : mCache.keySet()) {
            mCache.get(key).onCleared();
        }
        mCache.clear();
    }
}
