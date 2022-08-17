package com.kna.touristbook.model;

import java.text.DecimalFormat;

public class TourOrder {
    private long id;
    private String name;
    private String email, phone;
    private Tour tour;
    private String address;
    private int numPerson;
    private boolean isRate;

    public boolean isRate() {
        if ((System.currentTimeMillis() - id) >2*1000) {
            return true;
        }
        return false;
    }

    public TourOrder(long id, Tour tour, String name, String address, String email, String phone, int numPerson) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.tour = tour;
        this.numPerson = numPerson;
    }

    public long getId() {
        return id;
    }

    public String getTotalPrice() {
        String data = tour.getPrice().replace(" ", "")
                .replace("VND", "")
                .replace(".", "");
        long totalPrice = 0;
        try {
            totalPrice = Long.valueOf(data) * numPerson;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(totalPrice) + " VNĐ";
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
