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
package me.jessyan.mvparms.demo.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseJson;
import me.jessyan.mvparms.demo.mvp.model.entity.Girl;

/**
 * ================================================
 * 展示 Contract 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.1">Contract wiki 官方文档</a>
 * Created by Sum41forever on 2018/1/13
 * <a href="http://www.sum41forever.com/">My Blog</a>
 * <a href="https://github.com/sum41forever">My github</a>
 * ================================================
 */
public interface GirlContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void startLoadMore();

        void endLoadMore();

        Activity getActivity();

        //申请权限
        RxPermissions getRxPermissions();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {
        Observable<BaseJson<List<Girl>>> getGirls(int pageNum, boolean update);
    }
}
