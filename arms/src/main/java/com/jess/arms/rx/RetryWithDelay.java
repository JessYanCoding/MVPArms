package com.jess.arms.rx;

import com.apkfuns.logutils.LogUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * Created by jess on 9/2/16 14:32
 * Contact with jess.yan.effort@gmail.com
 */
public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {
    private final int maxRetries;
    private final int retryDelaySecond;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, Observable<?>>() {
            @Override
            public Observable<?> apply(Throwable throwable) throws Exception {
                if (++retryCount <= maxRetries) {
                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                    LogUtils.d("get error, it will try after " + retryDelaySecond + " second, retry count " + retryCount);
                    return Observable.timer(retryDelaySecond, TimeUnit.SECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Observable.error(throwable);
            }
        });
    }
}