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
package com.jess.arms.base;

/**
 * ================================================
 * Created by JessYan on 2018/7/27 15:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class Platform {
    public static final boolean DEPENDENCY_AUTO_LAYOUT;

    static {
        DEPENDENCY_AUTO_LAYOUT = findAutoLayout();
    }

    private static boolean findAutoLayout() {
        boolean hasDependency;
        try {
            Class.forName("com.zhy.autolayout.AutoLayoutInfo");
            hasDependency = true;
        } catch (ClassNotFoundException e) {
            hasDependency = false;
        }
        return hasDependency;
    }
}
