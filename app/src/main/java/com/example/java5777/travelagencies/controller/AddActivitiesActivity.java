package com.example.java5777.travelagencies.controller;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java5777.travelagencies.R;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;
import com.example.java5777.travelagencies.model.entities.TripType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import com.example.java5777.travelagencies.model.entities.TripType;

import static com.example.java5777.travelagencies.R.id.addActivity;
import static com.example.java5777.travelagencies.R.id.price;
import static com.example.java5777.travelagencies.model.entities.TripType.Airline;
import static com.example.java5777.travelagencies.model.entities.TripType.Entertainment;
import static com.example.java5777.travelagencies.model.entities.TripType.HotelVacationPackage;
import static com.example.java5777.travelagencies.model.entities.TripType.TravelAgency;

public class AddActivitiesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Spinner tripTypeSpinner;
    public TripType tripType;
    public EditText country;
    public EditText startDate;
    public GregorianCalendar realStartDate = new GregorianCalendar();
    public EditText endDate;
    public GregorianCalendar realEndDate = new GregorianCalendar();
    public EditText price;
    public EditText description;
    public EditText businessID;
    public Button insertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activities);

        tripTypeSpinner = (Spinner) findViewById(R.id.tripTypeSpinner);
        setTripTypeSpinner();

        country = (EditText) findViewById(R.id.country);

        startDate = (EditText) findViewById(R.id.startDate);
        setDateDrawableRightOnClick(startDate, realStartDate);

        endDate = (EditText) findViewById(R.id.endDate);
        setDateDrawableRightOnClick(endDate, realEndDate);

        price = (EditText) findViewById(R.id.price);
        description = (EditText) findViewById(R.id.description);
        businessID = (EditText) findViewById(R.id.businessID);
        insertButton = (Button) findViewById(R.id.insertButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validData()) {
                    insertActivity();
                    Intent intent = new Intent(AddActivitiesActivity.this, MainOptionsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //setting the trip type spinner
    protected void setTripTypeSpinner() {
        tripTypeSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Choose:");
        categories.add("Hotel Vacation Package");
        categories.add("Travel Agency");
        categories.add("Entertainment");
        categories.add("Airline");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tripTypeSpinner.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        switch(item) {
            case "Hotel Vacation Package": setTripType(HotelVacationPackage); break;
            case "Travel Agency": setTripType(TravelAgency); break;
            case "Entertainment": setTripType(Entertainment); break;
            case "Airline": setTripType(Airline); break;
            default: setTripType(null); break;
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        setTripType(null);
    }
    protected void setTripType (TripType t) {
        tripType = t;
    }

    //setting the date pick dialog for both dates
    protected void showDatePickDialog(final EditText date, final GregorianCalendar realDate) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(AddActivitiesActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.dialog_pick_date, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddActivitiesActivity.this);
        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final DatePicker datePicker = (DatePicker) promptView.findViewById(R.id.datePicker);
                        writeDateFromDatePicker(datePicker, date, realDate);
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
    protected void writeDateFromDatePicker(DatePicker datePicker, EditText date, GregorianCalendar realDate){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        realDate.set(year, month, day);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(sdf.format(calendar.getTime()));
    }
    protected void setDateDrawableRightOnClick(final EditText date, final GregorianCalendar realDate) {
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (date.getRight() - date.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showDatePickDialog(date, realDate);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    //insert activity - async task
    protected void insertActivity() {
        //get the values of the view grids and parse them if needed
        final String cntry = country.getText().toString();
        final Integer prc = Integer.parseInt(price.getText().toString());
        final String desc = description.getText().toString();
        final Long agencyID = Long.parseLong(businessID.getText().toString());
        //write it to the database
        new AsyncTask<Void, Void, Uri>() {
            @Override
            protected Uri doInBackground(final Void... params) {
                ContentValues val = TravelAgenciesContract.TripEntry.createContentValues(
                        tripType, cntry, realStartDate, realEndDate, prc, desc, agencyID
                );
                return getContentResolver().insert(TravelAgenciesContract.TripEntry.CONTENT_URI, val);
            }
        }.execute();
    }

    //validation of the data
    protected boolean validData() {
        return (checkTripType() && checkCountry() && checkDates() && checkPrice() && checkDescription() && checkBusinessID());
    }
    protected boolean checkTripType() {
        if (tripType == null) {
            TextView text = (TextView) findViewById(R.id.textView1);
            text.setTextColor(Color.RED);
            return false;
        }
        return true;
    }
    protected boolean checkCountry() {
        if (country.getText().toString() == "") {
            TextView text = (TextView) findViewById(R.id.textView2);
            text.setTextColor(Color.RED);
            return false;
        }
        return true;
    }
    protected boolean checkDates() {
        return true;
    }
    protected boolean checkPrice() {
        if (price.getText().toString() == "") {
            TextView text = (TextView) findViewById(R.id.textView5);
            text.setTextColor(Color.RED);
            return false;
        }
        return true;
    }
    protected boolean checkDescription() {
        return true;
    }
    protected boolean checkBusinessID() {
        if (businessID.getText().toString() == "") {
            TextView text = (TextView) findViewById(R.id.textView6);
            text.setTextColor(Color.RED);
            return false;
        }
        return true;
    }
}

