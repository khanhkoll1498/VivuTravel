package com.neos.touristbook.model;

public class User {
    private String id, name;
    private String image, email, phone;
    private String idTour;
    private String address;
    private int numPerson;


    public User(String id, String name, String image, String email, String phone) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.email = email;
        this.phone = phone;
    }

    public User(String idTour, String name, String address, String email, String phone, int numPerson) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.idTour = idTour;
        this.numPerson = numPerson;
    }

    public String getIdTour() {
        return idTour;
    }

    public String getAddress() {
        return address;
    }

    public int getNumPerson() {
        return numPerson;
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
