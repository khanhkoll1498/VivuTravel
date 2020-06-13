package com.neos.touristbook.model;

public class Account {
    private String email, password;

    public Account(String email, String password) {
        this.email = "";
        this.password = "";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
