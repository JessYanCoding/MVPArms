package com.jess.arms.integration;

import androidx.annotation.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;


public class RetrofitServiceProxyHandler implements InvocationHandler {

    private Retrofit mRetrofit;
    private Class<?> mServiceClass;
    private Object mRetrofitService;

    public RetrofitServiceProxyHandler(Retrofit retrofit, Class<?> serviceClass) {
        mRetrofit = retrofit;
        mServiceClass = serviceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, @Nullable Object[] args) throws Throwable {

        // 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化

        if (method.getReturnType() == Observable.class) {
            // 如果方法返回值是 Observable 的话，则包一层再返回，
            // 只包一层 defer 由外部去控制耗时方法以及网络请求所处线程，
            // 如此对原项目的影响为 0，且更可控。
            return Observable.defer(() -> {
                // 执行真正的 Retrofit 动态代理的方法
                return (Observable) method.invoke(getRetrofitService(), args);
            });
        } else if (method.getReturnType() == Single.class) {
            // 如果方法返回值是 Single 的话，则包一层再返回。
            return Single.defer(() -> {
                // 执行真正的 Retrofit 动态代理的方法
                return (Single) method.invoke(getRetrofitService(), args);
            });
        }

        // 返回值不是 Observable 或 Single 的话不处理。
        return method.invoke(getRetrofitService(), args);
    }

    private Object getRetrofitService() {
        if (mRetrofitService == null) {
            mRetrofitService = mRetrofit.create(mServiceClass);
        }
        return mRetrofitService;
    }
}
