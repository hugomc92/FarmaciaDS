package com.hugoroman.pharmacys.screens;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.util.PostUserAsync;
import com.hugoroman.pharmacys.util.SHA512;

import java.util.HashMap;

public class SignUpActivity extends Activity {

    private static final String URL_SERVER = "PUBLIC IP";
    private static final String PORT = "POR";

    private EditText iName;
    private EditText iSurname;
    private EditText iEmail;
    private EditText iPass;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String passHash;
    private Button btnSignUp;
    private TextView linkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        iName = (EditText) findViewById(R.id.input_name);
        iSurname = (EditText) findViewById(R.id.input_surname);
        iEmail = (EditText) findViewById(R.id.input_email);
        iPass = (EditText) findViewById(R.id.input_password);
        btnSignUp = (Button) findViewById(R.id.btn_login);
        linkLogin = (TextView) findViewById(R.id.link_signup);


        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Registrarse
                signup();
            }
        });

        linkLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Terminar la actividad para volver a la anterior
                finish();
            }
        });

    }

    private void signup() {

        if(!validate())
            return;

        name = iName.getText().toString();
        surname = iSurname.getText().toString();
        email = iEmail.getText().toString();
        password = iPass.getText().toString();

        try {
            passHash = SHA512.hashText(password);

        } catch(Exception e) {
            e.printStackTrace();

            return;
        }

        String url = URL_SERVER + ":" + PORT + "/pharmacys/rest/user/insert";

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("name", name);
        params.put("surname", surname);
        params.put("email", email);
        params.put("password", passHash);

        new PostUserAsync(this, url).execute(params);
    }

    public void onSignUpSuccess() {

        // Guardarlo en la BD local y remota
        DBConnector dbConnector = new DBConnector(this.getApplicationContext());
        dbConnector.addUser(this, name, surname, email, passHash, false);

        // Guardarlo en el Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SYSPRE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.USER_EMAIL, email);
        editor.putString(MainActivity.PASS, passHash);
        editor.commit();

        MediaPlayer successSound = MediaPlayer.create(this.getApplicationContext(), R.raw.tuntun);
        successSound.start();

        setResult(RESULT_OK, null);

        finish();
    }

    public void onSignUpFailed() {

        Toast.makeText(getBaseContext(), "Sign up failed, email already in use", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {

        boolean valid = true;

        name = iName.getText().toString();
        surname = iSurname.getText().toString();
        email = iEmail.getText().toString();
        password = iPass.getText().toString();

        if(name.isEmpty() || name.length() < 3) {
            iName.setError("at least 3 characters");
            valid = false;
        }
        else {
            iName.setError(null);
        }

        if(surname.isEmpty() || surname.length() < 3) {
            iSurname.setError("at least 3 characters");
            valid = false;
        }
        else {
            iName.setError(null);
        }

        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            iEmail.setError("enter a valid email address");
            valid = false;
        }
        else {
            iEmail.setError(null);
        }

        if(password.isEmpty() || password.length() < 8) {
            iPass.setError("8 or more alphanumeric characters");
            valid = false;
        }
        else {
            iPass.setError(null);
        }

        return valid;
    }
}
