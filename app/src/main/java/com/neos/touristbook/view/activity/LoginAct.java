package com.neos.touristbook.view.activity;

import android.content.Intent;

import com.neos.touristbook.R;
import com.neos.touristbook.view.base.BaseActivity;
import com.neos.touristbook.view.fragment.LoginFrg;
import com.neos.touristbook.view.fragment.RegisterFrg;

import static com.neos.touristbook.view.fragment.LoginFrg.KEY_LOGIN_SUCCESS;
import static com.neos.touristbook.view.fragment.RegisterFrg.KEY_REGISTER_SUCCESS;

public class LoginAct extends BaseActivity {
    private String currentTag = LoginFrg.TAG;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        showFragment(currentTag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    public void callback(String key, Object data) {
        if (key.equals(LoginFrg.KEY_TO_REGISTER)) {
            currentTag = RegisterFrg.TAG;
            showFragment(currentTag);
        } else if (key.equals(KEY_REGISTER_SUCCESS)) {
            currentTag = LoginFrg.TAG;
            showFragment(currentTag);
        } else if (key.equals(KEY_LOGIN_SUCCESS)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (currentTag.equals(LoginFrg.TAG)) {
            super.onBackPressed();
            return;
        }
        currentTag = LoginFrg.TAG;
        showFragment(currentTag);
    }
}
