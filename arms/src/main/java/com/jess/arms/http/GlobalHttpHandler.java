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

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jess on 8/30/16 17:47
 * Contact with jess.yan.effort@gmail.com
 */
public interface GlobalHttpHandler {
    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

    GlobalHttpHandler EMPTY = new GlobalHttpHandler() {
        @Override
        public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
            //不管是否处理,都必须将response返回出去
            return response;
        }

        @Override
        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
            //不管是否处理,都必须将request返回出去
            return request;
        }
    };

}
