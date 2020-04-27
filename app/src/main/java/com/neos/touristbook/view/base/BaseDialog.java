package com.neos.touristbook.view.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.neos.touristbook.App;
import com.neos.touristbook.StorageCommon;
import com.neos.touristbook.presenter.BasePresenter;
import com.neos.touristbook.view.event.OnActionCallback;

abstract public class BaseDialog<T extends BasePresenter> extends Dialog {
    protected T mPresenter;
    protected OnActionCallback mCallback;
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
    public void setmCallback(OnActionCallback mCallback) {
        this.mCallback = mCallback;
    }

    public BaseDialog(@NonNull Context context) {
        super(context);
        initPresenter();
        setContentView(getLayoutId());
        initView();
    }

    protected abstract void initPresenter();

    protected abstract void initView();

    protected abstract int getLayoutId();

    public <T extends View> T findViewById(int id, View.OnClickListener event) {
        return findViewById(id, event, null);
    }

    public <T extends View> T findViewById(int id, View.OnClickListener event, Typeface typeface) {
        T v = super.findViewById(id);
        if (event != null) {
            v.setOnClickListener(event);
        }
        if (v instanceof TextView) {
            if (typeface == null) {
                typeface = App.getInstance().getFontRegular();
            }
            ((TextView) v).setTypeface(typeface);
        }
        return v;
    }


    public <T extends View> T findViewById(int id, Typeface typeface) {
        return findViewById(id, null, typeface);
    }
}
