package com.example.java5777.travelagencies.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.java5777.travelagencies.R;

public class RegisterActivity extends AppCompatActivity {

    public EditText username;
    public EditText password;
    public CheckBox rememberMeBox;
    public Button registerButton;

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


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user is already registered
                //and than register new user to the DB, and if "Remember Me" also save him in the shared preferences
                Intent intent = new Intent(RegisterActivity.this, MainOptionsActivity.class);
                startActivity(intent);
            }
        });
    }
}
