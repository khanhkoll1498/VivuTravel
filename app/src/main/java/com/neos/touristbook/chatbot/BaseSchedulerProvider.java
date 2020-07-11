package com.neos.touristbook.chatbot;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {
    Scheduler io();

    Scheduler ui();
}
