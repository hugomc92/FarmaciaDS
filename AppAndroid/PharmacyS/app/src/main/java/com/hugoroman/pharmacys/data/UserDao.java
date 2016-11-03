package com.hugoroman.pharmacys.data;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hugoroman.pharmacys.data.PharmacySContract.UserTable;
import com.hugoroman.pharmacys.model.User;

public final class UserDao {

    public static User getUser(SQLiteDatabase db, String userEmail) {

        String selectQuery = "SELECT * FROM " + UserTable.TABLE_NAME + " WHERE " +
                UserTable.EMAIL + " = '" + userEmail + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        User user = null;

        if(c != null) {
            c.moveToFirst();

            user = new User(c.getString(c.getColumnIndex(UserTable.EMAIL)),
                    c.getString(c.getColumnIndex(UserTable.NAME)),
                    c.getString(c.getColumnIndex(UserTable.SURNAME)));

            c.close();
        }

        return user;
    }

    public static void addUser(SQLiteDatabase db, Activity activity, String name, String surname, String email, String password, boolean onlyLocal) {

        String selectQuery = "SELECT * FROM " + PharmacySContract.UserTable.TABLE_NAME + " WHERE " +
                UserTable.EMAIL + " = '" + email + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        ContentValues contentValues = new ContentValues();

        if(c != null && !c.moveToFirst()) {
            Log.e("ADD USER", "LLEGA IF " + name + ", " + surname + ", " + email + ", " + password);

            String selectUserQuery = "SELECT * FROM " + UserTable.TABLE_NAME;

            Cursor c2 = db.rawQuery(selectUserQuery, null);

            if(c2 != null && c2.moveToFirst()) {
                DBConnectorServer.restartBDLocal(activity, email);

                db.delete(UserTable.TABLE_NAME, null, null);

                c2.close();
            }

            c.close();
            contentValues.put(UserTable.NAME, name);
            contentValues.put(UserTable.SURNAME, surname);
            contentValues.put(UserTable.EMAIL, email);
            contentValues.put(UserTable.PASSWORD, password);

            db.insert(UserTable.TABLE_NAME, null, contentValues);

            if(!onlyLocal) {
                // Crear el usuario en el servidor
                DBConnectorServer.addUser(name, surname, email, password);
            }
        }
    }
}
