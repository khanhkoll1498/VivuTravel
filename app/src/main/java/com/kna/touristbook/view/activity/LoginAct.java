package com.kna.touristbook.view.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kna.touristbook.R;
import com.kna.touristbook.model.User;
import com.kna.touristbook.model.UserFB;
import com.kna.touristbook.utils.CommonUtils;
import com.kna.touristbook.view.base.BaseActivity;
import com.kna.touristbook.view.fragment.LoginFrg;
import com.kna.touristbook.view.fragment.RegisterFrg;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kna.touristbook.view.fragment.LoginFrg.KEY_HIDE_LOADING;
import static com.kna.touristbook.view.fragment.LoginFrg.KEY_LOGIN_SUCCESS;
import static com.kna.touristbook.view.fragment.LoginFrg.KEY_SHOW_LOADING;
import static com.kna.touristbook.view.fragment.RegisterFrg.KEY_REGISTER_SUCCESS;

public class LoginAct extends BaseActivity {
    private String currentTag = LoginFrg.TAG;
    public static CallbackManager callbackManager;

    @Override
    protected int getLayoutId() {
        return R.layout.act_login;
    }


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        showFragment(currentTag);
        initLoginFB();
    }

    private void initLoginFB() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        showLoading();
                        FirebaseAuth.getInstance().signInWithCredential(FacebookAuthProvider.getCredential(accessToken.getToken()))
                                .addOnCompleteListener(LoginAct.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(LoginAct.this,
                                                    "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            hideLoading();
                                            return;
                                        }
                                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                            saveUser(FirebaseAuth.getInstance().getCurrentUser());
                                        }
                                        startActivity(new Intent(LoginAct.this, MainActivity.class));
                                        finish();
                                    }
                                });
                    }

                    @Override
                    public void onCancel() {
                        CommonUtils.getInstance().toast("onCancel");

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        CommonUtils.getInstance().toast("onError" + exception);
                    }
                });
    }


    private void saveUser(FirebaseUser data) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        User user = new User(data.getUid(), data.getDisplayName(), data.getPhotoUrl().toString(), data.getEmail(), "");
        myRef.child("user").child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }


    private void excuteLoadInfo(AccessToken accessToken) {
        final GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        UserFB userFB = new Gson().fromJson(object.toString(), new TypeToken<UserFB>() {
                        }.getType());
                        User user = new User(userFB.getId(), userFB.getName(), userFB.getPicture().getData().getUrl(), "", "");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();
                        myRef.child("user").child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(LoginAct.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
                    }
                });

        final Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,id,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void callback(String key, Object data) {
        switch (key) {
            case LoginFrg.KEY_TO_REGISTER:
                currentTag = RegisterFrg.TAG;
                showFragment(currentTag);
                break;
            case KEY_REGISTER_SUCCESS:
                currentTag = LoginFrg.TAG;
                showFragment(currentTag);
                break;
            case KEY_LOGIN_SUCCESS:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case KEY_SHOW_LOADING:
                showLoading();
                break;
            case KEY_HIDE_LOADING:
                hideLoading();
                break;
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
