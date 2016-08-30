package com.jess.arms.http;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;


/**
 * Created by jess on 7/1/16.
 */
public class RequestIntercept implements Interceptor {
    private GlobeHttpResultHandler mHandler;

    public RequestIntercept(GlobeHttpResultHandler handler) {
        this.mHandler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Buffer requestbuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestbuffer);
        } else {
            Timber.tag("Request").w("request.body() == null");
        }

        //打印url信息
        Timber.tag("Request").w("Sending Request %s on %n Params --->  %s%n Connection ---> %s%n Headers ---> %s", request.url()
                , request.body() != null ? requestbuffer.readUtf8() : "null"
                , chain.connection()
                , request.headers());

        long t1 = System.nanoTime();
        Response originalResponse = chain.proceed(request);
        long t2 = System.nanoTime();
        //打赢响应时间
        Timber.tag("Response").w("Received response  in %.1fms%n%s", (t2 - t1) / 1e6d, originalResponse.headers());

        //读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }

        String bodyString = buffer.clone().readString(charset);
        Timber.tag("Response").w("Body------>" + bodyString);

        if (mHandler!=null)//这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
            mHandler.onHttpResultResponse(bodyString);

        return originalResponse;
    }

}
