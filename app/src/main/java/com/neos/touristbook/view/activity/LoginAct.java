package com.neos.touristbook.view.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.neos.touristbook.R;
import com.neos.touristbook.view.base.BaseActivity;
import com.neos.touristbook.view.fragment.LoginFrg;
import com.neos.touristbook.view.fragment.RegisterFrg;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginAct extends BaseActivity {
    private String currentTag = LoginFrg.TAG;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {

//        PackageInfo info;
//        try {
//            info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                //String something = new String(Base64.encodeBytes(md.digest()));
//                Log.e("hash key", something);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("name not found", e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("no such an algorithm", e.toString());
//        } catch (Exception e) {
//            Log.e("exception", e.toString());
//        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

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
        }
    }

    @Override
    public void onBackPressed() {
        if (currentTag.equals(LoginFrg.TAG)){
            super.onBackPressed();
            return;
        }
        currentTag = LoginFrg.TAG;
        showFragment(currentTag);
    }
}
