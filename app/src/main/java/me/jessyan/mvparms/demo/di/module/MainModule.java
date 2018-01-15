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
package me.jessyan.mvparms.demo.di.module;

import android.app.Application;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import me.jessyan.mvparms.demo.mvp.model.entity.ClassDetails;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import me.jessyan.mvparms.demo.mvp.ui.activity.MultiPersenterActivity;
import me.jessyan.mvparms.demo.mvp.ui.activity.UserActivity;
import me.jessyan.mvparms.demo.mvp.ui.adapter.UserAdapter;

/**
 * ================================================
 * 展示 Module 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.5">Module wiki 官方文档</a>
 * Created by Sum41forever on 2018/1/13
 * <a href="http://www.sum41forever.com/">My Blog</a>
 * <a href="https://github.com/sum41forever">My github</a>
 *  ================================================
 */
@Module
public class MainModule {


    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager(Application mApplication) {
        return new GridLayoutManager(mApplication, 2);
    }

    @ActivityScope
    @Provides
    List<ClassDetails> provideDataList() {

        List<ClassDetails> mClassList =  new ArrayList<>();
        //  基本使用
        mClassList.add(new ClassDetails("Quick Use", UserActivity.class));
        //  复用Presenter
        mClassList.add(new ClassDetails("Use Multi Presenter", MultiPersenterActivity.class));

        return mClassList;
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideUserAdapter(List<User> list){
        return new UserAdapter(list);
    }
}
