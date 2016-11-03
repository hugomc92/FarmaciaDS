package com.hugoroman.pharmacys.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hugoroman.pharmacys.screens.LoginActivity;
import com.hugoroman.pharmacys.screens.RecoverActivity;
import com.hugoroman.pharmacys.screens.SettingsActivity;
import com.hugoroman.pharmacys.screens.SignUpActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class PostUserAsync extends AsyncTask<HashMap<String, String>, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();

    private Activity activity;
    private ProgressDialog pDialog;
    private String url;

    public PostUserAsync(Activity activity, String url) {

        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {

        pDialog = new ProgressDialog(activity);

        if(activity.getClass() == LoginActivity.class)
            pDialog.setMessage("Attempting login...");
        else if(activity.getClass() == SignUpActivity.class)
            pDialog.setMessage("Attempting register...");
        else if(activity.getClass() == RecoverActivity.class)
            pDialog.setMessage("Attempting recover password...");
        else if(activity.getClass() == SettingsActivity.class)
            pDialog.setMessage("Attempting change password...");

        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(HashMap<String, String>... params) {

        try {
            JSONObject json = jsonParser.makeHttpRequest(url, params[0]);

            Log.e("request", "starting");

            if(json != null) {
                Log.e("JSON result", json.toString());

                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject json) {

        if(pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        String result = null;

        if(json != null) {
            result = json.toString();
        }

        if(result != null) {
            if (!result.contains("not")) {
                Log.e("Success!", result);
                if(activity.getClass() == LoginActivity.class)
                    ((LoginActivity)activity).onLoginPreSuccess();
                else if(activity.getClass() == SignUpActivity.class)
                    ((SignUpActivity)activity).onSignUpSuccess();
                else if(activity.getClass() == RecoverActivity.class)
                    ((RecoverActivity)activity).onRecoverSuccess();
                else if(activity.getClass() == SettingsActivity.class)
                    ((SettingsActivity) activity).onChangeSuccess();

            } else {
                Log.e("Success!", result);
                if(activity.getClass() == LoginActivity.class)
                    ((LoginActivity)activity).onLoginFailed();
                else if(activity.getClass() == SignUpActivity.class)
                    ((SignUpActivity)activity).onSignUpFailed();
                else if(activity.getClass() == RecoverActivity.class)
                    ((RecoverActivity)activity).onRecoverFailed();
                else if(activity.getClass() == SettingsActivity.class)
                    ((SettingsActivity) activity).onChangeFailed();
            }
        }
        else {
            Toast.makeText(activity.getApplicationContext(), "Inaccessible Server, please try again", Toast.LENGTH_SHORT).show();
            Log.e("Failure", "JSON Object null");
        }
    }
}