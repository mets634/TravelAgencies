package com.example.java5777.travelagencies.controller;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.java5777.travelagencies.R;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;

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

        final EditText city = (EditText) promptView.findViewById(R.id.city);
        final EditText country = (EditText) promptView.findViewById(R.id.country);
        final EditText email = (EditText) promptView.findViewById(R.id.email);
        final EditText businessId = (EditText) promptView.findViewById(R.id.id);
        final EditText name = (EditText) promptView.findViewById(R.id.name);
        final EditText phonenumber = (EditText) promptView.findViewById(R.id.phonenumber);
        final EditText street = (EditText) promptView.findViewById(R.id.street);
        final EditText website = (EditText) promptView.findViewById(R.id.website);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add Business", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(validData()) {
                            insertBusiness();
                        }
                    }

                    protected void insertBusiness() {
                        //get the values of the view grids and parse them if needed
                        final String cty = city.getText().toString();
                        final String cntry = country.getText().toString();
                        final String mail = email.getText().toString();
                        final Long businessID = Long.parseLong(businessId.getText().toString());
                        final String namee = name.getText().toString();
                        final String phoneNumber = phonenumber.getText().toString();
                        final String strt = street.getText().toString();
                        final String web = website.getText().toString();

                        //write it to the database
                        new AsyncTask<Void, Void, Uri>() {
                            @Override
                            protected Uri doInBackground(final Void... params) {
                                ContentValues val = TravelAgenciesContract.AgencyEntry.createContentValues(
                                        businessID, cty, cntry, mail, namee, phoneNumber, strt, web
                                );
                                return getContentResolver().insert(TravelAgenciesContract.AgencyEntry.CONTENT_URI, val);
                            }
                        }.execute();
                    }

                    protected boolean validData() {
                        return (
                                checkBusinessCity() && checkBusinessCountry() && checkBusinessEmail() && checkBusinessID() &&
                                        checkBusinessName() && checkBusinessPhoneNumber() && checkBusinessStreet() && checkBusinessWebsite()
                        );
                    }
                    protected boolean checkBusinessCity() {
                        return true;
                    }
                    protected boolean checkBusinessCountry() {
                        return true;
                    }
                    protected boolean checkBusinessEmail() {
                        return true;
                    }
                    protected boolean checkBusinessID() {
                        return true;
                    }
                    protected boolean checkBusinessName() {
                        return true;
                    }
                    protected boolean checkBusinessPhoneNumber() {
                        return true;
                    }
                    protected boolean checkBusinessStreet() {
                        return true;
                    }
                    protected boolean checkBusinessWebsite() {
                        return true;
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
