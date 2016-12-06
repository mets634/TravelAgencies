package com.example.java5777.travelagencies.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.java5777.travelagencies.R;

public class MainOptionsActivity extends AppCompatActivity {

    Button addBusinessButton;
    Button addActivityButton;
    Button serviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options);

        addBusinessButton = (Button) findViewById(R.id.addBusiness);
        addActivityButton = (Button) findViewById(R.id.addActivity);
        serviceButton = (Button) findViewById(R.id.serviceButton);

        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainOptionsActivity.this, AddActivitiesActivity.class);
                startActivity(intent);
            }
        });

        addBusinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open in a dialog
            }
        });

        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activate the service
            }
        });
    }
}
