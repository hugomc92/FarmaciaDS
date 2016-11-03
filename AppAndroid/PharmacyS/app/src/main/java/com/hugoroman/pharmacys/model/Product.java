package com.hugoroman.pharmacys.model;

import java.sql.Date;

public class Product {

    private int id;
    private int category;
    private String name;
    private String description;
    private String laboratory;
    private String units;
    private Date expiration_date;
    private int sizeUnits;
    private String lot;
    private String urlImage;


    public Product(int id, int category, String name, String description, String laboratory, String units, Date expiration_date, int sizeUnits, String lot, String urlImage) {

        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.laboratory = laboratory;
        this.units = units;
        this.expiration_date = expiration_date;
        this.sizeUnits = sizeUnits;
        this.lot = lot;
        this.urlImage = urlImage;
    }

    public int getId() {
        return id;
    }

    public int getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public String getUnits() {
        return units;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public int getSizeUnits() {
        return sizeUnits;
    }

    public String getLot() {
        return lot;
    }

    public String getUrlImage() {
        return urlImage;
    }
}