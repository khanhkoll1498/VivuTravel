package com.kna.touristbook.event;

public interface RegisterCallBack  extends OnCallback{
    void error(String str);

    void registerSuccess();

    void registerFail();
}
