package com.kna.touristbook.model;

public class User {
    private String id, name;
    private String image, email, phone;

    public User() {
    }

    public User(String id, String name, String image, String email, String phone) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.email = email;
        this.phone = phone;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
