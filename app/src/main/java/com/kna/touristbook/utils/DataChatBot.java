package com.kna.touristbook.utils;

import android.content.SharedPreferences;

import com.kna.touristbook.App;

import static android.content.Context.MODE_PRIVATE;

public class DataChatBot {
    private static final String CHAT_BOT_ANSWER = "CHAT_BOT_ANSWER";
    public static DataChatBot instance;

    private DataChatBot() {
        SharedPreferences.Editor editor = App.getInstance().getSharedPreferences(CHAT_BOT_ANSWER, MODE_PRIVATE).edit();
        editor.putString("du_lich", "Du lịch là một ý tưởng tuyệt vời");
        editor.putString("xin_chao", "VivuTravel xin chào");
        editor.putString("cam_on", "Chúc bạn có một chuyến đi tốt đẹp");
        editor.apply();
    }

    public static DataChatBot getInstance() {
        if (instance == null) {
            instance = new DataChatBot();
        }
        return instance;
    }

    public String getData(String key) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(CHAT_BOT_ANSWER, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
