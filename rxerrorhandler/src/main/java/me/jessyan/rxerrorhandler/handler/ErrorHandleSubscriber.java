package me.jessyan.rxerrorhandler.handler;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import rx.Subscriber;

/**
 * Created by jess on 9/2/16 14:41
 * Contact with jess.yan.effort@gmail.com
 */

public abstract class ErrorHandleSubscriber<T> extends Subscriber<T> {
    private ErrorHandlerFactory mHandlerFactory;

    public ErrorHandleSubscriber(RxErrorHandler rxErrorHandler){
        this.mHandlerFactory = rxErrorHandler.getmHandlerFactory();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mHandlerFactory.handleError(e);
    }

}

