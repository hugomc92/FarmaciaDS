package com.hugoroman.pharmacys.data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.hugoroman.pharmacys.model.Basket;
import com.hugoroman.pharmacys.model.Pharmacy;
import com.hugoroman.pharmacys.model.Product;
import com.hugoroman.pharmacys.screens.LoginActivity;
import com.hugoroman.pharmacys.screens.MainActivity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class DBConnectorServer {

    private static final String HOST = "PUBLIC IP";
    private static final String DATABASE = "DATABASE NAME";
    private static final String USER = "DATABASE USER";
    private static final String PASSWORD = "DATABASE PASSWORD";
    private static final String CONNECTION = "jdbc:mysql://"+HOST+"/"+DATABASE;

    public static Connection con;
    public static Statement st;

    private static DBConnector dbConnector;
    private static Basket basket;

    public static void createConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver").newInstance();

        try{
            con = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    // Cierra la conexion con la BBDD
    public static void closeConnection() {
        try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(final String name, final String surname, final String email, final String password) {

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String query = "INSERT INTO pharmacys.USER VALUES (" + name + "," + surname + "," + email + "," + password + "NULL, 0, 1, NULL)";
                int result = 0;

                try {
                    createConnection();

                    result = st.executeUpdate(query);

                    Log.e("INSERT USER", "" + result);

                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return String.valueOf(result);
            }

            @Override
            protected void onPostExecute(Object o) {
                Log.e("LLEGA", "ONPOSTEXECUTE INSERT USER");
            }
        };

        asyncTask.execute();
    }

    // Mostrar diálogo de proces de actualización de la BD
    private static void showDialog(ProgressDialog pDialog, String message) {

        pDialog.setMessage(message);

        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    // Ocultar diálogo
    private static void dismissDialog(ProgressDialog pDialog) {

        if(pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public static void getUserName(final Activity activity, final String email) {

        AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {

            private String userName;
            private ProgressDialog  progressDialog = new ProgressDialog(activity);


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(progressDialog, "Attempting get user info from server...");
            }

            @Override
            protected String doInBackground(String ... params) {
                String query = "SELECT NAME, SURNAME FROM pharmacys.USER WHERE EMAIL = '" + email + "'";

                try {
                    createConnection();

                    ResultSet rs = st.executeQuery(query);

                    if(rs.next()) {
                        userName = rs.getString("NAME");
                        userName += " " + rs.getString("SURNAME");
                    }

                    Log.e("DB CONNECTOR SERVER", "USER NAME AND SURNAME " + userName);

                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return userName;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.e("LLEGA", "ONPOSTEXECUTE USER NAME + " + result);

                dismissDialog(progressDialog);

                ((LoginActivity)activity).onLoginSuccess(result);
            }
        };

        asyncTask.execute();
    }

    public static void syncDB(Activity activity, String email) {

        Log.e("START SYNC", "email: " + email);

        restartBDLocal(activity, email);

        syncPharmacy(activity);
        syncProduct(activity);
        syncInventory(activity);
        syncOrder(activity, email);
        syncReservation(activity, email);

        MainActivity.UPDATED = true;

        Log.e("FINISH SYNC", "");

    }

    public static void restartBDLocal(Activity activity, String userEmail) {

        dbConnector = new DBConnector(activity.getApplicationContext());

        // Borrar elementos de las tablas que estan sujetos a desaparecer en la base de datos del servidor
        dbConnector.deleteAllInventories();
        dbConnector.deleteAllOrders();
        dbConnector.deleteAllReservations(userEmail);

        // Guardar la cesta para restaurarla luego cuando se hayan sincronizado los productos
        basket = dbConnector.getBasket();
        dbConnector.deleteBasket();
        dbConnector.deleteAllProducts();
    }

    public static void syncPharmacy(final Activity activity) {

        AsyncTask asyncTask = new AsyncTask() {

            private ProgressDialog  progressDialog = new ProgressDialog(activity);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(progressDialog, "Updating database...");
            }

            @Override
            protected Object doInBackground(Object... params) {

                try {
                    createConnection();

                    // Sincronizar Pharmacias
                    String selectPharmacy = "SELECT * FROM pharmacys.PHARMACY";

                    DBConnector dbConnector = new DBConnector(activity.getApplicationContext());

                    ResultSet rs = st.executeQuery(selectPharmacy);

                    Log.e("SYNC", "START PHARMACIES");
                    while(rs.next()) {
                        Log.e("SYNC", "WHILE PHARMACIES");
                        String pharmacyCif = rs.getString("CIF");
                        String pharmacyName = rs.getString("NAME");
                        int pharmacyPhone = rs.getInt("PHONE_NUMBER");
                        String pharmacyDescription = rs.getString("DESCRIPTION");
                        int pharmacyStart = rs.getInt("START_SCHEDULE");
                        int pharmacyEnd = rs.getInt("END_SCHEDULE");
                        double pharmacyLatitude = rs.getDouble("LATITUDE");
                        double pharmacyLongitude = rs.getDouble("LONGITUDE");
                        String pharmacyAddress = rs.getString("ADDRESS");
                        String pharmacyLogo = rs.getString("URL_IMG");

                        dbConnector.addPharmacy(pharmacyCif, pharmacyName, pharmacyPhone, pharmacyDescription, pharmacyStart, pharmacyEnd, pharmacyLatitude,
                                pharmacyLongitude, pharmacyAddress, pharmacyLogo);
                    }
                    Log.e("SYNC", "FINISH PHARMACIES");

                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object result) {

                dismissDialog(progressDialog);
            }
        };

        asyncTask.execute();
    }

    public static void syncProduct(final Activity activity) {

        AsyncTask asyncTask = new AsyncTask() {

            private ProgressDialog  progressDialog = new ProgressDialog(activity);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(progressDialog, "Updating...");
            }

            @Override
            protected Object doInBackground(Object... params) {

                try {
                    createConnection();

                    // Sincronizar Productos
                    String selectProduct = "SELECT * FROM pharmacys.PRODUCT";

                    ResultSet rs = st.executeQuery(selectProduct);

                    Log.e("SYNC", "START PRODUCT");
                    while(rs.next()) {
                        Log.e("SYNC", "WHILE PRODUCT");
                        int productId = rs.getInt("ID");
                        String productName = rs.getString("NAME");
                        String productDescription = rs.getString("DESCRIPTION");
                        String productLaboratoy = rs.getString("LABORATORY");
                        String productUnits = rs.getString("UNITS");
                        Date productExpDate = rs.getDate("EXPIRATION_DATE");
                        int productSize = rs.getInt("SIZE");
                        String productLot = rs.getString("LOT");
                        String productPhoto = rs.getString("URL_IMG");
                        int productCategory = rs.getInt("CATEGORYID");

                        dbConnector.addProduct(productId, productName, productCategory, productDescription, productLaboratoy, productUnits, productExpDate.getTime(), productSize, productLot, productPhoto);
                    }

                    Log.e("SYNC", "FINISH PRODUCT");

                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object result) {

                // Restaurar la cesta
                Log.e("BASKET", "START RESTORATION");
                for(List<Object> basketItem : basket.getProductsPharmaciesQuantities()) {
                    Pharmacy pharmacy = (Pharmacy) basketItem.get(0);
                    Product product = (Product) basketItem.get(1);
                    int quantity = (Integer) basketItem.get(2);

                    if(dbConnector.existsProduct(product.getId())) {
                        dbConnector.addToBasket(pharmacy.getCif(), product.getId(), quantity);
                    }
                }
                Log.e("BASKET", "END RESTORATION");

                dismissDialog(progressDialog);
            }
        };

        asyncTask.execute();
    }

    public static void syncInventory(final Activity activity) {

        AsyncTask asyncTask = new AsyncTask() {

            private ProgressDialog  progressDialog = new ProgressDialog(activity);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(progressDialog, "Updating...");
            }

            @Override
            protected Object doInBackground(Object... params) {

                try {
                    createConnection();

                    // Sincronizar Inventario
                    String selectInventory = "SELECT * FROM pharmacys.INVENTORY";

                    ResultSet rs = st.executeQuery(selectInventory);

                    Log.e("SYNC", "START INVENTORY");
                    while(rs.next()) {
                        Log.e("SYNC", "WHILE INVENTORY");
                        String pharmacyId = rs.getString("PHARMACYID");
                        int productId = rs.getInt("PRODUCTID");
                        float productPrice = rs.getFloat("PRICE");
                        int stock = rs.getInt("STOCK");

                        dbConnector.addInventory(pharmacyId, productId, productPrice, stock);
                    }
                    Log.e("SYNC", "FINISH INVENTORY");

                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object result) {

                dismissDialog(progressDialog);
            }
        };

        asyncTask.execute();
    }

    public static void syncOrder(final Activity activity, final String email) {

        AsyncTask asyncTask = new AsyncTask() {

            private ProgressDialog  progressDialog = new ProgressDialog(activity);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(progressDialog, "Updating...");
            }

            @Override
            protected Object doInBackground(Object... params) {

                try {
                    createConnection();

                    // Sincronizar Pedidos
                    String selectUserOrder = "SELECT * FROM pharmacys.USER_ORDER WHERE USERID = '" + email + "'";

                    ResultSet rs = st.executeQuery(selectUserOrder);

                    Log.e("SYNC", "START ORDER");
                    while(rs.next()) {
                        Log.e("SYNC", "WHILE USER_ORDER");

                        Integer orderId = rs.getInt("ID");
                        List<Product> products = new ArrayList<Product>();
                        List<Integer> quantities = new ArrayList<Integer>();

                        String selectOrderProduct = "SELECT * FROM pharmacys.ORDER_PRODUCT WHERE ORDERID = " + orderId;

                        Log.e("  WHILE USER_ORDER", selectOrderProduct);

                        String pharmacyId = rs.getString("PHARMACYID");
                        long date = rs.getDate("DATE").getTime();
                        float price = rs.getFloat("PRICE");

                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(selectOrderProduct);

                        while(rs2.next()) {
                            Log.e("SYNC", "WHILE ORDER_PRODUCT");
                            int productId = rs2.getInt("PRODUCTID");
                            int quantity = rs2.getInt("QUANTITY");

                            Product product = dbConnector.getProduct(productId);

                            products.add(product);
                            quantities.add(quantity);
                        }

                        rs2.close();
                        st2.close();

                        Log.e(" SYNC", " WHILE ORDER_PRODUCT FINISH");

                        dbConnector.addToOrder(email, pharmacyId, date, price, products, quantities, true, orderId);

                    }
                    Log.e("SYNC", "FINISH ORDER");


                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object result) {

                dismissDialog(progressDialog);
            }
        };

        asyncTask.execute();
    }

    public static void syncReservation(final Activity activity, final String email) {

        AsyncTask asyncTask = new AsyncTask() {

            private ProgressDialog  progressDialog = new ProgressDialog(activity);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(progressDialog, "Updating...");
            }

            @Override
            protected Object doInBackground(Object... params) {

                try {
                    createConnection();

                    // Sincronizar Reservas
                    String selectReservations = "SELECT * FROM pharmacys.RESERVATION WHERE USERID = '" + email + "'";

                    ResultSet rs = st.executeQuery(selectReservations);

                    Log.e("SYNC", "START RESERVATION");
                    while(rs.next()) {
                        Log.e("SYNC", "WHILE RESERVATION");

                        String pharmacyId = rs.getString("PHARMACYID");
                        int productId = rs.getInt("PRODUCTID");
                        int quantity = rs.getInt("QUANTITY");

                        dbConnector.addToReservation(pharmacyId, productId, quantity, email);
                    }
                    Log.e("SYNC", "FINISH RESERVATION");

                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                
                dismissDialog(progressDialog);
            }
        };

        asyncTask.execute();
    }

    public static void addToOrder(final String userEmail, final String pharmacyId, final float price, final List<Product> products, final List<Integer> quantities) {

        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {

                Log.e("ADD TO ORDER", "DO IN BACKGROUND");

                int result;
                int result2 = 0;

                try {
                    createConnection();

                    Log.e("ADD TO ORDER", "DO IN BACKGROUND TRY");

                    String query = "INSERT INTO pharmacys.USER_ORDER VALUES (NULL, '" + userEmail + "', '" + pharmacyId + "', now() , " + price + ")";

                    Log.e("ADD TO ORDER", query);

                    result = st.executeUpdate(query);

                    Log.e("INSERT USER_ORDER", "" + result + ".");

                    for(int i=0; i<products.size(); i++) {

                        Log.e("INSERT USER_ORDER", "FOR " + i);

                        // Coger el último id insertado para linkarlo con la tabla que tiene toda la información de los pedidos
                        String queryOrderid = "SELECT ID FROM pharmacys.USER_ORDER ORDER BY ID DESC LIMIT 1 ";

                        ResultSet rs = st.executeQuery(queryOrderid);

                        int orderId;

                        if(rs.next()) {
                            orderId = rs.getInt("ID");

                            Log.e("LAST ORDER ID", " " + orderId + " IF");

                            String queryOrderProduct = "INSERT INTO pharmacys.ORDER_PRODUCT VALUES (" + orderId + "," + products.get(i).getId() + "," + quantities.get(i) + ")";

                            Log.e("INSERT USER_ORDER QUERY", queryOrderProduct);

                            result2 = st.executeUpdate(queryOrderProduct);

                            Log.e("INSERT ORDER_PRODUCT", "" + result2);
                        }

                        rs.close();
                    }

                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return String.valueOf(result2);
            }

            @Override
            protected void onPostExecute(Object o) {
                Log.e("LLEGA", "ONPOSTEXECUTE INSERT USER_ORDER " + o + " .");
            }
        };

        asyncTask.execute();
    }

    public static Integer getLastIdOrder() {

        AsyncTask<String, Void, Integer> asyncTask = new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {

                Integer orderId = null;

                try {
                    createConnection();

                    // Obtener el Id del último pedido
                    String queryOrderid = "SELECT ID FROM pharmacys.USER_ORDER ORDER BY ID DESC LIMIT 1 ";

                    ResultSet rs = st.executeQuery(queryOrderid);

                    Log.e("GET ORDER ID", "START GET LAST ORDER ID");
                    if(rs.next()) {
                        Log.e("GET ORDER ID", "IF GET ORDER ID");
                        orderId = rs.getInt("ID");
                    }
                    Log.e("GET ORDER ID", "FINISH");

                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return orderId;
            }
        };

        try {
            return asyncTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();

            return  null;
        }
    }

    public static void updateStock(final String pharmacyId, final int productId, final int quantity) {

        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {

                Integer orderId = null;

                try {
                    createConnection();

                    // Actualizar el stock en el servidor
                    String query = "UPDATE pharmacys.INVENTORY SET STOCK = " + quantity +  " WHERE PHARMACYID = '" + pharmacyId + "' AND PRODUCTID = " + productId;

                    st.executeUpdate(query);

                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return null;
            }
        };

        asyncTask.execute();
    }

    public static void addToReservation(final String pharmacyId, final int productId, final String userEmail, final int quantity) {

        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {

                Log.e("ADD TO RESERVATION", "DO IN BACKGROUND");

                int result = 0;

                try {
                    createConnection();

                    Log.e("ADD TO RESERVATION", "DO IN BACKGROUND TRY");

                    String query = "INSERT INTO pharmacys.RESERVATION VALUES ('" + pharmacyId + "', " + productId + ", '" + userEmail + "', " + quantity + ")";

                    Log.e("ADD TO RESERVATION", query);

                    result = st.executeUpdate(query);

                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return String.valueOf(result);
            }

            @Override
            protected void onPostExecute(Object o) {
                Log.e("LLEGA", "ONPOSTEXECUTE INSERT RESERVATION " + o + ".");
            }
        };

        asyncTask.execute();
    }

    public static void updateReservation(final String pharmacyId, final int productId, final String userEmail, final int quantity) {

        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {

                try {
                    createConnection();

                    // Actualizar el stock en el servidor
                    String query = "UPDATE pharmacys.RESERVATION SET QUANTITY = " + quantity +  " WHERE PHARMACYID = '" + pharmacyId + "' AND PRODUCTID = " + productId + " AND USERID = '" + userEmail + "'";

                    st.executeUpdate(query);

                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return null;
            }
        };

        asyncTask.execute();
    }

    public static void removeReservation(final String pharmacyId, final int productId, final String userEmail) {

        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {

                try {
                    createConnection();

                    // Borrar la reserva en el servidor
                    String query = "DELETE FROM pharmacys.RESERVATION WHERE PHARMACYID = '" + pharmacyId + "' AND PRODUCTID = " + productId + " AND USERID = '" + userEmail + "'";

                    st.executeUpdate(query);

                    st.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }

                return null;
            }
        };

        asyncTask.execute();
    }
}