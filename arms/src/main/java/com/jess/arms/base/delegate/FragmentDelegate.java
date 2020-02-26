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
package com.jess.arms.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * ================================================
 * {@link Fragment} 代理类,用于框架内部在每个 {@link Fragment} 的对应生命周期中插入需要的逻辑
 *
 * @see FragmentDelegateImpl
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.13">FragmentDelegate wiki 官方文档</a>
 * Created by JessYan on 29/04/2017 14:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface FragmentDelegate {
    String FRAGMENT_DELEGATE = "FRAGMENT_DELEGATE";

    void onAttach(@NonNull Context context);

    void onCreate(@Nullable Bundle savedInstanceState);

    void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState);

    void onActivityCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(@NonNull Bundle outState);

    void onDestroyView();

    void onDestroy();

    void onDetach();

    /**
     * Return true if the fragment is currently added to its activity.
     */
    boolean isAdded();
}
