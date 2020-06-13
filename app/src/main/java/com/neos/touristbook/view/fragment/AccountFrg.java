package com.neos.touristbook.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neos.touristbook.R;
import com.neos.touristbook.event.AccountCallback;
import com.neos.touristbook.model.User;
import com.neos.touristbook.presenter.AccoutPresenter;
import com.neos.touristbook.view.base.BaseFragment;
import com.neos.touristbook.view.dialog.BookedTourDialog;
import com.neos.touristbook.view.dialog.EditProfileDialog;
import com.neos.touristbook.view.event.OnActionCallback;

import static com.neos.touristbook.view.dialog.EditProfileDialog.KEY_UPDATE;

public class AccountFrg extends BaseFragment<AccoutPresenter> implements AccountCallback, OnActionCallback {
    public static final String KEY_SIGN_OUT = "KEY_SIGN_OUT";
    public static String TAG = AccountFrg.class.getName();
    private User mUser;

    @Override
    protected void initPresenter() {
        mPresenter = new AccoutPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_account;
    }

    private ImageView ivAvatar;
    private TextView tvName, tvShownName, tvEmail, tvPhone;

    @Override
    protected void initView() {
        findViewById(R.id.rl_sign_out, this);
        findViewById(R.id.rl_edit, this);
        findViewById(R.id.rl_booked_tour, this);
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar, this);
        tvShownName = (TextView) findViewById(R.id.tv_shown_name);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        mPresenter.loadProfile();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sign_out:
                mCallback.callback(KEY_SIGN_OUT, null);
                break;
            case R.id.rl_edit:
                updateProfile();
                break;
            case R.id.rl_booked_tour:
                showBookedTour();
                break;
        }
    }

    private void showBookedTour() {
        BookedTourDialog dialog = new BookedTourDialog(getContext(), R.style.AppTheme);
        dialog.show();
    }

    private void updateProfile() {
        EditProfileDialog dialog = new EditProfileDialog(getContext(), R.style.AppTheme);
        dialog.updateUser(mUser);
        dialog.setmCallback(this);
        dialog.show();
    }


    @Override
    public void onResultUser(User user) {
        try {
            this.mUser = user;
            Glide.with(getContext()).load(user.getImage()).into(ivAvatar);
            tvName.setText(user.getName());
            tvShownName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            tvPhone.setText(user.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(String key, Object data) {
        if (key.equals(KEY_UPDATE)) {
            mPresenter.loadProfile();
        }
    }
}
