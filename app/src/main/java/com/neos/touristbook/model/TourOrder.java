package com.neos.touristbook.model;

public class TourOrder {
    private String name;
    private String email, phone;
    private Tour tour;
    private String address;
    private int numPerson;

    public TourOrder(Tour tour, String name, String address, String email, String phone, int numPerson) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.tour = tour;
        this.numPerson = numPerson;
    }

    public Tour getTour() {
        return tour;
    }

    public String getAddress() {
        return address;
    }

    public int getNumPerson() {
        return numPerson;
    }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
