package com.kna.touristbook.view.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kna.touristbook.R;
import com.kna.touristbook.event.RegisterCallBack;
import com.kna.touristbook.presenter.RegisterPresenter;
import com.kna.touristbook.utils.CommonUtils;
import com.kna.touristbook.view.base.BaseFragment;

import static com.kna.touristbook.view.fragment.LoginFrg.KEY_HIDE_LOADING;
import static com.kna.touristbook.view.fragment.LoginFrg.KEY_SHOW_LOADING;

public class RegisterFrg extends BaseFragment<RegisterPresenter> implements RegisterCallBack {
    public static final String KEY_REGISTER_SUCCESS = "KEY_REGISTER_SUCCESS";
    private EditText edtEmail, edtPass, edtConfirmPass;
    public final static String TAG = RegisterFrg.class.getName();

    @Override
    protected void initPresenter() {
        mPresenter = new RegisterPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_register;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tv_register, this);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_pass);
        edtConfirmPass = findViewById(R.id.edt_confirm);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_register) {
            mCallback.callback(KEY_SHOW_LOADING,null);

            mPresenter.checkValidation(edtEmail.getText().toString(),
                    edtPass.getText().toString(), edtConfirmPass.getText().toString());

        }
    }

    @Override
    public void error(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        mCallback.callback(KEY_HIDE_LOADING,null);
    }

    @Override
    public void registerSuccess() {
        CommonUtils.getInstance().toast("Đăng kí thành công");
        mCallback.callback(KEY_REGISTER_SUCCESS, null);

    }

    @Override
    public void registerFail() {
        CommonUtils.getInstance().toast("Đăng kí thất bại");
        mCallback.callback(KEY_HIDE_LOADING,null);
    }
}
