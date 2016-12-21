package com.example.java5777.travelagencies.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                showAddBusinessDialog();
            }
        });

        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activate the service
            }
        });
    }


    protected void showAddBusinessDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainOptionsActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_add_business, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainOptionsActivity.this);
        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add Business", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
