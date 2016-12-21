package com.example.java5777.travelagencies.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.java5777.travelagencies.R;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;

public class RegisterActivity extends AppCompatActivity {

    public EditText username;
    public EditText password;
    public CheckBox rememberMeBox;
    public Button registerButton;

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rememberMeBox = (CheckBox) findViewById(R.id.rememberMeBox);
        registerButton = (Button) findViewById(R.id.registerButton);

        Bundle bundle = getIntent().getExtras();
        username.setText(bundle.getString("username"));
        password.setText(bundle.getString("password"));

        prefs = getSharedPreferences("UsersInfo", 0);
        prefsEditor = prefs.edit();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user is already registered
                //and than register new user to the DB, and if "Remember Me" also save him in the shared preferences
                checkIfNotExistAndMoveOn();
                Intent intent = new Intent(RegisterActivity.this, MainOptionsActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void checkIfNotExistAndMoveOn() {
        final String[] credentials = new String[2];
        credentials[0] = username.getText().toString();
        credentials[1] = password.getText().toString();
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground (final Void... params) {
                Cursor res = getContentResolver().query(TravelAgenciesContract.UserEntry.CONTENT_URI, null, null, credentials, null);
                return res.getCount() == 0;
            }

            @Override
            protected void onPostExecute (Boolean result) {
                moveOn(result);
            }
        }.execute();
    }

    protected void moveOn(Boolean isNotExist) {
        if (isNotExist) {
            final String[] credentials = new String[2];
            credentials[0] = username.getText().toString();
            credentials[1] = password.getText().toString();
            //write it to the database
            new AsyncTask<Void, Void, Uri>() {
                @Override
                protected Uri doInBackground (final Void... params) {
                    ContentValues val = TravelAgenciesContract.UserEntry.createContentValues(1, credentials[0], credentials[1]);
                    return getContentResolver().insert(TravelAgenciesContract.UserEntry.CONTENT_URI, val);
                }
            }.execute();
        }

        if (rememberMeBox.isChecked()) {
            prefsEditor.putString("username", username.getText().toString());
            prefsEditor.putString("password", password.getText().toString());
            prefsEditor.commit();
        }
        Intent intent = new Intent(RegisterActivity.this, MainOptionsActivity.class);
        startActivity(intent);
    }
}
