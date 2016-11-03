package com.hugoroman.pharmacys.screens;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hugoroman.pharmacys.R;

public class SettingsActivity extends AppCompatActivity {

    private MediaPlayer successSound;
    private String passHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(getResources().getString(R.string.settings));

        successSound = MediaPlayer.create(getApplicationContext(), R.raw.tuntun);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new FragmentSettingsMain()).commit();
    }

    public void setNewPass(String password) {

        this.passHash = password;
    }

    public void onChangeSuccess() {

        // Guardar la nueva contraseña en el SharedPreferences
        SharedPreferences preferences = getSharedPreferences(MainActivity.SYSPRE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MainActivity.PASS, passHash);
        editor.commit();

        successSound.start();

        Toast.makeText(getApplicationContext(), "Password correctly changed", Toast.LENGTH_SHORT).show();
    }

    public void onChangeFailed() {

        Toast.makeText(getApplicationContext(), "Password not changed, please try again", Toast.LENGTH_SHORT).show();
    }
}