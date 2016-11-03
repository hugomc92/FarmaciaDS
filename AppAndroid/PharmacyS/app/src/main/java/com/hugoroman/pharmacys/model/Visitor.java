package com.hugoroman.pharmacys.model;

public abstract class Visitor {

    public abstract void visitInventory(Inventory inventory, int quantity);
}