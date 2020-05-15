package com.neos.touristbook.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neos.touristbook.R;
import com.neos.touristbook.event.LoginCallBack;
import com.neos.touristbook.model.Account;
import com.neos.touristbook.presenter.LoginPresenter;
import com.neos.touristbook.presenter.RegisterPresenter;
import com.neos.touristbook.utils.CommonUtils;
import com.neos.touristbook.view.base.BaseFragment;


public class LoginFrg extends BaseFragment<LoginPresenter> implements LoginCallBack {
    public final static String TAG = LoginFrg.class.getName();
    public static final String KEY_TO_REGISTER = "KEY_TO_REGISTER";
    public static final String KEY_LOGIN_SUCCESS = "KEY_LOGIN_SUCCESS";
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
        edtEmail = findViewById(R.id.edt_email);
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
        if (v.getId() == R.id.tv_register) {
            mCallback.callback(KEY_TO_REGISTER, null);
        } else if (v.getId() == R.id.tv_login) {
            Account account = new Account(edtEmail.getText().toString(), edtPass.getText().toString());
            mPresenter.loginWithAccount(account);
        }
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            mPresenter.excuteLoginGG(data);
        }
    }

}
