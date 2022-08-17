package com.kna.touristbook.event;

import android.content.Intent;

public interface LoginCallBack extends OnCallback {
    void onStartLoginGG(Intent signInIntent);

    void loginSuccess();

    void loginFailed();

}
