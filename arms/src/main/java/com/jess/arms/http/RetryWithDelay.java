package com.jess.arms.http;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * Created by xiaobailong24 on 2017/3/31.
 * 错误重试
 */
public class RetryWithDelay implements Function<Observable<Throwable>, Observable<?>> {
    public final String TAG = this.getClass().getSimpleName();
    private final int maxRetries;
    private final int retryDelaySecond;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<Throwable> attempts) throws Exception {
        return attempts
                .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if (++retryCount <= maxRetries) {
                        // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                        Timber.tag(TAG).e("apply: " + "get error, it will try after " + retryDelaySecond
                                + " second, retry count " + retryCount);
                        return Observable.timer(retryDelaySecond, TimeUnit.SECONDS);
                    }
                    // Max retries hit. Just pass the error along.
                    return Observable.error(throwable);
                });
    }
}
