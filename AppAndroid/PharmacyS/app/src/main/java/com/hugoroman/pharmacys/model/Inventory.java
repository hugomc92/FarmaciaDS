package com.hugoroman.pharmacys.model;


public class Inventory {

    private String pharmacyID;
    private int productID;
    private float price;
    private int stock;

    public Inventory(String pharmacyID, int productID, float price, int stock) {
        this.pharmacyID = pharmacyID;
        this.productID = productID;
        this.price = price;
        this.stock = stock;
    }

    public String getPharmacyID() {
        return pharmacyID;
    }

    public int getProductID() {
        return productID;
    }

    public float getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void acceptVisitor(Visitor visitor, int quantity) {

        visitor.visitInventory(this, quantity);
    }
}
