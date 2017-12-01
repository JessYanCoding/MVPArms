package me.jessyan.mvparms.demo.app.utils;

import com.jess.arms.http.HttpException;
import com.jess.arms.integration.IRepositoryManager;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import me.jessyan.mvparms.demo.app.utils.sign.ParameterSignUtils;
import me.jessyan.mvparms.demo.mvp.model.api.service.CommonService;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseJson;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by czw on 2017/11/21.
 */
public class NetworkUtils {

    /**
     * 解析对象
     *
     * @param mRepositoryManager
     * @param baseUrl            网络请求url
     * @param maps               参数
     * @param tClass             转换对象
     * @param <T>                需转换泛型
     * @return 解析后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable<T> requestObject(IRepositoryManager mRepositoryManager, String baseUrl, Map<String, String> maps, Class<T> tClass) {
//        RetrofitUrlManager.getInstance().putDomain("baidu", "https://www.baidu.com"); //单独请求需要修改BaseUrl,需要在RetrofitService添加@Headers({DOMAIN_NAME_HEADER + "baidu"})
        RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl); //修改全局的BaseUrl
        Map<String, String> parameterMaps = ParameterSignUtils.buildCommonParameter(baseUrl, maps); //拼接公共参数
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .executeEncodePost(baseUrl, parameterMaps)
                .flatMap(responseBody -> Observable.create((ObservableOnSubscribe<T>) e -> {
                    try {
                        if (tClass.equals(BaseJson.class)) {
                            //基类
                            BaseJson baseJson = GsonUtils.GsonToBean(responseBody.string(), BaseJson.class);
                            if (baseJson.getCode() == 200 && baseJson.isSuccess()) {
                                e.onNext((T) baseJson);
                            } else {
                                e.onError(new HttpException(baseJson.getMsg(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                            }
                        } else if (tClass.equals(String.class)) {
                            //JSON字符串
                            BaseJson baseJson = GsonUtils.GsonToBean(responseBody.string(), BaseJson.class);
                            if (baseJson.getCode() == 200 && baseJson.isSuccess()) {
                                e.onNext((T) baseJson.getData().toString());
                            } else {
                                e.onError(new HttpException(baseJson.getMsg(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                            }
                        } else {
                            //对象
                            BaseJson<T> baseJson = GsonUtils.GsonToBean(responseBody.string(), BaseJson.class);
                            if (baseJson.getCode() == 200 && baseJson.isSuccess()) {
                                String jsonStr = GsonUtils.GsonString(baseJson.getData());
                                T t = GsonUtils.GsonToBean(jsonStr, tClass);
                                e.onNext(t);
                            } else {
                                e.onError(new HttpException(baseJson.getMsg(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                            }
                        }
                    } catch (Exception error) {
                        e.onError(error);
                    }
                }));
    }


    /**
     * 解析列表对象
     *
     * @param mRepositoryManager
     * @param baseUrl            网络请求url
     * @param maps               参数
     * @param tClass             转换对象
     * @param <T>                需转换泛型
     * @return 解析后的列表对象
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable<List<T>> requestObjectList(IRepositoryManager mRepositoryManager, String baseUrl, Map<String, String> maps, Class<T[]> tClass) {
        RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl); //修改全局的BaseUrl
        Map<String, String> parameterMaps = ParameterSignUtils.buildCommonParameter(baseUrl, maps); //拼接公共参数
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .executeEncodePost(baseUrl, parameterMaps)
                .flatMap(responseBody -> Observable.create(e -> {
                    try {
                        //列表对象
                        BaseJson<T> baseJson = GsonUtils.GsonToBean(responseBody.string(), BaseJson.class);
                        if (baseJson.getCode() == 200 && baseJson.isSuccess()) {
                            String jsonStr = GsonUtils.GsonString(baseJson.getData());
                            List<T> t = GsonUtils.GsonToArray(jsonStr, tClass);
                            e.onNext(t);
                        } else {
                            e.onError(new HttpException(baseJson.getMsg(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                        }
                    } catch (Exception error) {
                        e.onError(error);
                    }
                }));
    }

    /**
     * 上传文件
     *
     * @param mRepositoryManager
     * @param baseUrl            网络请求url
     * @param parameterMap       参数
     * @param fileMap            文件路径
     * @param tClass             转换对象
     * @param <T>                需转换泛型
     * @return 解析后的列表对象
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable<List<T>> uploadFile(IRepositoryManager mRepositoryManager, String baseUrl, Map<String, String> parameterMap, Map<String, String> fileMap, Class<T[]> tClass) {
        RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl); //修改全局的BaseUrl
        Map<String, String> parameterMaps = ParameterSignUtils.buildCommonParameter(baseUrl, parameterMap); //拼接公共参数
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM); //表单类型

        if (parameterMap != null && parameterMap.size() > 0) {
            //传入参数
            Set<Map.Entry<String, String>> parameterSet = parameterMaps.entrySet();
            for (Map.Entry<String, String> entry : parameterSet) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        if (fileMap != null && fileMap.size() > 0) {
            //传入图片
            Set<Map.Entry<String, String>> parameterSet = fileMap.entrySet();
            for (Map.Entry<String, String> entry : parameterSet) {
                File file = new File(entry.getValue()); //filePath 图片地址
                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file); //RequestBody.create(MediaType.parse("application/octet-stream"), file)
                builder.addFormDataPart(entry.getKey(), file.getName(), imageBody); //"imgfile"+i 后台接收图片流的参数名
            }
        }

        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .executePost(baseUrl, builder.build())
                .flatMap(responseBody -> Observable.create(e -> {
                    try {
                        BaseJson baseJson = GsonUtils.GsonToBean(responseBody.string(), BaseJson.class);
                        if (baseJson.getCode() == 200 && baseJson.isSuccess()) {
                            String jsonStr = GsonUtils.GsonString(baseJson.getData());
                            List<T> t = GsonUtils.GsonToArray(jsonStr, tClass);
                            e.onNext(t);
                        } else {
                            e.onError(new HttpException(baseJson.getMsg(), baseJson.getCode())); //非200状态抛出给ResponseErrorListenerImpl统一处理
                        }
                    } catch (Exception error) {
                        e.onError(error);
                    }
                }));
    }

}
