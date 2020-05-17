package com.neos.touristbook.event;

import com.neos.touristbook.model.User;

public interface AccountCallback extends OnCallback {
    default void onResultUser(User user) {

    }
}
