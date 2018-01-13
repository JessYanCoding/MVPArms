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

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.jessyan.mvparms.demo.di.scope.GirlScope;
import me.jessyan.mvparms.demo.mvp.contract.GirlContract;
import me.jessyan.mvparms.demo.mvp.model.GirlModel;
import me.jessyan.mvparms.demo.mvp.model.entity.Girl;
import me.jessyan.mvparms.demo.mvp.ui.adapter.GirlAdapter;

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
public class GirlModule {
    private GirlContract.View view;

    /**
     * 构建 TestModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public GirlModule(GirlContract.View view) {
        this.view = view;
    }

    // 注意Scope不同
    @GirlScope
    @Provides
    GirlContract.View provideTestView() {
        return this.view;
    }

    @GirlScope
    @Provides
    GirlContract.Model provideGirlModel(GirlModel model) {
        return model;
    }


    @GirlScope
    @Provides
    List<Girl> provideGirlList() {
        return new ArrayList<>();
    }

    /**
     * 这里要加Named不然注入的时候Dagger不知道要注入什么类型
     * @see me.jessyan.mvparms.demo.mvp.ui.activity.UserActivity mGirlAdapter
     * @param list
     * @return
     */
    @GirlScope
    @Named("girl")
    @Provides
    RecyclerView.Adapter provideGirlAdapter(List<Girl> list){
        return new GirlAdapter(list);
    }
}
