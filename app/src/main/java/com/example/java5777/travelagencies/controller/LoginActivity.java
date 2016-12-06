package com.example.java5777.travelagencies.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.java5777.travelagencies.R;

public class LoginActivity extends AppCompatActivity {

    public Button loginButton;
    public Button clearButton;
    public Button registerButton;
    public EditText username;
    public EditText password;
    public CheckBox rememberMeBox;

    SharedPreferences prefs;

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

        //check if there is a saved user name and password

        // implement onClick method for login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user is in the system and check if it's a correct password
                //if it's a user, and "Remember Me", it saves the details in the shared preferences
                //
                Intent intent = new Intent(LoginActivity.this, MainOptionsActivity.class);
                startActivity(intent);
            }
        });

        // implement onClick method for clear
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("Username", TextView.BufferType.EDITABLE);
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
}
