package com.jess.arms.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jess on 8/30/16 17:47
 * Contact with jess.yan.effort@gmail.com
 */
public interface GlobeHttpHandler {
    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

    GlobeHttpHandler EMPTY = new GlobeHttpHandler() {
        @Override
        public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
            return null;
        }

        @Override
        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
            return null;
        }
    };

}
