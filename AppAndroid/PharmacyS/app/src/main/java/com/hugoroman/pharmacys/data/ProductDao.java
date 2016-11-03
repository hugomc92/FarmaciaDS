package com.hugoroman.pharmacys.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hugoroman.pharmacys.model.Product;

import java.sql.Date;

public final class ProductDao {

    public static Product getProduct(SQLiteDatabase db, int id) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.ProductTable.TABLE_NAME + " WHERE "
                + PharmacySContract.ProductTable.ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null) {
            c.moveToFirst();

            final Product product = new Product(c.getInt(c.getColumnIndex(PharmacySContract.ProductTable.ID)),
                                    c.getInt(c.getColumnIndex(PharmacySContract.ProductTable.CATEGORY)),
                                    c.getString(c.getColumnIndex(PharmacySContract.ProductTable.NAME)),
                                    c.getString(c.getColumnIndex(PharmacySContract.ProductTable.DESCRIPTION)),
                                    c.getString(c.getColumnIndex(PharmacySContract.ProductTable.LABORATORY)),
                                    c.getString(c.getColumnIndex(PharmacySContract.ProductTable.UNITS)),
                                    new Date(c.getLong(c.getColumnIndex(PharmacySContract.ProductTable.EXPIRATION_DATE))),
                                    c.getInt(c.getColumnIndex(PharmacySContract.ProductTable.SIZE)),
                                    c.getString(c.getColumnIndex(PharmacySContract.ProductTable.LOT)),
                                    c.getString(c.getColumnIndex(PharmacySContract.ProductTable.URL_IMAGE)));

            c.close();

            return product;
        }

        return null;
    }

    public static String getProductName(SQLiteDatabase db, int productId) {

        String selectQuery = "SELECT " + PharmacySContract.ProductTable.NAME + " FROM " + PharmacySContract.ProductTable.TABLE_NAME + " WHERE " +
                PharmacySContract.ProductTable.ID + " = " + productId;

        Cursor c = db.rawQuery(selectQuery, null);

        String productName = null;

        if(c != null) {
            c.moveToFirst();

            productName = c.getString(c.getColumnIndex(PharmacySContract.ProductTable.NAME));

            c.close();
        }

        return productName;
    }

    public static String getProductCategoryName(SQLiteDatabase db, int idProduct) {

        String selectQueryCategoryId = "SELECT " + PharmacySContract.ProductTable.CATEGORY + " FROM " + PharmacySContract.ProductTable.TABLE_NAME + " WHERE "
                + PharmacySContract.ProductTable.ID + " = " + idProduct;

        Cursor cId = db.rawQuery(selectQueryCategoryId, null);

        int categoryID = -1;

        if(cId!= null) {
            cId.moveToFirst();

            categoryID = cId.getInt(cId.getColumnIndex(PharmacySContract.ProductTable.CATEGORY));

            cId.close();
        }

        if(categoryID != -1) {
            String selectQueryCategoryName = "SELECT " + PharmacySContract.CategoryTable.NAME + " FROM " + PharmacySContract.CategoryTable.TABLE_NAME + " WHERE " +
                    PharmacySContract.CategoryTable.ID + " = '" + categoryID + "'";

            Cursor c = db.rawQuery(selectQueryCategoryName, null);

            String categoryName = null;

            if (c != null) {
                c.moveToFirst();

                categoryName = c.getString(c.getColumnIndex(PharmacySContract.CategoryTable.NAME));

                c.close();
            }

            return categoryName;
        }

        return null;
    }

    public static int getProductCategoryPhoto(SQLiteDatabase db, int idProduct) {

        String selectQueryCategoryId = "SELECT " + PharmacySContract.ProductTable.CATEGORY + " FROM " + PharmacySContract.ProductTable.TABLE_NAME + " WHERE "
                + PharmacySContract.ProductTable.ID + " = " + idProduct;

        Cursor cId = db.rawQuery(selectQueryCategoryId, null);

        int categoryID = -1;

        if(cId!= null) {
            cId.moveToFirst();

            categoryID = cId.getInt(cId.getColumnIndex(PharmacySContract.ProductTable.CATEGORY));

            cId.close();
        }

        if(categoryID != -1) {
            String selectQueryCategoryPhoto = "SELECT " + PharmacySContract.CategoryTable.IMG + " FROM " + PharmacySContract.CategoryTable.TABLE_NAME + " WHERE " +
                    PharmacySContract.CategoryTable.ID + " = '" + categoryID + "'";

            Cursor c = db.rawQuery(selectQueryCategoryPhoto, null);

            int categoryPhoto = 0;

            if (c != null) {
                c.moveToFirst();

                categoryPhoto = c.getInt(c.getColumnIndex(PharmacySContract.CategoryTable.IMG));

                c.close();
            }

            return categoryPhoto;
        }

        return categoryID;
    }

    public static int getProductCategoryId(SQLiteDatabase db, int idProduct) {

        String selectQueryCategoryId = "SELECT " + PharmacySContract.ProductTable.CATEGORY + " FROM " + PharmacySContract.ProductTable.TABLE_NAME + " WHERE "
                + PharmacySContract.ProductTable.ID + " = " + idProduct;

        Cursor cId = db.rawQuery(selectQueryCategoryId, null);

        int categoryId = -1;

        if(cId != null) {
            cId.moveToFirst();

            categoryId = cId.getInt(cId.getColumnIndex(PharmacySContract.ProductTable.CATEGORY));

            cId.close();
        }

        return categoryId;
    }

    public static String getCategoryName(SQLiteDatabase db, int categoryId) {

        String selectQuery = "SELECT " + PharmacySContract.CategoryTable.NAME + " FROM " + PharmacySContract.CategoryTable.TABLE_NAME + " WHERE " +
                PharmacySContract.CategoryTable.ID + " = " + categoryId;

        Cursor c = db.rawQuery(selectQuery, null);

        String categoryName = null;

        if(c != null) {
            c.moveToFirst();

             categoryName = c.getString(c.getColumnIndex(PharmacySContract.CategoryTable.NAME));

            c.close();
        }

        return categoryName;
    }

    public static void addProduct(SQLiteDatabase db, int productId, String productName, int productCategory, String productDescription, String productLaboratoy, String productUnits, long productExpDate,
                           int productSize, String productLot, String productPhoto) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.ProductTable.TABLE_NAME + " WHERE " + PharmacySContract.ProductTable.ID + " = '" + productId + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        ContentValues contentValues = new ContentValues();

        if(c != null) {
            boolean found = c.moveToFirst();

            c.close();

            contentValues.put(PharmacySContract.ProductTable.ID, productId);
            contentValues.put(PharmacySContract.ProductTable.NAME, productName);
            contentValues.put(PharmacySContract.ProductTable.CATEGORY, productCategory);
            contentValues.put(PharmacySContract.ProductTable.DESCRIPTION, productDescription);
            contentValues.put(PharmacySContract.ProductTable.LABORATORY, productLaboratoy);
            contentValues.put(PharmacySContract.ProductTable.UNITS, productUnits);
            contentValues.put(PharmacySContract.ProductTable.EXPIRATION_DATE, productExpDate);
            contentValues.put(PharmacySContract.ProductTable.SIZE, productSize);
            contentValues.put(PharmacySContract.ProductTable.LOT, productLot);
            contentValues.put(PharmacySContract.ProductTable.URL_IMAGE, productPhoto);

            if(found)
            db.delete(PharmacySContract.ProductTable.TABLE_NAME, null, null);

            db.insert(PharmacySContract.ProductTable.TABLE_NAME, null, contentValues);
        }
    }

    public static void deleteAllProducts(SQLiteDatabase db) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.ProductTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null && c.moveToFirst()) {
            do {
                db.delete(PharmacySContract.ProductTable.TABLE_NAME, null, null);
            } while(c.moveToNext());
        }

        if(c != null)
            c.close();
    }

    public static boolean existsProduct(SQLiteDatabase db, int productId) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.ProductTable.TABLE_NAME + " WHERE " +
                PharmacySContract.ProductTable.ID + " = " + productId;

        Cursor c = db.rawQuery(selectQuery, null);

        boolean exists = false;

        if(c != null) {
            if(c.moveToFirst())
                exists = true;

            c.close();
        }

        return exists;
    }
}