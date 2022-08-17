package com.kna.touristbook.view.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.kna.touristbook.App;
import com.kna.touristbook.R;
import com.kna.touristbook.StorageCommon;
import com.kna.touristbook.presenter.BasePresenter;
import com.kna.touristbook.view.event.OnActionCallback;

abstract public class BaseDialog<T extends BasePresenter> extends Dialog implements View.OnClickListener {
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

    public BaseDialog(@NonNull Context context, int style) {
        super(context, style);
        initPresenter();
        setContentView(getLayoutId());
        initView();
    }

    @Override
    public void onClick(View v) {

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
//        if (v instanceof TextView) {
//            if (typeface == null) {
//                typeface = App.getInstance().getFontRegular();
//            }
//            ((TextView) v).setTypeface(typeface);
//        }
        return v;
    }

    public void showLoading() {
        RelativeLayout rlLoading=findViewById(R.id.rl_loading);
        rlLoading.setVisibility(View.VISIBLE);
        rlLoading.setOnClickListener(this);
        ImageView ivLoading=rlLoading.findViewById(R.id.iv_loading);
        Glide.with(getContext()).load(R.drawable.loading).into(ivLoading);
    }

    public void hideLoading() {
        RelativeLayout rlLoading=findViewById(R.id.rl_loading);
        rlLoading.setVisibility(View.GONE);
    }

    public <T extends View> T findViewById(int id, Typeface typeface) {
        return findViewById(id, null, typeface);
    }

    public <T extends View> T findViewById(int id) {
        return findViewById(id, null, null);
    }
}
