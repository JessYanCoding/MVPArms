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
package com.jess.arms.integration;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jess.arms.base.delegate.FragmentDelegate;
import com.jess.arms.base.delegate.FragmentDelegateImpl;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.Preconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * ================================================
 * {@link FragmentManager.FragmentLifecycleCallbacks} 默认实现类
 * 通过 {@link FragmentDelegate} 管理 {@link Fragment}
 * <p>
 * Created by JessYan on 04/09/2017 16:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Singleton
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    @Inject
    public FragmentLifecycle() {
    }

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        Timber.w(f.toString() + " - onFragmentAttached");
        if (f instanceof IFragment) {
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {
                Cache<String, Object> cache = getCacheFromFragment((IFragment) f);
                fragmentDelegate = new FragmentDelegateImpl(fm, f);
                cache.put(FragmentDelegate.FRAGMENT_DELEGATE, fragmentDelegate);
            }
            fragmentDelegate.onAttach(context);
        }
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Timber.w(f.toString() + " - onFragmentCreated");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        Timber.w(f.toString() + " - onFragmentViewCreated");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreateView(v, savedInstanceState);
        }
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Timber.w(f.toString() + " - onFragmentActivityCreated");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onActivityCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentStarted");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStart();
        }
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentResumed");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onResume();
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentPaused");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onPause();
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentStopped");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStop();
        }
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        Timber.w(f.toString() + " - onFragmentSaveInstanceState");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentViewDestroyed");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroyView();
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentDestroyed");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroy();
        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentDetached");
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDetach();
        }
    }

    private FragmentDelegate fetchFragmentDelegate(Fragment fragment) {
        if (fragment instanceof IFragment) {
            Cache<String, Object> cache = getCacheFromFragment((IFragment) fragment);
            return (FragmentDelegate) cache.get(FragmentDelegate.FRAGMENT_DELEGATE);
        }
        return null;
    }

    @NonNull
    private Cache<String, Object> getCacheFromFragment(IFragment fragment) {
        Cache<String, Object> cache = fragment.provideCache();
        Preconditions.checkNotNull(cache, "%s cannot be null on Fragment", Cache.class.getName());
        return cache;
    }

}
