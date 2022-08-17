package com.kna.touristbook.event;

import com.kna.touristbook.model.User;

public interface AccountCallback extends OnCallback {
    default void onResultUser(User user) {

    }
}
