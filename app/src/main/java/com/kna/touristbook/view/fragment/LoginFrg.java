package com.kna.touristbook.view.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kna.touristbook.R;
import com.kna.touristbook.event.LoginCallBack;
import com.kna.touristbook.model.Account;
import com.kna.touristbook.presenter.LoginPresenter;
import com.kna.touristbook.presenter.RegisterPresenter;
import com.kna.touristbook.utils.CommonUtils;
import com.kna.touristbook.view.base.BaseFragment;


public class LoginFrg extends BaseFragment<LoginPresenter> implements LoginCallBack, GoogleApiClient.OnConnectionFailedListener {
    public final static String TAG = LoginFrg.class.getName();
    public static final String KEY_TO_REGISTER = "KEY_TO_REGISTER";
    public static final String KEY_LOGIN_SUCCESS = "KEY_LOGIN_SUCCESS";
    public static final String KEY_SHOW_LOADING = "KEY_SHOW_LOADING";
    public static final String KEY_HIDE_LOADING = "KEY_HIDE_LOADING";
    private EditText edtEmail, edtPass;

    @Override
    protected void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_login;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tv_login, this);
        findViewById(R.id.tv_register, this);
        findViewById(R.id.tv_login_fb, this);
        findViewById(R.id.tv_login_gg, this);
        edtEmail = findViewById(R.id.edt_email);
        edtEmail.setOnClickListener(this);
        edtEmail.setCursorVisible(false);
        edtPass = findViewById(R.id.edt_pass);
        updateUI();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            loginSuccess();
        }
    }

    private void updateUI() {
        String data = CommonUtils.getInstance().getValuePref(RegisterPresenter.CURRENT_ACCOUNT, "");
        if (!data.isEmpty()) {
            Account account = new Gson().fromJson(data, new TypeToken<Account>() {
            }.getType());
            edtEmail.setText(account.getEmail());
            edtPass.setText(account.getPassword());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                mCallback.callback(KEY_TO_REGISTER, null);
                break;
            case R.id.tv_login:
                Account account = new Account(edtEmail.getText().toString(), edtPass.getText().toString());
                mPresenter.loginWithAccount(account);
                mCallback.callback(KEY_SHOW_LOADING, null);
                break;
            case R.id.tv_login_fb:
                loginFB();
                break;
            case R.id.tv_login_gg:
                mPresenter.loginGoogle();
                break;
            case R.id.edt_email:
                edtEmail.setCursorVisible(true);
                break;
        }
    }


    private void loginFB() {
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setFragment(this);
    }


    @Override
    public void onStartLoginGG(Intent signInIntent) {
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void loginSuccess() {
        mCallback.callback(KEY_LOGIN_SUCCESS, null);
    }


    @Override
    public void loginFailed() {
        CommonUtils.getInstance().toast("Đăng nhập không thành công!");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCallback.callback(KEY_HIDE_LOADING, null);
            }
        }, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            mPresenter.excuteLoginGG(data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
