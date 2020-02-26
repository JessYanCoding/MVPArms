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

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.subjects.Subject;

/**
 * ================================================
 * 配合 {@link FragmentLifecycleable} 使用,使 {@link Fragment} 具有 {@link RxLifecycle} 的特性
 * <p>
 * Created by JessYan on 26/08/2017 16:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Singleton
public class FragmentLifecycleForRxLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    @Inject
    public FragmentLifecycleForRxLifecycle() {
    }

    @Override
    public void onFragmentAttached(@NotNull @NonNull FragmentManager fm, @NotNull @NonNull Fragment f, @NotNull @NonNull Context context) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.ATTACH);
        }
    }

    @Override
    public void onFragmentCreated(@NotNull @NonNull FragmentManager fm, @NotNull @NonNull Fragment f, Bundle savedInstanceState) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.CREATE);
        }
    }

    @Override
    public void onFragmentViewCreated(@NotNull @NonNull FragmentManager fm, @NotNull @NonNull Fragment f, @NotNull @NonNull View v, Bundle savedInstanceState) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.CREATE_VIEW);
        }
    }

    @Override
    public void onFragmentStarted(@NotNull FragmentManager fm, @NotNull Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.START);
        }
    }

    @Override
    public void onFragmentResumed(@NotNull FragmentManager fm, @NotNull Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.RESUME);
        }
    }

    @Override
    public void onFragmentPaused(@NotNull FragmentManager fm, @NotNull Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.PAUSE);
        }
    }

    @Override
    public void onFragmentStopped(@NotNull FragmentManager fm, @NotNull Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.STOP);
        }
    }

    @Override
    public void onFragmentViewDestroyed(@NotNull FragmentManager fm, @NotNull Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.DESTROY_VIEW);
        }
    }

    @Override
    public void onFragmentDestroyed(@NotNull FragmentManager fm, @NotNull Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.DESTROY);
        }
    }

    @Override
    public void onFragmentDetached(@NotNull FragmentManager fm, @NotNull Fragment f) {
        if (f instanceof FragmentLifecycleable) {
            obtainSubject(f).onNext(FragmentEvent.DETACH);
        }
    }

    private Subject<FragmentEvent> obtainSubject(Fragment fragment) {
        return ((FragmentLifecycleable) fragment).provideLifecycleSubject();
    }
}
