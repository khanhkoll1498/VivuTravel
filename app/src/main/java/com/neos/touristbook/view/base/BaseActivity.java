package com.neos.touristbook.view.base;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.neos.touristbook.App;
import com.neos.touristbook.R;
import com.neos.touristbook.StorageCommon;
import com.neos.touristbook.presenter.BasePresenter;
import com.neos.touristbook.view.event.OnActionCallback;
import com.neos.touristbook.view.fragment.AccountFrg;
import com.neos.touristbook.view.fragment.FavouriteFrg;
import com.neos.touristbook.view.fragment.HomeFrg;
import com.neos.touristbook.view.fragment.ReviewFrg;
import com.neos.touristbook.view.fragment.TourFrg;

import java.lang.reflect.Constructor;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements OnActionCallback {
    protected T mPresenter;
    public static DisplayMetrics displayMetrics;

    protected Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            excuteHandler(msg);
            return false;
        }
    });

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
        displayMetrics = new DisplayMetrics();
        getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

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
