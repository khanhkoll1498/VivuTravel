package com.neos.touristbook.view.base;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neos.touristbook.App;
import com.neos.touristbook.StorageCommon;
import com.neos.touristbook.presenter.BasePresenter;
import com.neos.touristbook.view.event.OnActionCallback;

abstract public class BaseFragment<T extends BasePresenter> extends Fragment implements View.OnClickListener {
    protected Context context;
    protected View rootView;
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

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        initPresenter();
        initView();
        return rootView;
    }

    protected abstract void initPresenter();

    protected abstract int getLayoutId();

    protected abstract void initView();

    public <T extends View> T findViewById(int id) {

        return findViewById(id, null, null);
    }

    public <T extends View> T findViewById(int id, View.OnClickListener event) {

        return findViewById(id, event, null);
    }

    public <T extends View> T findViewById(int id, View.OnClickListener event, Typeface typeface) {
        T v = rootView.findViewById(id);
        if (event != null) {
            v.setOnClickListener(event);
        }
//        if (v instanceof TextView) {
//            if (typeface == null) {
//                typeface = App.getInstance().getFontRegular();
//            }
//            ((TextView) v).setTypeface(typeface);
//        }
        return v;
    }

    public <T extends View> T findViewById(int id, Typeface typeface) {
        return findViewById(id, null, typeface);
    }

    @Override
    public void onClick(View v) {
    }




}
