package com.example.java5777.travelagencies.controller;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.java5777.travelagencies.R;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.*;

public class LoginActivity extends AppCompatActivity {

    public Button loginButton;
    public Button clearButton;
    public Button registerButton;
    public EditText username;
    public EditText password;
    public CheckBox rememberMeBox;

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize components
        loginButton = (Button) findViewById(R.id.loginButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rememberMeBox = (CheckBox) findViewById(R.id.rememberMeBox);

        prefs = getSharedPreferences("UsersInfo", 0);
        prefsEditor = prefs.edit();

        //check if there is a saved user name and password
        username.setText(prefs.getString("username", username.getText().toString()));
        password.setText(prefs.getString("password", password.getText().toString()));

        // implement onClick method for login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user is in the system and check if it's a correct password
                //if it's the user, save him in the shared prefernces file and move to the main options screen
                //else go to the register screen
                checkIfUserAndMoveOn();
            }
        });

        // implement onClick method for clear
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("", TextView.BufferType.EDITABLE);
                password.setText("", TextView.BufferType.EDITABLE);
            }
        });

        // implement onClick method for register
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("username", username.getText().toString());
                intent.putExtra("password", password.getText().toString());
                startActivity(intent);
            }
        });
    }

    protected void checkIfUserAndMoveOn() {
        final String[] credentials = new String[2];
        credentials[0] = username.getText().toString();
        credentials[1] = password.getText().toString();
        new AsyncTask<Void, Void, Boolean> () {
            @Override
            protected Boolean doInBackground (final Void... params) {
                Cursor res = getContentResolver().query(UserEntry.CONTENT_URI, null, null, credentials, null);
                return res.getCount() > 0;
            }

            @Override
            protected void onPostExecute (Boolean result) {
                moveOn(result);
            }
        }.execute();
    }

    protected void moveOn (Boolean isUser) {
        if (isUser) {
            //checks if the user wants to be remembered
            if (rememberMeBox.isChecked()) {
                prefsEditor.putString("username", username.getText().toString());
                prefsEditor.putString("password", password.getText().toString());
                prefsEditor.commit();
            }
            Intent intent = new Intent(LoginActivity.this, MainOptionsActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("username", username.getText().toString());
            intent.putExtra("password", password.getText().toString());
            intent.putExtra("RememberMe", rememberMeBox.isChecked());
            startActivity(intent);
        }
    }
}
