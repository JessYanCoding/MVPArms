package com.jess.arms.http;

import android.support.annotation.Nullable;

import com.jess.arms.utils.CharactorHandler;
import com.jess.arms.utils.ZipHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

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
 * Contact with jess.yan.effort@gmail.com
 */
@Singleton
public class RequestInterceptor implements Interceptor {
    private GlobalHttpHandler mHandler;

    @Inject
    public RequestInterceptor(@Nullable GlobalHttpHandler handler) {
        this.mHandler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        boolean hasRequestBody = request.body() != null;

        Buffer requestbuffer = new Buffer();

        if (hasRequestBody) {
            request.body().writeTo(requestbuffer);
        }

        //打印请求信息
        Timber.tag(getTag(request, "Request_Info")).w("Params : 「 %s 」%nConnection : 「 %s 」%nHeaders : %n「 %s 」"
                , hasRequestBody ? parseParams(request.body(), requestbuffer) : "Null"
                , chain.connection()
                , request.headers());

        long t1 = System.nanoTime();

        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            Timber.w("Http Error: " + e);
            throw e;
        }
        long t2 = System.nanoTime();

        String bodySize = originalResponse.body().contentLength() != -1 ? originalResponse.body().contentLength() + "-byte" : "unknown-length";

        //打印响应时间以及响应头
        Timber.tag(getTag(request, "Response_Info")).w("Received response in [ %d-ms ] , [ %s ]%n%s"
                , TimeUnit.NANOSECONDS.toMillis(t2 - t1), bodySize, originalResponse.headers());

        //打印响应结果
        String bodyString = printResult(request, originalResponse);

        if (mHandler != null)//这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);

        return originalResponse;
    }

    /**
     * 打印响应结果
     *
     * @param request
     * @param originalResponse
     * @return
     * @throws IOException
     */
    @Nullable
    private String printResult(Request request, Response originalResponse) throws IOException {
        //读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        String bodyString = null;
        if (isParseable(responseBody)) {
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

            Timber.tag(getTag(request, "Response_Result")).w(isJson(responseBody) ? CharactorHandler.jsonFormat(bodyString) : bodyString);

        } else {
            Timber.tag(getTag(request, "Response_Result")).w("This result isn't parsed");
        }
        return bodyString;
    }


    private String getTag(Request request, String tag) {
        return String.format(" [%s] 「 %s 」>>> %s", request.method(), request.url().toString(), tag);
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
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            return ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset));//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));//解压
        } else {//content没有被压缩
            return clone.readString(charset);
        }
    }

    public static String parseParams(RequestBody body, Buffer requestbuffer) throws UnsupportedEncodingException {
        if (body.contentType() == null) return "Unknown";
        if (!body.contentType().toString().contains("multipart")) {
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            return URLDecoder.decode(requestbuffer.readString(charset), convertCharset(charset));
        }
        return "This Params isn't Text";
    }

    public static boolean isParseable(ResponseBody responseBody) {
        if (responseBody.contentLength() == 0 || responseBody.contentType() == null) return false;
        return responseBody.contentType().toString().toLowerCase().contains("text")
                || isJson(responseBody) || isForm(responseBody)
                || isHtml(responseBody) || isXml(responseBody);
    }

    public static boolean isJson(ResponseBody responseBody) {
        return responseBody.contentType().toString().toLowerCase().contains("json");
    }

    public static boolean isXml(ResponseBody responseBody) {
        return responseBody.contentType().toString().toLowerCase().contains("xml");
    }

    public static boolean isHtml(ResponseBody responseBody) {
        return responseBody.contentType().toString().toLowerCase().contains("html");
    }

    public static boolean isForm(ResponseBody responseBody) {
        return responseBody.contentType().toString().toLowerCase().contains("x-www-form-urlencoded");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1)
            return s;
        return s.substring(i + 1, s.length() - 1);
    }
}
