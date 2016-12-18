package com.example.java5777.travelagencies.controller;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.java5777.travelagencies.R;
import com.example.java5777.travelagencies.model.Service.CheckUpdatesService;
import com.example.java5777.travelagencies.model.backend.DSManager;
import com.example.java5777.travelagencies.model.backend.DSManagerFactory;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;
import com.example.java5777.travelagencies.model.entities.Address;
import com.example.java5777.travelagencies.model.entities.Trip;
import com.example.java5777.travelagencies.model.entities.TripType;
import com.example.java5777.travelagencies.model.entities.User;
import com.example.java5777.travelagencies.model.entities.Agency;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.AgencyEntry;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.UserEntry;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.TripEntry;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    static Intent service = null;

    public void startButtonOnClick(View v) {
        if (service != null) {
            Toast.makeText(this, "SERVICE ALREADY STARTED", Toast.LENGTH_SHORT).show();
            return;
        }
        service = new Intent(this, CheckUpdatesService.class);
        startService( service );

        Toast.makeText(this, "STARTING SERVICE...", Toast.LENGTH_SHORT).show();
    }

    public void endButtonOnClick(View v) {
        if (service == null) {
            Toast.makeText(this, "SERVICE ALREADY STOPPED", Toast.LENGTH_SHORT).show();
            return;
        }
        stopService( service );

        service = null;

        Toast.makeText(this, "STOPPING SERVICE...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(service);
    }
}
