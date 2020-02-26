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
package com.jess.arms.integration.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.reactivex.subjects.Subject;

/**
 * ================================================
 * 配合 {@link ActivityLifecycleable} 使用,使 {@link Activity} 具有 {@link RxLifecycle} 的特性
 * <p>
 * Created by JessYan on 25/08/2017 18:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Singleton
public class ActivityLifecycleForRxLifecycle implements Application.ActivityLifecycleCallbacks {
    @Inject
    Lazy<FragmentLifecycleForRxLifecycle> mFragmentLifecycle;

    @Inject
    public ActivityLifecycleForRxLifecycle() {
    }

    /**
     * 通过桥梁对象 {@code BehaviorSubject<ActivityEvent> mLifecycleSubject}
     * 在每个 Activity 的生命周期中发出对应的生命周期事件
     */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.CREATE);
            if (activity instanceof FragmentActivity) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle.get(), true);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.START);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.RESUME);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.PAUSE);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.STOP);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.DESTROY);
        }
    }

    /**
     * 从 {@link com.jess.arms.base.BaseActivity} 中获得桥梁对象 {@code BehaviorSubject<ActivityEvent> mLifecycleSubject}
     *
     * @see <a href="https://mcxiaoke.gitbooks.io/rxdocs/content/Subject.html">BehaviorSubject 官方中文文档</a>
     */
    private Subject<ActivityEvent> obtainSubject(Activity activity) {
        return ((ActivityLifecycleable) activity).provideLifecycleSubject();
    }
}
