package me.jessyan.rxerrorhandler.handler;

import android.content.Context;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErroListener;

/**
 * Created by jess on 9/2/16 13:47
 * Contact with jess.yan.effort@gmail.com
 */
public class ErrorHandlerFactory {
    public final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ResponseErroListener mResponseErroListener;

    public ErrorHandlerFactory(Context mContext, ResponseErroListener mResponseErroListener) {
        this.mResponseErroListener = mResponseErroListener;
        this.mContext = mContext;
    }

    /**
     *  处理错误
     * @param throwable
     */
    public void handleError(Throwable throwable) {
        mResponseErroListener.handleResponseError(mContext, (Exception) throwable);
    }
}
