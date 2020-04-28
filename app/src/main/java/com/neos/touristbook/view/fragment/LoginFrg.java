package com.neos.touristbook.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neos.touristbook.R;
import com.neos.touristbook.event.LoginCallBack;
import com.neos.touristbook.model.User;
import com.neos.touristbook.presenter.LoginPresenter;
import com.neos.touristbook.utils.CommonUtils;
import com.neos.touristbook.view.activity.MainActivity;
import com.neos.touristbook.view.base.BaseFragment;

import java.util.Arrays;


public class LoginFrg extends BaseFragment<LoginPresenter> implements LoginCallBack {
    private static final String KEY_LOG_IN_GOOGLE = "KEY_LOG_IN_GOOGLE";
    private static final String KEY_LOG_IN_FACEBOOK = "KEY_LOG_IN_FACEBOOK";
    public final static String TAG = LoginFrg.class.getName();
    public static final String KEY_TO_REGISTER = "KEY_TO_REGISTER";
    private CallbackManager mCallbackManager;

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
        findViewById(R.id.iv_google, this);
        findViewById(R.id.iv_facebook, this);
        findViewById(R.id.tv_sign_up, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_sign_up) {
            mCallback.callback(KEY_TO_REGISTER, null);
        } else if (v.getId() == R.id.iv_google) {
            mPresenter.loginGoogle();
        } else if (v.getId() == R.id.iv_facebook) {
            excuteLoginFb();
        }
    }

    private static final String EMAIL = "email";

    private void excuteLoginFb() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                CommonUtils.getInstance().log(error.getMessage());
            }
        });
    }


    private void handleFacebookAccessToken(AccessToken token) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                        }
                    }
                });
    }


    @Override
    public void onStartLoginGG(Intent signInIntent) {
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void loginGGSuccess() {

    }


    @Override
    public void loginGGFailed() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 101) {
            mPresenter.excuteLoginGG(data);
        }
    }

}
