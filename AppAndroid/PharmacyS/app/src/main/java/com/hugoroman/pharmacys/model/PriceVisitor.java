package com.hugoroman.pharmacys.model;

public class PriceVisitor extends Visitor {

    private float basketPrice;

    public PriceVisitor() {

        basketPrice = 0.0f;
    }

    @Override
    public void visitInventory(Inventory inventory, int quantity) {

        basketPrice += (quantity * inventory.getPrice());
    }

    public float getBasketPrice() {

        return basketPrice;
    }

    public void resetPrice() {

        basketPrice = 0.0f;
    }
}