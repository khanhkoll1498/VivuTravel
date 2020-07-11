package com.neos.touristbook.chatbot;

import com.google.gson.annotations.SerializedName;

public class BodyRequest {
    @SerializedName("content")
    private String content;
    @SerializedName("save_history")
    private boolean saveHistory;

    public BodyRequest(String content, boolean saveHistory) {
        this.content = content;
        this.saveHistory = saveHistory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
