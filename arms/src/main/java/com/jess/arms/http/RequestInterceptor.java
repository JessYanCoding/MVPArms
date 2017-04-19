package com.jess.arms.http;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.jess.arms.common.utils.CharactorHandler;
import com.jess.arms.common.utils.ZipHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Locale;
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

import static com.apkfuns.logutils.LogUtils.w;


/**
 * Created by jess on 7/1/16.
 */
@Singleton
public class RequestInterceptor implements Interceptor {
    private IGlobeHttpHandler mHandler;
    private String mUserAgent;

    @Inject
    public RequestInterceptor(Application context,IGlobeHttpHandler handler) {
        this.mHandler = handler;
        this.mUserAgent = getUserAgent(context);
    }

    /**
     * 获取UserAgent
     * @param context
     * @return
     */
    private String getUserAgent(Context context) {
            String webUserAgent = null;
            try {
                Class<?> sysResCls = Class.forName("com.android.internal.R$string");
                Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
                Integer resId = (Integer) webUserAgentField.get(null);
                webUserAgent = context.getString(resId);
            } catch (Exception e) {
                // We have nothing to do
            }
            if (TextUtils.isEmpty(webUserAgent)) {
                webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/5.0 %sSafari/533.1";
            }

            Locale locale = Locale.getDefault();
            StringBuffer buffer = new StringBuffer();
            // Add version
            final String version = Build.VERSION.RELEASE;
            if (version.length() > 0) {
                buffer.append(version);
            } else {
                // default to "1.0"
                buffer.append("1.0");
            }
            buffer.append("; ");
            final String language = locale.getLanguage();
            if (language != null) {
                buffer.append(language.toLowerCase(locale));
                final String country = locale.getCountry();
                if (!TextUtils.isEmpty(country)) {
                    buffer.append("-");
                    buffer.append(country.toLowerCase(locale));
                }
            } else {
                // default to "en"
                buffer.append("en");
            }
            // add the model for the release build
            if ("REL".equals(Build.VERSION.CODENAME)) {
                final String model = Build.MODEL;
                if (model.length() > 0) {
                    buffer.append("; ");
                    buffer.append(model);
                }
            }
            final String id = Build.ID;
            if (id.length() > 0) {
                buffer.append(" Build/");
                buffer.append(id);
            }
        return String.format(webUserAgent, buffer, "Mobile ");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //替换掉okhhtp初始的"User-Agent"
        Request request = chain.request().newBuilder().header("User-Agent", mUserAgent).build();

        boolean hasRequestBody = request.body() != null;

        Buffer requestbuffer = new Buffer();

        if (hasRequestBody) {
            request.body().writeTo(requestbuffer);
        }

        //打印请求信息
        LogUtils.tag("Request").w("Params : 「 %s 」%nConnection : 「 %s 」%nHeaders : %n「 %s 」"
                , hasRequestBody ? parseParams(request.body(), requestbuffer) : "Null"
                , chain.connection()
                , request.headers());

        long t1 = System.nanoTime();

        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            w("Http Error: " + e);
            throw e;
        }
        long t2 = System.nanoTime();

        String bodySize = originalResponse.body().contentLength() != -1 ? originalResponse.body().contentLength() + "-byte" : "unknown-length";

        //打印响应时间以及响应头
        LogUtils.tag("Response").w("Received response in [ %d-ms ] , [ %s ]%n%s"
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
            if (isJson(responseBody)) {
                bodyString = CharactorHandler.jsonFormat(bodyString);
                if (bodyString.length() > 3072) {
                    bodyString = bodyString.substring(0,3062) +"\r\n........";
                }
            }
            LogUtils.tag("Response").w(bodyString);

        } else {
            LogUtils.tag("Response").w("This result isn't parsed");
        }
        return bodyString;
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
            return ZipHelper.decompressForGzip(clone.readByteArray(),convertCharset(charset));//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(),convertCharset(charset));//解压
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
        if (responseBody.contentLength() == 0) return false;
        return responseBody.contentType().toString().contains("text") || isJson(responseBody);
    }

    public static boolean isJson(ResponseBody responseBody) {
        return responseBody.contentType().toString().contains("json");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1)
            return s;
        return s.substring(i + 1, s.length() - 1);
    }
}
