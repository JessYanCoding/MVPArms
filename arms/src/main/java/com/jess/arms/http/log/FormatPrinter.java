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
package com.jess.arms.http.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.module.GlobalConfigModule;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;

/**
 * ================================================
 * 对 OkHttp 的请求和响应信息进行更规范和清晰的打印, 开发者可更根据自己的需求自行扩展打印格式
 *
 * @see DefaultFormatPrinter
 * @see GlobalConfigModule.Builder#formatPrinter(FormatPrinter)
 * Created by JessYan on 31/01/2018 17:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

public interface FormatPrinter {

    /**
     * 打印网络请求信息, 当网络请求时 {{@link okhttp3.RequestBody}} 可以解析的情况
     *
     * @param request
     * @param bodyString 发送给服务器的请求体中的数据(已解析)
     */
    void printJsonRequest(@NonNull Request request, @NonNull String bodyString);

    /**
     * 打印网络请求信息, 当网络请求时 {{@link okhttp3.RequestBody}} 为 {@code null} 或不可解析的情况
     *
     * @param request
     */
    void printFileRequest(@NonNull Request request);

    /**
     * 打印网络响应信息, 当网络响应时 {{@link okhttp3.ResponseBody}} 可以解析的情况
     *
     * @param chainMs      服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code         响应码
     * @param headers      请求头
     * @param contentType  服务器返回数据的数据类型
     * @param bodyString   服务器返回的数据(已解析)
     * @param segments     域名后面的资源地址
     * @param message      响应信息
     * @param responseUrl  请求地址
     */
    void printJsonResponse(long chainMs, boolean isSuccessful, int code, @NonNull String headers, @Nullable MediaType contentType,
                           @Nullable String bodyString, @NonNull List<String> segments, @NonNull String message, @NonNull String responseUrl);

    /**
     * 打印网络响应信息, 当网络响应时 {{@link okhttp3.ResponseBody}} 为 {@code null} 或不可解析的情况
     *
     * @param chainMs      服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code         响应码
     * @param headers      请求头
     * @param segments     域名后面的资源地址
     * @param message      响应信息
     * @param responseUrl  请求地址
     */
    void printFileResponse(long chainMs, boolean isSuccessful, int code, @NonNull String headers,
                           @NonNull List<String> segments, @NonNull String message, @NonNull String responseUrl);
}
