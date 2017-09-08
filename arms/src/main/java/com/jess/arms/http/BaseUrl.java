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

import okhttp3.HttpUrl;

/**
 * Created by jess on 11/07/2017 14:58
 * Contact with jess.yan.effort@gmail.com
 */

public interface BaseUrl {
    /**
     * 针对于 BaseUrl 在 App 启动时不能确定,需要请求服务器接口动态获取的应用场景
     * 在调用 Retrofit 接口之前,使用 Okhttp 或其他方式,请求到正确的 BaseUrl 并通过此方法返回
     * @return
     */
    HttpUrl url();
}
