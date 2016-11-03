package com.hugoroman.pharmacys.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hugoroman.pharmacys.data.PharmacySContract.PharmacyTable;
import com.hugoroman.pharmacys.model.Pharmacy;

import java.util.ArrayList;
import java.util.List;

// Clase que realiza las operaciones con la base de datos referentes a las farmacias solamente
public final class PharmacyDao {

    public static Pharmacy getPharmacy(SQLiteDatabase db, String pharmacyId) {

        // Coge los datos de la base de datos y los mete en un objeto Pharmacy y lo devuelve
        String selectQuery = "SELECT * FROM " + PharmacyTable.TABLE_NAME + " WHERE "
                + PharmacyTable.ID + " = '" + pharmacyId + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null) {
            c.moveToFirst();

            final Pharmacy pharmacy = new Pharmacy(c.getString(c.getColumnIndex(PharmacyTable.ID)),
                                    c.getString(c.getColumnIndex(PharmacyTable.NAME)),
                                    c.getInt(c.getColumnIndex(PharmacyTable.PHONE_NUMBER)),
                                    c.getString(c.getColumnIndex(PharmacyTable.DESCRIPTION)),
                                    c.getInt(c.getColumnIndex(PharmacyTable.START_SCHEDULE)),
                                    c.getInt(c.getColumnIndex(PharmacyTable.END_SCHEDULE)),
                                    c.getDouble(c.getColumnIndex(PharmacyTable.LATITUDE)),
                                    c.getDouble(c.getColumnIndex(PharmacyTable.LONGITUDE)),
                                    c.getString(c.getColumnIndex(PharmacyTable.ADDRESS)),
                                    c.getString(c.getColumnIndex(PharmacyTable.LOGO)));

            c.close();

            return pharmacy;
        }

        return null;
    }

    public static List<Pharmacy> getAllPharmacies(SQLiteDatabase db) {

        String selectQuery = "SELECT * FROM " + PharmacyTable.TABLE_NAME;

        //Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();

        if(c != null && c.moveToFirst()) {
            do {
                final Pharmacy pharmacy = new Pharmacy(c.getString(c.getColumnIndex(PharmacyTable.ID)),
                        c.getString(c.getColumnIndex(PharmacyTable.NAME)),
                        c.getInt(c.getColumnIndex(PharmacyTable.PHONE_NUMBER)),
                        c.getString(c.getColumnIndex(PharmacyTable.DESCRIPTION)),
                        c.getInt(c.getColumnIndex(PharmacyTable.START_SCHEDULE)),
                        c.getInt(c.getColumnIndex(PharmacyTable.END_SCHEDULE)),
                        c.getDouble(c.getColumnIndex(PharmacyTable.LATITUDE)),
                        c.getDouble(c.getColumnIndex(PharmacyTable.LONGITUDE)),
                        c.getString(c.getColumnIndex(PharmacyTable.ADDRESS)),
                        c.getString(c.getColumnIndex(PharmacyTable.LOGO)));

                pharmacies.add(pharmacy);
            } while(c.moveToNext());

            c.close();
        }

        return pharmacies;
    }

    public static String getPharmacyName(SQLiteDatabase db, String pharmacyId) {

        String selectQuery = "SELECT " + PharmacyTable.NAME + " FROM " + PharmacyTable.TABLE_NAME + " WHERE " +
                PharmacyTable.ID + " = '" + pharmacyId + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        String pharmacyName = null;

        if(c != null) {
            c.moveToFirst();

            pharmacyName = c.getString(c.getColumnIndex(PharmacyTable.NAME));

            c.close();
        }

        return pharmacyName;
    }

    public static void addPharmacy(SQLiteDatabase db, String pharmacyCif, String pharmacyName, int pharmacyPhone, String pharmacyDescription, int pharmacyStart, int pharmacyEnd,
                            double pharmacyLatitude, double pharmacyLongitude, String pharmacyAddress, String pharmacyLogo) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.PharmacyTable.TABLE_NAME + " WHERE " + PharmacyTable.ID + " = '" + pharmacyCif + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        ContentValues contentValues = new ContentValues();

        if(c != null) {
            boolean update = c.moveToFirst();

            c.close();

            contentValues.put(PharmacyTable.ID, pharmacyCif);
            contentValues.put(PharmacyTable.NAME, pharmacyName);
            contentValues.put(PharmacyTable.PHONE_NUMBER, pharmacyPhone);
            contentValues.put(PharmacyTable.DESCRIPTION, pharmacyDescription);
            contentValues.put(PharmacyTable.START_SCHEDULE, pharmacyStart);
            contentValues.put(PharmacyTable.END_SCHEDULE, pharmacyEnd);
            contentValues.put(PharmacyTable.LATITUDE, pharmacyLatitude);
            contentValues.put(PharmacyTable.LONGITUDE, pharmacyLongitude);
            contentValues.put(PharmacyTable.ADDRESS, pharmacyAddress);
            contentValues.put(PharmacyTable.LOGO, pharmacyLogo);

            if(!update) {
                db.insert(PharmacyTable.TABLE_NAME, null, contentValues);
            }
            else {
                db.update(PharmacyTable.TABLE_NAME, contentValues, PharmacyTable.ID + " = ?", new String[] { pharmacyCif });
            }
        }
    }
}
