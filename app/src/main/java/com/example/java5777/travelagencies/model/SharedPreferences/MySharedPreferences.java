package com.example.java5777.travelagencies.model.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by yonah on 12/30/2016.
 */

public class MySharedPreferences {
    public MySharedPreferences(Context context) {
        // initialize shared preferences
        prefs = context.getSharedPreferences(SHAREDPREFERENCES_NAME, 0);
        prefsEditor = prefs.edit();
    }

    // class fields

    private SharedPreferences prefs;
    private Editor prefsEditor;

    // class constants

    public static final String SHAREDPREFERENCES_NAME = "UsersInfo";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    // class methods

    /**
     * A method to return the username that
     * is inside the shared preferences.
     * @return username.
     */
    public String getUserName() {
        return prefs.getString(KEY_USERNAME, "");
    }

    /**
     * A method to return the password that
     * is inside the shared preferences.
     * @return password.
     */
    public String getPassword() {
        return prefs.getString(KEY_PASSWORD, "");
    }

    /**
     * A method to insert the username into the shared preference
     * @param value The String to insert.
     */
    public void putUserName(String value) {
        prefsEditor.putString(KEY_USERNAME, value);
        prefsEditor.commit();
    }

    /**
     * A method to insert the password into the shared preference
     * @param value The String to insert.
     */
    public void putPassword(String value) {
        prefsEditor.putString(KEY_PASSWORD, value);
        prefsEditor.commit();
    }
}
