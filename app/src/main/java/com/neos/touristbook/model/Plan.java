package com.neos.touristbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan {
    @SerializedName("content")
    @Expose
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
