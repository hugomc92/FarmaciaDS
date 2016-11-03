package com.hugoroman.pharmacys.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hugoroman.pharmacys.data.PharmacySContract.OrderProductTable;
import com.hugoroman.pharmacys.data.PharmacySContract.OrderTable;
import com.hugoroman.pharmacys.model.Order;
import com.hugoroman.pharmacys.model.Product;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public final class OrderDao {

    public static List<Order> getAllOrders(SQLiteDatabase db, String userEmail) {

        String selectQuery = "SELECT * FROM " + OrderTable.TABLE_NAME + " WHERE " +
                OrderTable.USER_ID + " = '" + userEmail + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        List<Order> orders = new ArrayList<Order>();

        if(c != null && c.moveToFirst()) {
            do {
                Order order = new Order(c.getInt(c.getColumnIndex(OrderTable.ID)),
                                        c.getString(c.getColumnIndex(OrderTable.USER_ID)),
                                        c.getString(c.getColumnIndex(OrderTable.PHARMACY_ID)),
                                        new Date(c.getLong(c.getColumnIndex(OrderTable.DATE))),
                                        c.getFloat(c.getColumnIndex(OrderTable.PRICE)));

                orders.add(order);

            } while(c.moveToNext());

            c.close();
        }

        return orders;
    }

    public static List<List<String>> getOrderInfo(SQLiteDatabase db, int orderId) {

        String selectQuery = "SELECT * FROM " + OrderProductTable.TABLE_NAME + " WHERE " +
                OrderProductTable.ID_ORDER + " = " + orderId;


        Cursor c = db.rawQuery(selectQuery, null);

        List<List<String>> productsQuantity = new ArrayList<List<String>>();

        if(c != null && c.moveToFirst()) {
            do {
                List<String> proQua = new ArrayList<String>();

                String productName = ProductDao.getProductName(db, c.getInt(c.getColumnIndex(OrderProductTable.PRODUCT_ID)));

                proQua.add(productName);
                proQua.add(c.getString(c.getColumnIndex(OrderProductTable.QUANTITY)));

                productsQuantity.add(proQua);
            } while(c.moveToNext());
        }

        if(c != null)
            c.close();

        return productsQuantity;
    }

    public static String getOrderPharmacyId(SQLiteDatabase db, int orderId) {

        String selectQuery = "SELECT " + OrderTable.PHARMACY_ID + " FROM " + OrderTable.TABLE_NAME + " WHERE " +
                OrderTable.ID + " = " + orderId;

        Cursor c = db.rawQuery(selectQuery, null);

        String pharmacyName = null;

        if(c != null) {
            c.moveToFirst();

            pharmacyName = c.getString(c.getColumnIndex(OrderTable.PHARMACY_ID));

            c.close();
        }

        return pharmacyName;
    }

    public static List<Product> getAllOrderProducts(SQLiteDatabase db, int orderId) {

        String selectQuery = "SELECT " + OrderProductTable.PRODUCT_ID + " FROM " + OrderProductTable.TABLE_NAME + " WHERE " +
                OrderProductTable.ID_ORDER + " = " + orderId;

        Cursor c = db.rawQuery(selectQuery, null);

        List<Product> products = new ArrayList<Product>();

        if(c != null && c.moveToFirst()) {
            do {
                final Product product = ProductDao.getProduct(db, c.getInt(c.getColumnIndex(OrderProductTable.PRODUCT_ID)));

                products.add(product);
            } while(c.moveToNext());

            c.close();
        }

        return products;
    }

    public static void addToOrder(SQLiteDatabase db, String userEmail, String pharmacyId, long date, float price, List<Product> products, List<Integer> quantities, boolean onlyLocal, Integer orderLastId) {

        if(!onlyLocal) {
            // Crear el pedido en el servidor
            DBConnectorServer.addToOrder(userEmail, pharmacyId, price, products, quantities);
            Log.e("CREATE ORDER", "ON SERVER");

            // Llamar a la api rest para producir recibo
        }

        ContentValues contentValuesOrder = new ContentValues();

        Integer idOrder;

        if(orderLastId == null)
            idOrder = DBConnectorServer.getLastIdOrder();
        else
            idOrder = orderLastId;

        Log.e("ADD TO ORDER", "LAST ID " + idOrder + " asdFAS");

        contentValuesOrder.put(OrderTable.ID, idOrder);
        contentValuesOrder.put(OrderTable.USER_ID, userEmail);
        contentValuesOrder.put(OrderTable.PHARMACY_ID, pharmacyId);
        contentValuesOrder.put(OrderTable.DATE, date);
        contentValuesOrder.put(OrderTable.PRICE, price);

        long orderId = db.insert(PharmacySContract.OrderTable.TABLE_NAME, null, contentValuesOrder);

        ContentValues contentValuesOrderProduct = new ContentValues();

        for(int i=0; i<products.size(); i++) {
            contentValuesOrderProduct.put(OrderProductTable.ID_ORDER, orderId);
            contentValuesOrderProduct.put(OrderProductTable.PRODUCT_ID, products.get(i).getId());
            contentValuesOrderProduct.put(OrderProductTable.QUANTITY, quantities.get(i));
        }

        db.insert(OrderProductTable.TABLE_NAME, null, contentValuesOrderProduct);
    }

    public static void deleteAllOrders(SQLiteDatabase db) {

        String selectQuery = "SELECT * FROM " + OrderTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null && c.moveToFirst()) {
            do {
                int productId = c.getInt(c.getColumnIndex(OrderTable.ID));

                String selectQueryOrderProduct = "SELECT * FROM " + OrderProductTable.TABLE_NAME + " WHERE " +
                        OrderProductTable.ID_ORDER + " = " + productId;

                Cursor c2 = db.rawQuery(selectQueryOrderProduct, null);

                if (c2 != null && c2.moveToFirst()) {
                    do {
                        db.delete(OrderProductTable.TABLE_NAME, null, null);
                    } while (c2.moveToNext());

                    c2.close();
                }

                db.delete(OrderTable.TABLE_NAME, null, null);

            } while(c.moveToNext());

            c.close();
        }
    }
}