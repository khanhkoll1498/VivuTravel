package com.kna.touristbook.model;

public class ContentReview {
    private String image, content;

    public ContentReview(String image, String content) {
        this.image = image;
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }
}
