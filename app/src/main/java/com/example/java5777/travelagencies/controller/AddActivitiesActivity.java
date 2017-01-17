package com.example.java5777.travelagencies.controller;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    // view components
    private Spinner tripTypeSpinner;
    private TripType tripType;
    private EditText country;
    private EditText startDate;
    private GregorianCalendar realStartDate = new GregorianCalendar();
    private EditText endDate;
    private GregorianCalendar realEndDate = new GregorianCalendar();
    private EditText price;
    private EditText description;
    private EditText businessID;
    private Button insertButton;

    /**
     * Implementation of onCreate method.
     * initialize view components.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activities);

        // initialize view components
        tripTypeSpinner = (Spinner) findViewById(R.id.tripTypeSpinner);
        country = (EditText) findViewById(R.id.country);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        price = (EditText) findViewById(R.id.price);
        description = (EditText) findViewById(R.id.description);
        businessID = (EditText) findViewById(R.id.businessID);
        insertButton = (Button) findViewById(R.id.insertButton);

        // setup spinner
        setTripTypeSpinner();

        // setup drawable on click for dates
        setDateDrawableRightOnClick(this, startDate, realStartDate);
        setDateDrawableRightOnClick(this, endDate, realEndDate);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validData()) {
                    insertButtonOnClick(v);
                    Intent intent = new Intent(AddActivitiesActivity.this, MainOptionsActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Implementation of insertbuttonOnClick method.
     * Inserts the data given into the database.
     * @param v Current view.
     */
    protected void insertButtonOnClick(View v) {
        try {
            if (!validData())
                throw new Exception();

            // get the values of the view grids and parse them if needed
            final String cntry = country.getText().toString();
            final Integer prc = Integer.parseInt(price.getText().toString());
            final String destination = description.getText().toString();
            final Long agencyID = Long.parseLong(businessID.getText().toString());

            // attempt to insert new activity using an asynctask
            AddActivityAsyncTask at = new AddActivityAsyncTask(
                    this, tripType, cntry, realStartDate, realEndDate, prc, destination, agencyID
            );
            at.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        catch (Exception e) {
            Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show();
        }
    }


    // spinner methods

    /**
     * Setup up spinner to include
     * all types of trips.
     */
    protected void setTripTypeSpinner() {
        tripTypeSpinner.setOnItemSelectedListener(this);

        // add spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Choose:");
        categories.add("Hotel Vacation Package");
        categories.add("Travel Agency");
        categories.add("Entertainment");
        categories.add("Airline");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tripTypeSpinner.setAdapter(dataAdapter);
    }

    /**
     * Implementation of onItemSelected method.
     * Chooses the correct trip given by spinner.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        switch(item) {
            case "Hotel Vacation Package": tripType = HotelVacationPackage; break;
            case "Travel Agency": tripType = TravelAgency; break;
            case "Entertainment": tripType = Entertainment; break;
            case "Airline": tripType = Airline; break;
            default: tripType = null; break;
        }
    }

    /**
     * Implementation of onNothingSelected method.
     * Sets trip type if nothing was selected.
     * @param arg0
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        tripType = null;
    }


    // Date picker methods

    /**
     * A method to show the date picker.
     * @param date View components to set text of date to.
     * @param realDate GregorianCalendar to receive date.
     */
    protected void showDatePickDialog(final EditText date, final GregorianCalendar realDate) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(AddActivitiesActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.dialog_pick_date, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddActivitiesActivity.this);
        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                // positive button onClick
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final DatePicker datePicker = (DatePicker) promptView.findViewById(R.id.datePicker);
                        writeDateFromDatePicker(datePicker, date, realDate);
                    }
                })
                // negative button onClick
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

    /**
     * Extract date from date picker into Gregorian calendar
     * @param datePicker The date picker.
     * @param date View component to set text to.
     * @param realDate The GregorianCalendar to set the date for.
     */
    protected static void writeDateFromDatePicker(DatePicker datePicker, EditText date, GregorianCalendar realDate){
        // extract date
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        // set gregorian calendar
        realDate.set(year, month, day);

        // set view components date as text
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(sdf.format(realDate.getTime()));
    }

    /**
     * A method to set the date picker drawable to clickable.
     * @param date View component to set as clickable.
     * @param realDate Gregorian calendar to write the date to.
     */
    protected static void setDateDrawableRightOnClick(final AddActivitiesActivity c, final EditText date, final GregorianCalendar realDate) {
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                // if clicked on, show date pick dialog
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (date.getRight() - date.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        c.showDatePickDialog(date, realDate);
                        return true;
                    }
                }
                return false;
            }
        });
    }


    // data validation methods

    /**
     * A method that validates the data given.
     * @return Whether all data is good.
     */
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

    // asynctask extension classes

    /**
     * A class extending the asynctask class that
     * inserts an activity into the database.
     */
    class AddActivityAsyncTask extends AsyncTask<Void, Void, Integer> {
        private TripType tripType;
        private String country;
        private GregorianCalendar startDate;
        private GregorianCalendar endDate;
        private int price;
        private String destination;
        private long agencyID;

        private Context currentContext;

        /**
         * AddActivitiesAsyncTask constructor.
         * Initializes all data.
         * @param tripType
         * @param country
         * @param startDate
         * @param endDate
         * @param price
         * @param destination
         * @param agencyID
         * @param c
         */
        public AddActivityAsyncTask(Context c, TripType tripType, String country, GregorianCalendar startDate, GregorianCalendar endDate, int price, String destination, long agencyID) {
            this.tripType = tripType;
            this.country = country;
            this.startDate = startDate;
            this.endDate = endDate;
            this.price = price;
            this.destination = destination;
            this.agencyID = agencyID;

            currentContext = c;
        }

        /**
         * Implementation of doInBackground.
         * Attempts to insert new activity into database.
         * @param params
         * @return CODE from insert attempt.
         */
        @Override
        protected Integer doInBackground(Void... params) {
            ContentValues val = TravelAgenciesContract.TripEntry.createContentValues(
                    tripType, country, startDate, endDate, price, destination, agencyID
            );

            // attempt to insert new activity
            return Integer.valueOf( // return CODE from uri
                    currentContext.getContentResolver().insert(TravelAgenciesContract.TripEntry.CONTENT_URI, val).getLastPathSegment()
            );
        }
    }
}

