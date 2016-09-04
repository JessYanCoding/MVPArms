package com.jess.arms.http;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by jess on 8/30/16 17:47
 * Contact with jess.yan.effort@gmail.com
 */
public interface GlobeHttpResultHandler {
    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);
}
