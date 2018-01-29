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

package com.jess.arms.http;

import android.text.TextUtils;

import java.util.List;

import okhttp3.Request;
import timber.log.Timber;

/**
 * ================================================
 * 对 OkHttp 的请求和响应信息进行更规范和清晰的打印
 * <p>
 * Created by JessYan on 25/01/2018 14:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

public class FormatPrinter {
    private static final String TAG = "ArmsHttpLog";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR;

    private static final String[] OMITTED_RESPONSE = {LINE_SEPARATOR, "Omitted response body"};
    private static final String[] OMITTED_REQUEST = {LINE_SEPARATOR, "Omitted request body"};

    private static final String N = "\n";
    private static final String T = "\t";
    private static final String REQUEST_UP_LINE = "┌────── Request ────────────────────────────────────────────────────────────────────────";
    private static final String END_LINE = "└───────────────────────────────────────────────────────────────────────────────────────";
    private static final String RESPONSE_UP_LINE = "┌────── Response ───────────────────────────────────────────────────────────────────────";
    private static final String BODY_TAG = "Body:";
    private static final String URL_TAG = "URL: ";
    private static final String METHOD_TAG = "Method: @";
    private static final String HEADERS_TAG = "Headers:";
    private static final String STATUS_CODE_TAG = "Status Code: ";
    private static final String RECEIVED_TAG = "Received in: ";
    private static final String CORNER_UP = "┌ ";
    private static final String CORNER_BOTTOM = "└ ";
    private static final String CENTER_LINE = "├ ";
    private static final String DEFAULT_LINE = "│ ";

    private FormatPrinter() {
        throw new UnsupportedOperationException("you can't instantiate me!");
    }

    private static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || N.equals(line) || T.equals(line) || TextUtils.isEmpty(line.trim());
    }

    /**
     * 打印网络请求信息, 当网络请求时 {{@link okhttp3.RequestBody}} 可以解析的情况
     *
     * @param request
     * @param bodyString
     */
    static void printJsonRequest(Request request, String bodyString) {
        final String requestBody = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + bodyString;
        final String tag = getTag(true);

        Timber.tag(tag).i(REQUEST_UP_LINE);
        logLines(tag, new String[]{URL_TAG + request.url()}, false);
        logLines(tag, getRequest(request), true);
        logLines(tag, requestBody.split(LINE_SEPARATOR), true);
        Timber.tag(tag).i(END_LINE);
    }

    /**
     * 打印网络请求信息, 当网络请求时 {{@link okhttp3.RequestBody}} 为 {@code null} 或不可解析的情况
     *
     * @param request
     */
    static void printFileRequest(Request request) {
        final String tag = getTag(true);

        Timber.tag(tag).i(REQUEST_UP_LINE);
        logLines(tag, new String[]{URL_TAG + request.url()}, false);
        logLines(tag, getRequest(request), true);
        logLines(tag, OMITTED_REQUEST, true);
        Timber.tag(tag).i(END_LINE);
    }

    /**
     * 打印网络响应信息, 当网络响应时 {{@link okhttp3.ResponseBody}} 可以解析的情况
     *
     * @param chainMs
     * @param isSuccessful
     * @param code
     * @param headers
     * @param bodyString
     * @param segments
     * @param message
     * @param responseUrl
     */
    static void printJsonResponse(long chainMs, boolean isSuccessful,
                                  int code, String headers, String bodyString, List<String> segments, String message, final String responseUrl) {

        final String responseBody = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + bodyString;
        final String tag = getTag(false);
        final String[] urlLine = {URL_TAG + responseUrl, N};

        Timber.tag(tag).i(RESPONSE_UP_LINE);
        logLines(tag, urlLine, true);
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments, message), true);
        logLines(tag, responseBody.split(LINE_SEPARATOR), true);
        Timber.tag(tag).i(END_LINE);
    }

    /**
     * 打印网络响应信息, 当网络响应时 {{@link okhttp3.ResponseBody}} 为 {@code null} 或不可解析的情况
     *
     * @param chainMs
     * @param isSuccessful
     * @param code
     * @param headers
     * @param segments
     * @param message
     * @param responseUrl
     */
    static void printFileResponse(long chainMs, boolean isSuccessful,
                                  int code, String headers, List<String> segments, String message, final String responseUrl) {
        final String tag = getTag(false);
        final String[] urlLine = {URL_TAG + responseUrl, N};

        Timber.tag(tag).i(RESPONSE_UP_LINE);
        logLines(tag, urlLine, true);
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments, message), true);
        logLines(tag, OMITTED_RESPONSE, true);
        Timber.tag(tag).i(END_LINE);
    }


    /**
     * 对 {@code lines} 中的信息进行逐行打印
     *
     * @param tag
     * @param lines
     * @param withLineSize 为 {@code true} 时, 每行的信息长度不会超过110, 超过则自动换行
     */
    private static void logLines(String tag, String[] lines, boolean withLineSize) {
        for (String line : lines) {
            int lineLength = line.length();
            int MAX_LONG_SIZE = withLineSize ? 110 : lineLength;
            for (int i = 0; i <= lineLength / MAX_LONG_SIZE; i++) {
                int start = i * MAX_LONG_SIZE;
                int end = (i + 1) * MAX_LONG_SIZE;
                end = end > line.length() ? line.length() : end;
                Timber.tag(tag).i(DEFAULT_LINE + line.substring(start, end));
            }
        }
    }


    private static String[] getRequest(Request request) {
        String log;
        String header = request.headers().toString();
        log = METHOD_TAG + request.method() + DOUBLE_SEPARATOR +
                (isEmpty(header) ? "" : HEADERS_TAG + LINE_SEPARATOR + dotHeaders(header));
        return log.split(LINE_SEPARATOR);
    }

    private static String[] getResponse(String header, long tookMs, int code, boolean isSuccessful,
                                        List<String> segments, String message) {
        String log;
        String segmentString = slashSegments(segments);
        log = ((!TextUtils.isEmpty(segmentString) ? segmentString + " - " : "") + "is success : "
                + isSuccessful + " - " + RECEIVED_TAG + tookMs + "ms" + DOUBLE_SEPARATOR + STATUS_CODE_TAG +
                code + " / " + message + DOUBLE_SEPARATOR + (isEmpty(header) ? "" : HEADERS_TAG + LINE_SEPARATOR +
                dotHeaders(header)));
        return log.split(LINE_SEPARATOR);
    }

    private static String slashSegments(List<String> segments) {
        StringBuilder segmentString = new StringBuilder();
        for (String segment : segments) {
            segmentString.append("/").append(segment);
        }
        return segmentString.toString();
    }

    /**
     * 对 {@code header} 按规定的格式进行处理
     *
     * @param header
     * @return
     */
    private static String dotHeaders(String header) {
        String[] headers = header.split(LINE_SEPARATOR);
        StringBuilder builder = new StringBuilder();
        String tag = "─ ";
        if (headers.length > 1) {
            for (int i = 0; i < headers.length; i++) {
                if (i == 0) {
                    tag = CORNER_UP;
                } else if (i == headers.length - 1) {
                    tag = CORNER_BOTTOM;
                } else {
                    tag = CENTER_LINE;
                }
                builder.append(tag).append(headers[i]).append("\n");
            }
        } else {
            for (String item : headers) {
                builder.append(tag).append(item).append("\n");
            }
        }
        return builder.toString();
    }


    private static String getTag(boolean isRequest) {
        if (isRequest) {
            return TAG + "-Request";
        } else {
            return TAG + "-Response";
        }
    }

}
