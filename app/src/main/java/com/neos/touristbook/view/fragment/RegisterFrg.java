package com.neos.touristbook.view.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.neos.touristbook.R;
import com.neos.touristbook.event.RegisterCallBack;
import com.neos.touristbook.presenter.RegisterPresenter;
import com.neos.touristbook.utils.CommonUtils;
import com.neos.touristbook.view.base.BaseFragment;

public class RegisterFrg extends BaseFragment<RegisterPresenter> implements RegisterCallBack {
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
        findViewById(R.id.bt_register, this);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_pass);
        edtConfirmPass = findViewById(R.id.edt_confirm_pass);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_register) {
            mPresenter.checkValidation(edtEmail.getText().toString(),
                    edtPass.getText().toString(), edtConfirmPass.getText().toString());
        }
    }

    @Override
    public void error(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSuccess() {
        CommonUtils.getInstance().toast("Đăng kí thành công");
    }

    @Override
    public void registerFail() {
        CommonUtils.getInstance().toast("Đăng kí thất bại");
    }
}
