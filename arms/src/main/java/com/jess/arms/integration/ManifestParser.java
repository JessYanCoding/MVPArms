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
package com.jess.arms.integration;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 用于解析 AndroidManifest 中的 Meta 属性
 * 配合 {@link ConfigModule} 使用
 * <p>
 * Created by JessYan on 12/04/2017 14:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public final class ManifestParser {
    private static final String MODULE_VALUE = "ARMS_MODULE_CONFIG";

    private final Context context;

    public ManifestParser(Context context) {
        this.context = context;
    }

    public List<ConfigModule> parse() {
        List<ConfigModule> modules = new ArrayList<ConfigModule>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if(MODULE_VALUE.equals(key)) {
                        try {
                            String className = appInfo.metaData.get(key).toString();
                            if(!TextUtils.isEmpty(className)) {
                                Class<?> clazz = Class.forName(className);
                                for(Class clazzInter : clazz.getInterfaces()) {
                                    if("com.jess.arms.integration.ConfigModule".equals(clazzInter.getName())) {
                                        modules.add(parseModule(clazz));
                                    }
                                }
                            } else {
                                Log.w("ManifestParser", "module config value cannot be null in AndroidManifest,xml");
                            }
                        } catch (ClassNotFoundException e) {
                            Log.w("ManifestParser", "Unable to find module config class");
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse ConfigModule", e);
        }

        return modules;
    }

    private static ConfigModule parseModule(Class<?> clazz) {

        Object module;
        try {
            module = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate ConfigModule implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate ConfigModule implementation for " + clazz, e);
        }

        return (ConfigModule) module;
    }
}