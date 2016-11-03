package com.hugoroman.pharmacys.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.transition.Slide;
import android.view.Gravity;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.util.PostUserAsync;
import com.hugoroman.pharmacys.util.SHA512;

import java.util.HashMap;

public class FragmentSettingsMain extends PreferenceFragment {

    private static final String URL_SERVER = "PUBLIC IP";
    private static final String PORT = "PORT";

    private boolean anim = false;

    public FragmentSettingsMain() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.BOTTOM));
            this.setExitTransition(new Slide(Gravity.TOP));

            anim = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        final EditTextPreference passwordPref = (EditTextPreference) findPreference("password");

        passwordPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String password = (String) newValue;

                if(password.length() < 8) {

                    showDialog("Password too small", "Password must be at least 8 characters");

                    return false;
                }

                String oldPass = getActivity().getSharedPreferences(MainActivity.SYSPRE, Context.MODE_PRIVATE).getString(MainActivity.PASS, MainActivity.NOT_PASS);
                String email = getActivity().getSharedPreferences(MainActivity.SYSPRE, Context.MODE_PRIVATE).getString(MainActivity.USER_EMAIL, MainActivity.NOT_USER_EMAIL);

                if(!oldPass.equals(MainActivity.NOT_PASS) && !email.equals(MainActivity.NOT_USER_EMAIL)) {
                    String passHash = "";

                    try {
                        passHash = SHA512.hashText(password);

                    } catch (Exception e) {
                        e.printStackTrace();

                        return false;
                    }

                    // Lógica del cambio de contraseña
                    String url = URL_SERVER + ":" + PORT + "/pharmacys/rest/user/changePassword";

                    HashMap<String, String> params = new HashMap<String, String>();

                    params.put("email", email);
                    params.put("oldPassword", oldPass);
                    params.put("newPassword", passHash);

                    ((SettingsActivity)getActivity()).setNewPass(passHash);

                    new PostUserAsync(getActivity(), url).execute(params);
                }

                return true;
            }
        });
    }

    public void showDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).create();

        dialog.show();
    }
}
