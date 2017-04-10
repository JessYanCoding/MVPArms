package com.jess.arms.http;

import android.support.annotation.NonNull;

import com.jess.arms.utils.CharactorHandler;
import com.jess.arms.utils.ZipHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;


/**
 * Created by jess on 7/1/16.
 */
@Singleton
public class RequestInterceptor implements Interceptor {
    private GlobeHttpHandler mHandler;

    @Inject
    public RequestInterceptor(GlobeHttpHandler handler) {
        this.mHandler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Buffer requestbuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestbuffer);
        }


        //打印url信息
        Timber.tag(getTag(request, "Request_Info")).w("Params : 「 %s 」%nConnection : 「 %s 」%nHeaders : %n「 %s 」"
                , request.body() != null ? parseParams(request.body(), requestbuffer) : "null"
                , chain.connection()
                , request.headers());

        long t1 = System.nanoTime();
        Response originalResponse = chain.proceed(request);
        long t2 = System.nanoTime();
        //打印响应时间
        Timber.tag(getTag(request, "Response_Info")).w("Received response in [ %.1fms ]%n%s", (t2 - t1) / 1e6d, originalResponse.headers());

        //读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        String bodyString = null;
        if (isJson(responseBody)) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            //获取content的压缩类型
            String encoding = originalResponse
                    .headers()
                    .get("Content-Encoding");

            Buffer clone = buffer.clone();


            //解析response content
            bodyString = parseContent(responseBody, encoding, clone);

            Timber.tag(getTag(request, "Response_Result")).w(CharactorHandler.jsonFormat(bodyString));

        } else {
            Timber.tag(getTag(request, "Response_Result")).w("The result isn't json");
        }

        if (mHandler != null)//这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);

        return originalResponse;
    }


    public String getTag(Request request, String tag) {
        return "「 " + request.url() + " 」" + tag;
    }


    /**
     * 解析服务器响应的内容
     *
     * @param responseBody
     * @param encoding
     * @param clone
     * @return
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            return ZipHelper.decompressForGzip(clone.readByteArray());//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            return ZipHelper.decompressToStringForZlib(clone.readByteArray());//解压
        } else {//content没有被压缩
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            return clone.readString(charset);
        }
    }

    @NonNull
    public static String parseParams(RequestBody body, Buffer requestbuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestbuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    public boolean isJson(ResponseBody responseBody) {
        return responseBody.contentType().toString().contains("json");
    }
}
