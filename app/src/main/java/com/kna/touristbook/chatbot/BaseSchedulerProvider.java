package com.kna.touristbook.chatbot;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {
    Scheduler io();

    Scheduler ui();
}
