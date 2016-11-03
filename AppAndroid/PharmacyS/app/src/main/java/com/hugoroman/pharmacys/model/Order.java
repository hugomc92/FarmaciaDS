package com.hugoroman.pharmacys.model;

import java.sql.Date;

public class Order {

    private int id;
    private String userId;
    private String pharmacyId;
    private Date date;
    private float price;

    public Order(int id, String userId, String pharmacyId, Date date, float price) {

        this.id = id;
        this.userId = userId;
        this.pharmacyId = pharmacyId;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public Date getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }
}
