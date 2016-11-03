package com.hugoroman.pharmacys.model;

import java.util.List;

public class Basket {

    private List<List<Object>> productsPharmaciesQuantities;

    public Basket(List<List<Object>> productsPharmaciesQuantities) {

        this.productsPharmaciesQuantities = productsPharmaciesQuantities;
    }

    public List<List<Object>> getProductsPharmaciesQuantities() {

        return productsPharmaciesQuantities;
    }
}