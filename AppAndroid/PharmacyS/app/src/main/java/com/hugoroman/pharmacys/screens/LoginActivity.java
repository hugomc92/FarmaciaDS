package com.hugoroman.pharmacys.screens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.data.DBConnectorServer;
import com.hugoroman.pharmacys.util.PostUserAsync;
import com.hugoroman.pharmacys.util.SHA512;

import java.util.HashMap;

public class LoginActivity extends Activity {

    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_RECOVER = 1;

    private static final String URL_SERVER = "PUBLIC IP";
    private static final String PORT = "PORT";

    private EditText iEmail;
    private EditText iPass;
    private String email;
    private String passHash;
    private Button btnLogin;
    private TextView linkForgotPassword;
    private TextView linkSignUp;
    private MediaPlayer successSound;

    private Toast exitToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iEmail = (EditText) findViewById(R.id.input_email);
        iPass = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        linkForgotPassword = (TextView) findViewById(R.id.link_forgot_password);
        linkSignUp = (TextView) findViewById(R.id.link_signup);


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Loguearse
                login();
            }
        });

        linkForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recuperación de contraseña
                Intent intent = new Intent(getApplicationContext(), RecoverActivity.class);
                startActivityForResult(intent, REQUEST_RECOVER);
            }
        });

        linkSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Lanzar la actividad de registrarse
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    private void login() {

        if(!validate())
            return;

        email = iEmail.getText().toString();
        String password = iPass.getText().toString();

        try {
            passHash = SHA512.hashText(password);

        } catch(Exception e) {
            e.printStackTrace();

            return;
        }

        String url = URL_SERVER + ":" + PORT + "/pharmacys/rest/user/login";

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("password", passHash);

        new PostUserAsync(this, url).execute(params);
    }

    public void onLoginPreSuccess() {

        // Obtener el resto de datos del servidor
        DBConnectorServer.getUserName(this, email);
    }

    public void onLoginSuccess(String name) {

        // Guardarlo en la BD local
        DBConnector dbConnector = new DBConnector(this.getApplicationContext());

        int space = name.indexOf(" ");
        String surname = name.substring(space + 1);
        name = name.substring(0, space);

        Log.e("USER NAME LOG IN", name);
        Log.e("USER SURNAME LOG IN", surname);
        dbConnector.addUser(this, name, surname, email, passHash, true);

        // Guardar en el SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SYSPRE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.USER_EMAIL, email);
        editor.putString(MainActivity.PASS, passHash);
        editor.commit();

        successSound = MediaPlayer.create(this.getApplicationContext(), R.raw.tuntun);
        successSound.start();
        setResult(RESULT_OK, null);

        finish();
    }

    public void onLoginFailed() {

        Toast.makeText(getBaseContext(), "Login failed, wrong user or password", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {

        boolean valid = true;

        String email = iEmail.getText().toString();
        String password = iPass.getText().toString();

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

    // Capturar el evento cuando se registre el usuario si así lo ha pulsado
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SIGNUP) {
            if(resultCode == RESULT_OK) {
                // Terminamos la actividad de login y volvemos a la main
                setResult(RESULT_OK, null);

                finish();
            }
        }
        else if(requestCode == REQUEST_RECOVER) {
            if(resultCode == RESULT_OK)
                finishAffinity();
        }
    }

    @Override
    public void onBackPressed() {

        if(exitToast!=null && exitToast.getView().getWindowToken() != null) {
            finishAffinity();
        }
        else {
            exitToast = Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT);
            exitToast.show();
        }
    }
}