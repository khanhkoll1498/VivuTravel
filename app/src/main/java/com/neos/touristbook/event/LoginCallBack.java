package com.neos.touristbook.event;

import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;

public interface LoginCallBack extends OnCallback {
    void onStartLoginGG(Intent signInIntent);

    void loginSuccess();

    void loginFailed();

}
