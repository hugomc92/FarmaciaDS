package com.hugoroman.pharmacys.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hugoroman.pharmacys.model.Inventory;
import com.hugoroman.pharmacys.model.Product;

import java.util.ArrayList;
import java.util.List;

public final class InventoryDao {

    public static List<Inventory> getPharmacyInventory(SQLiteDatabase db, String pharmacyId) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.InventoryTable.TABLE_NAME + " WHERE "
                + PharmacySContract.InventoryTable.PHARMACY_ID + " = '" + pharmacyId + "'";

        //Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        List<Inventory> inventories = new ArrayList<Inventory>();

        if(c != null && c.moveToFirst()) {
            do {
                Inventory inventory = new Inventory(c.getString(c.getColumnIndex(PharmacySContract.InventoryTable.PHARMACY_ID)),
                                                    c.getInt(c.getColumnIndex(PharmacySContract.InventoryTable.PRODUCT_ID)),
                                                    c.getFloat(c.getColumnIndex(PharmacySContract.InventoryTable.PRICE)),
                                                    c.getInt(c.getColumnIndex(PharmacySContract.InventoryTable.QUANTITY)));

                inventories.add(inventory);
            } while(c.moveToNext());

            c.close();
        }

        return inventories;
    }

    public static int getInventoryQuantity(SQLiteDatabase db, String pharmacyId, int productId) {

        String selectQuery = "SELECT " + PharmacySContract.InventoryTable.QUANTITY + " FROM " + PharmacySContract.InventoryTable.TABLE_NAME + " WHERE " +
                PharmacySContract.InventoryTable.PHARMACY_ID + " = '" + pharmacyId + "' AND " + PharmacySContract.InventoryTable.PRODUCT_ID + " = " + productId;

        Cursor c = db.rawQuery(selectQuery, null);

        int quantity = 0;

        if(c.moveToFirst())
            quantity = c.getInt(c.getColumnIndex(PharmacySContract.InventoryTable.QUANTITY));


        c.close();

        return quantity;
    }

    public static Inventory getInventory(SQLiteDatabase db, String pharmacyId, int productId) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.InventoryTable.TABLE_NAME + " WHERE " +
                PharmacySContract.InventoryTable.PHARMACY_ID + " = '" + pharmacyId + "' AND " + PharmacySContract.InventoryTable.PRODUCT_ID + " = " + productId;

        Cursor c = db.rawQuery(selectQuery, null);

        Inventory inventory = null;

        if(c != null) {
            c.moveToFirst();

            inventory = new Inventory(c.getString(c.getColumnIndex(PharmacySContract.InventoryTable.PHARMACY_ID)),
                                        c.getInt(c.getColumnIndex(PharmacySContract.InventoryTable.PRODUCT_ID)),
                                        c.getFloat(c.getColumnIndex(PharmacySContract.InventoryTable.PRICE)),
                                        c.getInt(c.getColumnIndex(PharmacySContract.InventoryTable.QUANTITY)));

            c.close();
        }

        return inventory;
    }

    public static List<Product> getAllProductsByCategoryId(SQLiteDatabase db, int categoryId, String pharmacyId) {

        String selectQuery = "SELECT " + PharmacySContract.InventoryTable.PRODUCT_ID + " FROM " + PharmacySContract.InventoryTable.TABLE_NAME + " WHERE " +
                PharmacySContract.InventoryTable.PHARMACY_ID + " = '" + pharmacyId + "'";

        List<Product> products = new ArrayList<Product>();

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null && c.moveToFirst()) {
            do {
                final Product product = ProductDao.getProduct(db, c.getInt(c.getColumnIndex(PharmacySContract.InventoryTable.PRODUCT_ID)));

                if(product.getCategory() == categoryId)
                    products.add(product);

            } while(c.moveToNext());
        }

        if(c != null)
            c.close();

        return products;
    }

    public static float getProductPrice(SQLiteDatabase db, String pharmacyId, int productId) {

        String selectQuery = "SELECT " + PharmacySContract.InventoryTable.PRICE + " FROM " + PharmacySContract.InventoryTable.TABLE_NAME + " WHERE " +
                PharmacySContract.InventoryTable.PHARMACY_ID + " = '" + pharmacyId + "' AND " + PharmacySContract.InventoryTable.PRODUCT_ID + " = " + productId;

        Cursor c = db.rawQuery(selectQuery, null);

        float productPrice = 0.0f;

        if(c != null) {
            c.moveToFirst();

            productPrice = c.getFloat(c.getColumnIndex(PharmacySContract.InventoryTable.PRICE));

            c.close();
        }

        return productPrice;
    }

    public static void addInventory(SQLiteDatabase db, String pharmacyId, int productId, float productPrice, int stock) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.InventoryTable.TABLE_NAME + " WHERE " +
                PharmacySContract.InventoryTable.PHARMACY_ID + " = '" + pharmacyId + "' AND " + PharmacySContract.InventoryTable.PRODUCT_ID + " = " + productId;

        Cursor c = db.rawQuery(selectQuery, null);

        ContentValues contentValues = new ContentValues();

        if(c != null) {
            boolean found = c.moveToFirst();

            c.close();

            contentValues.put(PharmacySContract.InventoryTable.PHARMACY_ID, pharmacyId);
            contentValues.put(PharmacySContract.InventoryTable.PRODUCT_ID, productId);
            contentValues.put(PharmacySContract.InventoryTable.PRICE, productPrice);
            contentValues.put(PharmacySContract.InventoryTable.QUANTITY, stock);

            if(found)
                db.delete(PharmacySContract.InventoryTable.TABLE_NAME, null, null);

            db.insert(PharmacySContract.InventoryTable.TABLE_NAME, null, contentValues);
        }
    }

    public static void deleteAllInventories(SQLiteDatabase db) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.InventoryTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null && c.moveToFirst()) {
            do {
                db.delete(PharmacySContract.InventoryTable.TABLE_NAME, null, null);
            } while(c.moveToNext());

            c.close();
        }
    }

    public static void updateStock(SQLiteDatabase db, String pharmacyId, int productId, int quantity) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.InventoryTable.TABLE_NAME + " WHERE " +
                PharmacySContract.InventoryTable.PHARMACY_ID + " = '" + pharmacyId + "' AND " + PharmacySContract.InventoryTable.PRODUCT_ID + " = " + productId;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null && c.moveToFirst()) {
            ContentValues contentValues = new ContentValues();

            int currentQuantity = c.getInt(c.getColumnIndex(PharmacySContract.InventoryTable.QUANTITY));
            int newQuantity = currentQuantity - quantity;

            contentValues.put(PharmacySContract.InventoryTable.QUANTITY, newQuantity);

            db.update(PharmacySContract.InventoryTable.TABLE_NAME, contentValues, null, null);

            DBConnectorServer.updateStock(pharmacyId, productId, newQuantity);

            c.close();
        }
    }
}
