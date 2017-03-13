package me.jessyan.rxerrorhandler.handler.listener;

import android.content.Context;

/**
 * Created by jess on 9/2/16 13:58
 * Contact with jess.yan.effort@gmail.com
 */
public interface ResponseErroListener {
    void handleResponseError(Context context,Exception e);

    ResponseErroListener EMPTY = new ResponseErroListener() {
        @Override
        public void handleResponseError(Context context, Exception e) {

        }
    };
}
