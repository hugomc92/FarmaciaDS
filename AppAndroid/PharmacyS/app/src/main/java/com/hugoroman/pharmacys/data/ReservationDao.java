package com.hugoroman.pharmacys.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hugoroman.pharmacys.model.Pharmacy;
import com.hugoroman.pharmacys.model.Product;
import com.hugoroman.pharmacys.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public final class ReservationDao {

    public static Reservation getReservation(SQLiteDatabase db) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.ReservationTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        List<List<Object>> productsPharmaciesQuantities = new ArrayList<List<Object>>();

        if(c != null && c.moveToFirst()) {
            do {
                List<Object> productPharmacyQuantity = new ArrayList<Object>();

                String pharmacyCif = c.getString(c.getColumnIndex(PharmacySContract.ReservationTable.PHARMACY_ID));

                final Pharmacy pharmacy = PharmacyDao.getPharmacy(db, pharmacyCif);

                int productID = c.getInt(c.getColumnIndex(PharmacySContract.ReservationTable.PRODUCT_ID));

                final Product product = ProductDao.getProduct(db, productID);

                productPharmacyQuantity.add(pharmacy);
                productPharmacyQuantity.add(product);
                productPharmacyQuantity.add(c.getInt(c.getColumnIndex(PharmacySContract.ReservationTable.QUANTITY)));

                productsPharmaciesQuantities.add(productPharmacyQuantity);
            } while(c.moveToNext());
        }

        if(c != null)
            c.close();

        return new Reservation(productsPharmaciesQuantities);
    }

    public static void addToReservation(SQLiteDatabase db, String pharmacyCif, int productId, int quantity, String userEmail) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.ReservationTable.TABLE_NAME + " WHERE " +
                PharmacySContract.ReservationTable.PHARMACY_ID + " = '" + pharmacyCif + "' AND " + PharmacySContract.ReservationTable.PRODUCT_ID + " = " + productId;

        Cursor c = db.rawQuery(selectQuery, null);

        ContentValues contentValues = new ContentValues();

        if(c != null) {
            boolean update = c.moveToFirst();

            c.close();

            if(!update) {
                contentValues.put(PharmacySContract.ReservationTable.PHARMACY_ID, pharmacyCif);
                contentValues.put(PharmacySContract.ReservationTable.PRODUCT_ID, productId);
                contentValues.put(PharmacySContract.ReservationTable.QUANTITY, quantity);
                db.insert(PharmacySContract.ReservationTable.TABLE_NAME, null, contentValues);

                DBConnectorServer.addToReservation(pharmacyCif, productId, userEmail, quantity);
            } else {
                contentValues.put(PharmacySContract.ReservationTable.QUANTITY, quantity);
                db.update(PharmacySContract.ReservationTable.TABLE_NAME, contentValues,
                        PharmacySContract.ReservationTable.PHARMACY_ID + " = ? AND " + PharmacySContract.ReservationTable.PRODUCT_ID + " = ?", new String[]{pharmacyCif, String.valueOf(productId)});

                DBConnectorServer.updateReservation(pharmacyCif, productId, userEmail, quantity);
            }
        }
    }

    public static void removeFromReservation(SQLiteDatabase db, String pharmacyCif, int productId, String userEmail, boolean onlyLocal) {

        db.delete(PharmacySContract.ReservationTable.TABLE_NAME,
                PharmacySContract.ReservationTable.PHARMACY_ID + " = ? AND " + PharmacySContract.ReservationTable.PRODUCT_ID + " = ?",
                new String[] { pharmacyCif, String.valueOf(productId) });

        if(!onlyLocal)
            DBConnectorServer.removeReservation(pharmacyCif, productId, userEmail);
    }

    public static void deleteAllReservations(SQLiteDatabase db, String userEmail) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.ReservationTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null && c.moveToFirst()) {
            do {
                removeFromReservation(db, c.getString(c.getColumnIndex(PharmacySContract.ReservationTable.PHARMACY_ID)), c.getInt(c.getColumnIndex(PharmacySContract.ReservationTable.PRODUCT_ID)), userEmail, true);
            } while(c.moveToNext());

            c.close();
        }
    }
}