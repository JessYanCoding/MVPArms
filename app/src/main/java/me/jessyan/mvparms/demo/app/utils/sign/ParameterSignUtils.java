package me.jessyan.mvparms.demo.app.utils.sign;

import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

/**
 * Created by czw on 2017/11/30.
 * 所有验签工具类
 */
public class ParameterSignUtils {

    /**
     * 公共参数 和 请求参数验签方法 工具类
     *
     * @param url 请求链接
     * @return 对应链接的公共参数
     */
    public static Map<String, String> buildCommonParameter(String url, Map<String, String> maps) {

        /*打印请求url*/
        getParameterUrl(url, maps);

        if (TextUtils.isEmpty(url)) {
            return new HashMap<>();
        }
        //提交订单公共参数
//        if (url.contains(ApiConfiguration.Domain.CARD_BASE_URL)) {
            Map<String, String> sMap = new HashMap<>();
            sMap.put("merCode", "1000");//合作伙伴账号（非空，由平台分配）
            sMap.put("terminalId", "1000");//合作伙伴开发的渠道的编号（由平台分配，为空字符串时代表合作伙伴自身）
            sMap.put("terminalType", "androidClient");//调用接口的终端编号，用于记录调用接口的入口，可以进行权限控制（非空，合作伙伴自定义）
            sMap.put("dataType", "json");//请求输出的数据格式
            sMap.put("version", "1.0.0");//版本号
//            if (!TextUtils.isEmpty(PreferenceUtils.getReqtime())) {
//                sMap.put("reqtime", PreferenceUtils.getReqtime());//版本号
//
//            }
            SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String _date = _simpleDateFormat.format(new Date());
            sMap.put("reqtime", _date);//请求时间，格式：yyyyMMddhhmmss
            sMap.putAll(maps); //添加请求参数
            sMap.put("sign", buildSignature(url, sMap)); //所有参数进行验签
            return sMap;
//        } else {
//            //业务服务器公共参数
//            Map<String, String> sMap = new HashMap<>();
//            sMap.put("platformId", "0757000002");//合作伙伴账号（非空，由平台分配）
//            sMap.put("channelId", "0757000002_100062");//合作伙伴开发的渠道的编号（由平台分配，为空字符串时代表合作伙伴自身）
//            sMap.put("terminalId", "zhangwo");//调用接口的终端编号，用于记录调用接口的入口，可以进行权限控制（非空，合作伙伴自定义）
//            sMap.put("signMethod", "MD5");//签名方法（非空，暂时支持只MD5）
//            sMap.put("dataType", "json");//请求输出的数据格式
//            sMap.put("version", "1.0.0");//版本号
//            SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
//            String _date = _simpleDateFormat.format(new Date());
//            sMap.put("reqtime", _date);//请求时间，格式：yyyyMMddhhmmss
//            sMap.putAll(maps); //添加请求参数
//            sMap.put("sign", buildSignature(url, sMap)); //所有参数进行验签
//            return sMap;
//        }
    }

    /**
     * 生成验签工具类
     *
     * @param url 请求链接
     * @param map 需要验签的map集合
     * @return
     */
    private static String buildSignature(String url, Map<String, String> map) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
//        if (url.contains(ApiConfiguration.Domain.BASE_URL) || url.contains(ApiConfiguration.Domain.FQGCLIENT_BASE_URL)) {
//            return SignUtils.buildSignature(map);
//        } else if (url.contains(ApiConfiguration.Domain.FQG_ORDER_BASE_URL)) {
//            return ClientSignValidator.buildSignature(map, "rngEYBpDZEsM5EMIOUbmEVHAOjyJCP4b");
//        }
        return SignUtils.buildSignature(map);
    }


    /**
     * get请求参数拼接
     *
     * @param url          请求url
     * @param parameterMap 请求参数map集合
     * @return 拼接好参数的url
     */
    public static void getParameterUrl(String url, Map<String, String> parameterMap) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.contains("?")) {
            url += "?";
        }

        Set<Map.Entry<String, String>> sSet = parameterMap.entrySet();
        StringBuilder urlBuilder = new StringBuilder(url);
        for (Map.Entry<String, String> entry : sSet) {
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        url = urlBuilder.toString();


        if (url.contains("&")) {
            url = url.substring(0, url.length() - 1);
        }

        //打印请求信息
        Timber.tag("Request_Url").w(url.replace("\n", ""));
    }

    /**
     * get请求参数拼接
     *
     * @param url          请求url
     * @param parameterMap 请求参数map集合
     * @param filePathMaps 请求文件路径集合
     * @return 拼接好参数的url
     */
    public static void getParameterUrl(String url, Map<String, String> parameterMap, List<Map<String, String>> filePathMaps) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.contains("?")) {
            url += "?";
        }

        Set<Map.Entry<String, String>> sSet = parameterMap.entrySet();
        StringBuilder urlBuilder = new StringBuilder(url);
        for (Map.Entry<String, String> entry : sSet) {
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        url = urlBuilder.toString();

        if (url.contains("&")) {
            url = url.substring(0, url.length() - 1);
        }

        //打印请求信息
        Timber.tag("Request_Url").w(url.replace("\n", ""));

        if (filePathMaps != null && filePathMaps.size() > 0) {
            //传入文件路径
            for (Map<String, String> filePathMap : filePathMaps) {
                Set<Map.Entry<String, String>> filePathSet = filePathMap.entrySet();
                for (Map.Entry<String, String> entry : filePathSet) {
                    String sFilePath = entry.getValue();
                    if (TextUtils.isEmpty(sFilePath)) {
                        continue;
                    }
                    File sFile = new File(sFilePath);

                    //打印请求信息
                    Timber.tag("Request_FilePaths").w(sFile + "\n");
                }
            }
        }
    }


}
