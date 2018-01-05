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
package me.jessyan.mvparms.demo.mvp.model.api.service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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
     * GET 请求
     *
     * @param baseUrl 请求链接
     * @param maps    参数
     * @return 返回对象
     */
    @GET
    Observable<ResponseBody> executeGet(
            @Url String baseUrl,
            @QueryMap Map<String, String> maps);

    /**
     * POST 请求
     *
     * @param baseUrl 请求链接
     * @param maps    参数
     * @return 返回对象
     */
    @POST
    Observable<ResponseBody> executePost(
            @Url String baseUrl,
            //  @Header("") String authorization,
            @QueryMap Map<String, String> maps);

    /**
     * POST 请求
     *
     * @param baseUrl     请求url
     * @param requestBody 请求方法requestBody，可以防止中文乱码问题，可以传图片
     * @return
     */
    @POST
    Observable<ResponseBody> executePost(
            @Url String baseUrl,
            @Body RequestBody requestBody);

    /**
     * POST 请求
     * FormUrlEncoded 注解，防止后台接收会是乱码
     * FieldMap map防止中文乱码
     *
     * @param baseUrl 请求链接
     * @param maps    中文参数
     * @return 返回对象
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> executeEncodePost(
            @Url String baseUrl,
            @FieldMap Map<String, String> maps);

    /**
     * POST 请求
     *
     * @param baseUrl 请求链接
     * @param jsonStr json格式参数
     * @return 返回对象
     */
    @POST
    Observable<ResponseBody> json(
            @Url String baseUrl,
            @Body RequestBody jsonStr);

    /**
     * POST请求 单图片上传
     *
     * @param baseUrl     请求链接
     * @param requestBody 上传图片对象
     * @return 返回对象
     */
    @Multipart
    @POST
    Observable<ResponseBody> upLoadFile(
            @Url String baseUrl,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    /**
     * POST请求 图文参数 混合请求
     *
     * @param baseUrl     请求链接
     * @param headers     请求头信息
     * @param description 文件路径
     * @param maps        参数
     * @return
     */
    @POST
    Call<ResponseBody> uploadFiles(
            @Url String baseUrl,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    /**
     * GET 请求 文件下载
     *
     * @param fileUrl 下载路径
     * @return 返回对象
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
