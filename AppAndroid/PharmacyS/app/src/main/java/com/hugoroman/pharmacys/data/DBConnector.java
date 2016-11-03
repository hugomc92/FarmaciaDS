package com.hugoroman.pharmacys.data;

import android.app.Activity;
import android.content.Context;

import com.hugoroman.pharmacys.model.Basket;
import com.hugoroman.pharmacys.model.Inventory;
import com.hugoroman.pharmacys.model.Order;
import com.hugoroman.pharmacys.model.Pharmacy;
import com.hugoroman.pharmacys.model.Product;
import com.hugoroman.pharmacys.model.Reservation;
import com.hugoroman.pharmacys.model.User;

import java.util.List;

public class DBConnector {

    private DBPharmacyS pharmacyS;

    public DBConnector(Context context) {

        this.pharmacyS = new DBPharmacyS(context);
    }

    public Pharmacy getPharmacy(String pharmacyId) {

        return pharmacyS.getPharmacy(pharmacyId);
    }

    public List<Pharmacy> getAllPharmacies() {

        return pharmacyS.getAllPharmacies();
    }

    public String getPharmacyName(String pharmacyId) {

        return pharmacyS.getPharmacyName(pharmacyId);
    }

    public List<Inventory> getPharmacyInventory(String pharmacyId) {

        return pharmacyS.getPharmacyInventory(pharmacyId);
    }

    public Product getProduct(int productId) {

        return pharmacyS.getProduct(productId);
    }

    public String getProductCategoryName(int idProduct) {

        return pharmacyS.getProductCategoryName(idProduct);
    }

    public int getProductCategoryPhoto(int idProduct) {

        return pharmacyS.getProductCategoryPhoto(idProduct);
    }

    public int getProductCategoryId(int idProduct) {

        return pharmacyS.getProductCategoryId(idProduct);
    }

    public List<Product> getAllProductsByCategoryId(int categoryId, String pharmacyId) {

        return pharmacyS.getAllProductsByCategoryId(categoryId, pharmacyId);
    }

    public int getInventoryQuantity(String pharmacyId, int productId) {

        return pharmacyS.getInventoryQuantity(pharmacyId, productId);
    }

    public Basket getBasket() {

        return pharmacyS.getBasket();
    }

    public void addToBasket(String pharmacyId, int productId, int quantity) {

        pharmacyS.addToBasket(pharmacyId, productId, quantity);
    }

    public void removeFromBasket(String pharmacyId, int productId) {

        pharmacyS.removeFromBasket(pharmacyId, productId);
    }

    public Reservation getReservation() {

        return pharmacyS.getReservation();
    }

    public void addToReservation(String pharmacyId, int productId, int quantity, String userEmail) {

        pharmacyS.addToReservation(pharmacyId, productId, quantity, userEmail);
    }

    public void removeFromReservation(String pharmacyId, int productId, String userEmail, boolean onlyLocal) {

        pharmacyS.removeFromReservation(pharmacyId, productId, userEmail, onlyLocal);
    }

    public Inventory getInventory(String pharmacyId, int productId) {

        return pharmacyS.getInventory(pharmacyId, productId);
    }

    public String getCategoryName(int categoryId) {

        return pharmacyS.getCategoryName(categoryId);
    }

    public User getUser(String userEmail) {

        return pharmacyS.getUser(userEmail);
    }

    public List<Order> getAllOrders(String userEmail) {

        return pharmacyS.getAllOrders(userEmail);
    }

    public List<List<String>> getOrderInfo(int orderId) {

        return pharmacyS.getOrderInfo(orderId);
    }

    public String getOrderPharmacyId(int orderId) {

        return pharmacyS.getOrderPharmacyId(orderId);
    }

    public List<Product> getAllOrderProducts(int orderId) {

        return pharmacyS.getAllOrderProducts(orderId);
    }

    public float getProductPrice(String pharmacyId, int productId) {

        return pharmacyS.getProductPrice(pharmacyId, productId);
    }

    public Basket getPharmacyBasket(String pharmacyId) {

        return pharmacyS.getPharmacyBasket(pharmacyId);
    }

    public void addToOrder(String userEmail, String pharmacyId, long date, float price, List<Product> products, List<Integer> quantities, boolean onlyLocal, Integer orderLastId) {

        pharmacyS.addToOrder(userEmail, pharmacyId, date, price, products, quantities, onlyLocal, orderLastId);
    }

    public void addUser(Activity activity, String name, String surname, String email, String password, boolean onlyLocal) {

        pharmacyS.addUser(activity, name, surname, email, password, onlyLocal);
    }

    public void addPharmacy(String pharmacyCif, String pharmacyName, int pharmacyPhone, String pharmacyDescription, int pharmacyStart, int pharmacyEnd,
                            double pharmacyLatitude, double pharmacyLongitude, String pharmacyAddress, String pharmacyLogo) {

        pharmacyS.addPharmacy(pharmacyCif, pharmacyName, pharmacyPhone, pharmacyDescription, pharmacyStart, pharmacyEnd, pharmacyLatitude,
                pharmacyLongitude, pharmacyAddress, pharmacyLogo);
    }

    public void addProduct(int productId, String productName, int productCategory, String productDescription, String productLaboratoy, String productUnits, long productExpDate,
                           int productSize, String productLot, String productPhoto) {

        pharmacyS.addProduct(productId, productName, productCategory, productDescription, productLaboratoy, productUnits, productExpDate, productSize, productLot, productPhoto);
    }

    public void addInventory(String pharmacyId, int productId, float productPrice, int stock) {

        pharmacyS.addInventory(pharmacyId, productId, productPrice, stock);
    }

    public void deleteAllProducts() {

        pharmacyS.deleteAllProducts();
    }

    public void deleteAllInventories() {

        pharmacyS.deleteAllInventories();
    }

    public void deleteAllOrders() {

        pharmacyS.deleteAllOrders();
    }

    public void deleteBasket() {

        pharmacyS.deleteBasket();
    }

    public void deleteAllReservations(String userEmail) {

        pharmacyS.deleteAllReservations(userEmail);
    }

    public boolean existsProduct(int productId) {

        return pharmacyS.existsProduct(productId);
    }

    public void updateStock(String pharmacyId, int productId, int quantity) {

        pharmacyS.updateStock(pharmacyId, productId, quantity);
    }
}