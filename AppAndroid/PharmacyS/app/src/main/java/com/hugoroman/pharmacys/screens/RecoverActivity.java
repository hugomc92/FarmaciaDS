package com.hugoroman.pharmacys.screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.util.PostUserAsync;

import java.util.HashMap;

public class RecoverActivity extends Activity {

    private static final String URL_SERVER = "PUBLIC IP";
    private static final String PORT = "PORT";

    private EditText iEmail;
    private Button btnRecover;
    private String email;

    private Toast exitToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pass);

        iEmail = (EditText) findViewById(R.id.input_email_recover);
        btnRecover = (Button) findViewById(R.id.btn_recover) ;

        btnRecover.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recover();
            }
        });
    }

    private void recover() {

        if(!validate())
            return;

        email = iEmail.getText().toString();

        // Conexión con el servidor
        String url = URL_SERVER + ":" + PORT + "/pharmacys/rest/user/resetPassword";

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        new PostUserAsync(this, url).execute(params);
    }

    public void onRecoverSuccess() {

        Toast.makeText(getBaseContext(), "We have send you an email to reset your password. Thank you", Toast.LENGTH_LONG).show();

        setResult(RESULT_OK, null);

        finish();
    }

    public void onRecoverFailed() {

        Toast.makeText(getBaseContext(), "Introduced email is not in the system, please, Sing Up", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {

        boolean valid = true;

        String email = iEmail.getText().toString();

        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            iEmail.setError("enter a valid email address");
            valid = false;
        }
        else {
            iEmail.setError(null);
        }

        return valid;
    }
}