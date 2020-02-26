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
package com.jess.arms.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.module.GlobalConfigModule;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 处理 Http 请求和响应结果的处理类
 * 使用 {@link GlobalConfigModule.Builder#globalHttpHandler(GlobalHttpHandler)} 方法配置
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.2">GlobalHttpHandler Wiki 官方文档</a>
 * Created by JessYan on 8/30/16 17:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface GlobalHttpHandler {

    /**
     * 空实现
     */
    GlobalHttpHandler EMPTY = new GlobalHttpHandler() {

        @NonNull
        @Override
        public Response onHttpResultResponse(@Nullable String httpResult, @NonNull Interceptor.Chain chain, @NonNull Response response) {
            //不管是否处理, 都必须将 response 返回出去
            return response;
        }

        @NonNull
        @Override
        public Request onHttpRequestBefore(@NonNull Interceptor.Chain chain, @NonNull Request request) {
            //不管是否处理, 都必须将 request 返回出去
            return request;
        }
    };

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link okhttp3.Interceptor.Chain}
     * @param response   {@link Response}
     * @return {@link Response}
     */
    @NonNull
    Response onHttpResultResponse(@Nullable String httpResult, @NonNull Interceptor.Chain chain, @NonNull Response response);

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link okhttp3.Interceptor.Chain}
     * @param request {@link Request}
     * @return {@link Request}
     */
    @NonNull
    Request onHttpRequestBefore(@NonNull Interceptor.Chain chain, @NonNull Request request);
}
