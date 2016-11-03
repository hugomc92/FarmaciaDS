package com.hugoroman.pharmacys.model;

import java.util.ArrayList;
import java.util.List;

public class Reservation {

    private List<List<Object>> productsPharmaciesQuantities;

    public Reservation(List<List<Object>> productsPharmaciesQuantities) {

        this.productsPharmaciesQuantities = productsPharmaciesQuantities;
    }

    public Reservation(Reservation reservation) {

        this.productsPharmaciesQuantities = new ArrayList<List<Object>>();

        for(int i=0; i<reservation.getProductsPharmaciesQuantities().size(); i++) {

            this.productsPharmaciesQuantities.add(reservation.getProductsPharmaciesQuantities().get(i));
        }
    }

    public List<List<Object>> getProductsPharmaciesQuantities() {

        return productsPharmaciesQuantities;
    }
}