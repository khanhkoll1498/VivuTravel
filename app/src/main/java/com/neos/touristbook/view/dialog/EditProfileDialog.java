package com.neos.touristbook.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.neos.touristbook.R;
import com.neos.touristbook.event.AccountCallback;
import com.neos.touristbook.model.Image;
import com.neos.touristbook.model.User;
import com.neos.touristbook.presenter.AccoutPresenter;
import com.neos.touristbook.view.base.BaseDialog;
import com.neos.touristbook.view.event.OnActionCallback;


public class EditProfileDialog extends BaseDialog<AccoutPresenter> implements OnActionCallback, AccountCallback {

    public static final String KEY_UPDATE = "KEY_UPDATE";
    private ImageView ivAvatar;
    private EditText edtName, edtShownName, edtEmail, edtPhone;
    private String urlImage = "";

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_edit_profile;
    }

    public EditProfileDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new AccoutPresenter(this);
    }

    @Override
    protected void initView() {
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar, this);
        findViewById(R.id.tv_update_profile, this);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_avatar) {
            pickImage();
        } else {
            updateProfile();
        }
    }

    private void updateProfile() {
        User user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), edtName.getText().toString()
                , urlImage, edtEmail.getText().toString()
                , edtPhone.getText().toString());
        mPresenter.updateProfile(user);

    }

    @Override
    public void onResultUser(User user) {
        if (user==null){
            mCallback.callback(KEY_UPDATE,null);
            dismiss();
        }
    }

    private void pickImage() {
        PickImageDialog dialog = new PickImageDialog(getContext(), R.style.AppTheme);
        dialog.setmCallback(this);
        dialog.show();
    }

    @Override
    public void callback(String key, Object data) {
        if (key.equals("KEY_AVATAR")) {
            Image image = (Image) data;
            urlImage = image.getImage();
            Glide.with(getContext()).load(image.getImage()).into(ivAvatar);
        }
    }

    public void updateUser(User mUser) {
        if (mUser != null) {
            urlImage = mUser.getImage();
            Glide.with(getContext()).load("file://" + mUser.getImage()).into(ivAvatar);
            edtName.setText(mUser.getName());
            edtPhone.setText(mUser.getPhone());
            edtEmail.setText(mUser.getEmail());
        }
    }
}
