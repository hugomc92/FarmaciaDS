package com.hugoroman.pharmacys.model;


public class Pharmacy {

    private String cif;
    private String name;
    private Integer phoneNumber;
    private String description;
    private Integer startSchedule;
    private Integer endSchedule;
    private double latitude;
    private double longitude;
    private String address;
    private String logo;

    public Pharmacy(String cif, String name, Integer phoneNumber, String description, Integer startSchedule, Integer endSchedule, double latitude, double longitude, String address, String logo) {

        this.cif = cif;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.logo = logo;
    }

    public String getCif() {
        return cif;
    }

    public String getName() {
        return name;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStartSchedule() {
        return startSchedule;
    }

    public Integer getEndSchedule() {
        return endSchedule;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getLogo() {
        return logo;
    }
}