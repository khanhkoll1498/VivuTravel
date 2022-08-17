package com.kna.touristbook.view.base;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kna.touristbook.App;
import com.kna.touristbook.R;
import com.kna.touristbook.StorageCommon;
import com.kna.touristbook.presenter.BasePresenter;
import com.kna.touristbook.view.event.OnActionCallback;

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
    protected FirebaseAuth mAuth;
    protected FirebaseUser user;

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
    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
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
