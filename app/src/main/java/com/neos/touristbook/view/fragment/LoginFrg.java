package com.neos.touristbook.view.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neos.touristbook.R;
import com.neos.touristbook.event.LoginCallBack;
import com.neos.touristbook.model.Account;
import com.neos.touristbook.presenter.LoginPresenter;
import com.neos.touristbook.presenter.RegisterPresenter;
import com.neos.touristbook.utils.CommonUtils;
import com.neos.touristbook.view.activity.LoginAct;
import com.neos.touristbook.view.activity.MainActivity;
import com.neos.touristbook.view.base.BaseFragment;


public class LoginFrg extends BaseFragment<LoginPresenter> implements LoginCallBack, GoogleApiClient.OnConnectionFailedListener {
    public final static String TAG = LoginFrg.class.getName();
    public static final String KEY_TO_REGISTER = "KEY_TO_REGISTER";
    public static final String KEY_LOGIN_SUCCESS = "KEY_LOGIN_SUCCESS";
    public static final String KEY_SHOW_LOADING = "KEY_SHOW_LOADING";
    public static final String KEY_HIDE_LOADING = "KEY_HIDE_LOADING";
    private EditText edtEmail, edtPass;
    private GoogleSignInClient googleSignInClient;

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
            mCallback.callback(KEY_SHOW_LOADING, null);
        } else if (v.getId() == R.id.tv_login_fb) {
            loginFB();
        } else if (v.getId() == R.id.tv_login_gg) {
            mPresenter.loginGoogle();
//            logInGG();
        }
    }

    private void logInGG() {
        SignInButton googleSignInButton = findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1044263490886-psff6qlvp0unldbcdcbf2utn0h3sm82e.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(context, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });

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
        mCallback.callback(KEY_HIDE_LOADING, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            mPresenter.excuteLoginGG(data);
        }
//
//        try {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            GoogleSignInAccount account = task.getResult(ApiException.class);
////            onLoggedIn(account);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
