package com.neos.touristbook.presenter;


import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.neos.touristbook.App;
import com.neos.touristbook.StorageCommon;
import com.neos.touristbook.event.OnCallback;

abstract public class BasePresenter<T extends OnCallback> {
    protected T mCallback;

    protected Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            excuteHandler(msg);
            return false;
        }
    });

    protected void excuteHandler(Message msg) {

    }

    protected StorageCommon getStorageCommon() {
        return App.getInstance().getStorageCommon();
    }


    protected void updateHandler(int... value) {
        if (value.length == 0) {
            return;
        }
        Message msg = new Message();
        msg.what = value[0];
        try {
            msg.arg1 = value[1];
            msg.arg2 = value[2];
        } catch (Exception e) {

        }
        msg.setTarget(mHandler);
        msg.sendToTarget();
    }

    public BasePresenter(T mCallback) {
        this.mCallback = mCallback;
    }
}
