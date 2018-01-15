/**
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
package me.jessyan.mvparms.demo.mvp.model.api.service;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseJson;
import me.jessyan.mvparms.demo.mvp.model.entity.Girl;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static me.jessyan.mvparms.demo.mvp.model.api.Api.GANK_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * ================================================
 * 存放通用的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface CommonService {

    /**
     * 如果不需要多个 BaseUrl ,继续使用初始化时传入 Retrofit 中的默认 BaseUrl,就不要加上 DOMAIN_NAME_HEADER 这个 Header
     * @param pageNum  page页数
     * @param pageSize 一页多少条数据
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER + GANK_DOMAIN_NAME})
    @GET("api/data/福利/{number}/{page}")
    Observable<BaseJson<List<Girl>>> getGirlData(@Path("page") int pageNum, @Path("number") int pageSize);
}
