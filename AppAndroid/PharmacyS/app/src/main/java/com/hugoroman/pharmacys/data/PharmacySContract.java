package com.hugoroman.pharmacys.data;

import android.provider.BaseColumns;

/**
 * Clase para la creación de la base de datos. Esquema de la base de datos
 */

public final class PharmacySContract {

    public PharmacySContract() {}

    // Clase estáticas abstractas para la distintas tabla de la BD con los campos de dichas

    public static abstract class UserTable implements BaseColumns {

        public static final String TABLE_NAME = "USER";
        public static final String EMAIL = "EMAIL";
        public static final String NAME = "NAME";
        public static final String SURNAME = "SURNAME";
        public static final String PASSWORD = "PASSWORD";
    }

    public static abstract class PharmacyTable implements BaseColumns {

        public static final String TABLE_NAME = "PHARMACY";
        public static final String ID = "CIF";
        public static final String NAME = "NAME";
        public static final String PHONE_NUMBER = "PHONE_NUMBER";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String START_SCHEDULE = "START_SCHEDULE";
        public static final String END_SCHEDULE = "END_SCHEDULE";
        public static final String LATITUDE = "LATITUDE";
        public static final String LONGITUDE = "LONGITUDE";
        public static final String ADDRESS = "ADDRESS";
        public static final String LOGO = "LOGO";
    }

    public static abstract class CategoryTable implements BaseColumns {

        public static final String TABLE_NAME = "CATEGORY";
        public static final String ID = "ID";
        public static final String NAME = "NAME";
        public static final String IMG = "IMG";
    }

    public static abstract class ProductTable implements BaseColumns {

        public static final String TABLE_NAME = "PRODUCT";
        public static final String ID = "ID";
        public static final String NAME = "NAME";
        public static final String CATEGORY = "CATEGORY";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String LABORATORY = "LABORATORY";
        public static final String UNITS = "UNITS";
        public static final String EXPIRATION_DATE = "EXPIRATION_DATE";
        public static final String SIZE = "SIZE";
        public static final String LOT = "LOT";
        public static final String URL_IMAGE = "URL_IMAGE";
    }

    public static abstract class InventoryTable implements BaseColumns {

        public static final String TABLE_NAME = "INVENTORY";
        public static final String PHARMACY_ID = "PHARMACY_ID";
        public static final String PRODUCT_ID = "PRODUCT_ID";
        public static final String PRICE = "PRICE";
        public static final String QUANTITY = "STOCK";
        public static final String QUERYCOUNT = "QUERYCOUNT";
    }

    public static abstract class BasketTable implements BaseColumns {

        public static final String TABLE_NAME = "BASKET";
        public static final String PHARMACY_ID = "PHARMACY_ID";
        public static final String PRODUCT_ID = "PRODCUT_ID";
        public static final String QUANTITY = "QUANTITY";
    }

    public static abstract class ReservationTable implements BaseColumns {

        public static final String TABLE_NAME = "RESERVATION";
        public static final String PHARMACY_ID = "PHARMACY_ID";
        public static final String PRODUCT_ID = "PRODCUT_ID";
        public static final String QUANTITY = "QUANTITY";
    }

    public static abstract class OrderTable implements  BaseColumns {

        public static final String TABLE_NAME = "USER_ORDER";
        public static final String ID = "ID";
        public static final String USER_ID = "USER_ID";
        public static final String PHARMACY_ID = "PHARMACY_ID";
        public static final String DATE = "ORDER_DATE";
        public static final String PRICE = "PRICE";
    }

    public static abstract class OrderProductTable implements  BaseColumns {

        public static final String TABLE_NAME = "ORDER_PRODUCT";
        public static final String ID_ORDER = "ID_ORDER";
        public static final String PRODUCT_ID = "PRODUCT_ID";
        public static final String QUANTITY = "QUANTITY";
    }
}
