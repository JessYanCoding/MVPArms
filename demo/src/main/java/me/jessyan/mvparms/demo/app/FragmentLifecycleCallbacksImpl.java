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
package me.jessyan.mvparms.demo.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link FragmentManager.FragmentLifecycleCallbacks} 的用法
 * <p>
 * Created by JessYan on 23/08/2018 17:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class FragmentLifecycleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentAttached(@NotNull @NonNull FragmentManager fm, Fragment f, @NotNull Context context) {
        Timber.i("%s - onFragmentAttached", f.toString());
    }

    @Override
    public void onFragmentCreated(@NotNull FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Timber.i("%s - onFragmentCreated", f.toString());
        // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建时重复利用已经创建的 Fragment。
        // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
        // 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
        // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
        f.setRetainInstance(true);
    }

    @Override
    public void onFragmentViewCreated(@NotNull FragmentManager fm, Fragment f, @NotNull View v, Bundle savedInstanceState) {
        Timber.i("%s - onFragmentViewCreated", f.toString());
    }

    @Override
    public void onFragmentActivityCreated(@NotNull FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Timber.i("%s - onFragmentActivityCreated", f.toString());
    }

    @Override
    public void onFragmentStarted(@NotNull FragmentManager fm, Fragment f) {
        Timber.i("%s - onFragmentStarted", f.toString());
    }

    @Override
    public void onFragmentResumed(@NotNull FragmentManager fm, Fragment f) {
        Timber.i("%s - onFragmentResumed", f.toString());
    }

    @Override
    public void onFragmentPaused(@NotNull FragmentManager fm, Fragment f) {
        Timber.i("%s - onFragmentPaused", f.toString());
    }

    @Override
    public void onFragmentStopped(@NotNull FragmentManager fm, Fragment f) {
        Timber.i("%s - onFragmentStopped", f.toString());
    }

    @Override
    public void onFragmentSaveInstanceState(@NotNull FragmentManager fm, Fragment f, @NotNull Bundle outState) {
        Timber.i("%s - onFragmentSaveInstanceState", f.toString());
    }

    @Override
    public void onFragmentViewDestroyed(@NotNull FragmentManager fm, Fragment f) {
        Timber.i("%s - onFragmentViewDestroyed", f.toString());
    }

    @Override
    public void onFragmentDestroyed(@NotNull FragmentManager fm, Fragment f) {
        Timber.i("%s - onFragmentDestroyed", f.toString());
    }

    @Override
    public void onFragmentDetached(@NotNull FragmentManager fm, Fragment f) {
        Timber.i("%s - onFragmentDetached", f.toString());
    }
}
