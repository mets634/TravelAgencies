package com.example.java5777.travelagencies.controller;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java5777.travelagencies.R;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;
import com.example.java5777.travelagencies.model.entities.TripType;

import java.util.GregorianCalendar;

public class MainOptionsActivity extends AppCompatActivity {
    // view components
    private Button addBusinessButton;
    private Button addActivityButton;

    /**
     * Implementation of onCreate method.
     * Initialize view components.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options);

        // initialize view components
        addBusinessButton = (Button) findViewById(R.id.addBusiness);
        addActivityButton = (Button) findViewById(R.id.addActivity);
    }

    /**
     * addActivity button on click method.
     * Starts AddActivitiesActivity.
     * @param v Current view.
     */
    public void addActivityButtonOnClick(View v) {
        // go to AddActivitiesActivity screen
        Intent intent = new Intent(this, AddActivitiesActivity.class);
        startActivity(intent);
    }

    //////////// @// TODO: 12/31/2016 SUPER UGLY!!! FIX THIS!!!
    public void addBusinessButtonOnClick(View v) {
        final Context c = this;

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainOptionsActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_add_business, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainOptionsActivity.this);
        alertDialogBuilder.setView(promptView);

        // hold onto view components
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
                        else {
                            //Toast toast = new Toast(context);
                        }
                    }

                    protected void insertBusiness() {
                        try {
                            //get the values of the view grids and parse them if needed
                            final String cty = city.getText().toString();
                            final String cntry = country.getText().toString();
                            final String mail = email.getText().toString();
                            final Long businessID = Long.parseLong(businessId.getText().toString());
                            final String namee = name.getText().toString();
                            final String phoneNumber = phonenumber.getText().toString();
                            final String strt = street.getText().toString();
                            final String web = website.getText().toString();

                            // write it to the database
                            InsertBusinessAsyncTask at = new InsertBusinessAsyncTask(
                                    c, businessID, namee, cntry, cty, strt, phoneNumber, mail, web
                            );
                            at.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                        catch (Exception e) {
                            Toast.makeText(getParent(), "Error Creating Business", Toast.LENGTH_SHORT).show();
                        }
                    }

                    protected boolean validData() {
                        return (
                                checkBusinessCity() && checkBusinessCountry() && checkBusinessEmail() && checkBusinessID() &&
                                        checkBusinessName() && checkBusinessPhoneNumber() && checkBusinessStreet() && checkBusinessWebsite()
                        );
                    }
                    protected boolean checkBusinessCity() {
                        if (city.getText().toString() == "") return false;
                        return true;

                    }
                    protected boolean checkBusinessCountry() {
                        if (country.getText().toString() == "") return false;
                        return true;
                    }
                    protected boolean checkBusinessEmail() {
                        return true;
                    }
                    protected boolean checkBusinessID() {
                        if (businessId.getText().toString() == "") return false;
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

    private class InsertBusinessAsyncTask extends AsyncTask<Void, Void, Integer>
    {
        private Context c;

        private Long businessID;
        private String name;
        private String country;
        private String city;
        private String street;
        private String phoneNumber;
        private String email;
        private String web;

        public InsertBusinessAsyncTask(Context c, Long businessID, String name, String country, String city, String street, String phoneNumber, String email, String web) {
            this.businessID = businessID;
            this.name = name;
            this.country = country;
            this.city = city;
            this.street = street;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.web = web;

            this.c = c;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            ContentValues val = TravelAgenciesContract.AgencyEntry.createContentValues(
                    businessID, name, country, city, street, phoneNumber, email, web
            );
            return Integer.valueOf(
                    c.getContentResolver().insert(TravelAgenciesContract.AgencyEntry.CONTENT_URI, val).getLastPathSegment()
            );
        }
    }
}
