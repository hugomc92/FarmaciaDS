package com.hugoroman.pharmacys.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class JSONParser {

    private String charset = "UTF-8";
    private HttpURLConnection connection;
    private DataOutputStream postWriter;
    private StringBuilder result;
    private URL urlObj;
    private JSONObject jObj = null;


    public JSONObject makeHttpRequest(String url, HashMap<String, String> params) {

        try {
            urlObj = new URL(url);

            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);

            connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            connection.setRequestProperty("Accept","*/*");

            connection.connect();

            postWriter = new DataOutputStream(connection.getOutputStream());
            int i = 0;

            String paramsString = "";
            for(String key : params.keySet()) {
                if (i != 0){
                    paramsString += "&";
                }
                paramsString += key + "=" + params.get(key);

                i++;
            }

            Log.e("PARAMS", paramsString);

            postWriter.writeBytes(paramsString);

            postWriter.flush();
            postWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Recibir la respuesta del servidor
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.disconnect();

        // Parsear la respuesta a un objeto JSON
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jObj;
    }
}