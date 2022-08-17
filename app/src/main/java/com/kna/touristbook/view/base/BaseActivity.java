package com.kna.touristbook.view.base;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kna.touristbook.App;
import com.kna.touristbook.R;
import com.kna.touristbook.StorageCommon;
import com.kna.touristbook.presenter.BasePresenter;
import com.kna.touristbook.view.event.OnActionCallback;
import com.kna.touristbook.view.fragment.AccountFrg;
import com.kna.touristbook.view.fragment.FavouriteFrg;
import com.kna.touristbook.view.fragment.HomeFrg;
import com.kna.touristbook.view.fragment.ReviewFrg;
import com.kna.touristbook.view.fragment.TourFrg;

import java.lang.reflect.Constructor;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements OnActionCallback, View.OnClickListener {
    protected T mPresenter;
    public static DisplayMetrics displayMetrics;

    protected Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            excuteHandler(msg);
            return false;
        }
    });

    @Override
    public void onClick(View v) {

    }

    protected final String[] TAG = new String[]{HomeFrg.TAG, TourFrg.TAG, ReviewFrg.TAG, FavouriteFrg.TAG, AccountFrg.TAG};

    protected void excuteHandler(Message msg) {

    }

    protected StorageCommon getStorageCommon() {
        return App.getInstance().getStorageCommon();
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initPresenter();
        initView();
        try {
            displayMetrics = new DisplayMetrics();
            getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);
        } catch (Exception e) {

        }
    }

    protected abstract void initPresenter();

    protected abstract void initView();

    protected abstract int getLayoutId();

    public <T extends View> T findViewById(int id, View.OnClickListener event) {

        return findViewById(id, event, null);
    }
    public void showLoading() {
        RelativeLayout rlLoading=findViewById(R.id.rl_loading);
        rlLoading.setVisibility(View.VISIBLE);
        rlLoading.setOnClickListener(this);
        ImageView ivLoading=rlLoading.findViewById(R.id.iv_loading);
        Glide.with(this).load(R.drawable.loading).into(ivLoading);
    }

    public void hideLoading() {
        RelativeLayout rlLoading=findViewById(R.id.rl_loading);
        rlLoading.setVisibility(View.GONE);
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

    protected void showFragment(String tag) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(tag);
            Constructor<?> ctor = clazz.getConstructor();
            BaseFragment object = (BaseFragment) ctor.newInstance();
            object.setmCallback(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fr_main, object).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(String key, Object data) {

    }
}
