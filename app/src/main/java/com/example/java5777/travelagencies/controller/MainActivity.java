package com.example.java5777.travelagencies.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.java5777.travelagencies.R;
import com.example.java5777.travelagencies.model.backend.DSManager;
import com.example.java5777.travelagencies.model.backend.DSManagerFactory;
import com.example.java5777.travelagencies.model.entities.Address;
import com.example.java5777.travelagencies.model.entities.ClassSerializer;
import com.example.java5777.travelagencies.model.entities.Password;
import com.example.java5777.travelagencies.model.entities.Trip;
import com.example.java5777.travelagencies.model.entities.TripType;
import com.example.java5777.travelagencies.model.entities.User;
import com.example.java5777.travelagencies.model.entities.Agency;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            DSManager db = DSManagerFactory.getDSManager("List");

            // build data

            /////////////// USERS TESTED!!!

            ContentValues cv = User.toContentValues(1, "yonah", "password1");
            db.InsertUser(cv);
            cv = User.toContentValues(2, "erel", "password2");
            db.InsertUser(cv);

            ////////////// AGENCY TESTED!!!

            cv = Agency.toContentValue(3, "mann", Address.toContentValues("Israel", "Raanana", "Ravutski"),
                    "0000000000", "agency1@secret.com", "agency1.com");
            db.InsertAgency(cv);
            cv = Agency.toContentValue(4, "feldmar", Address.toContentValues("Israel", "Petach Tikva", "Haatzmaoot"),
                    "1111111111", "agency2@secret.com", "agency2.com");
            db.InsertAgency(cv);

            //////////////// TRIP TESTED!!!

            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();

            start.setTime(new Date(2000));
            end.setTime(new Date(3000));

            cv = Trip.toContentValues(TripType.Airline, "Thailand", start, end, 1000, "trip1", 3);
            db.InsertTrip(cv);

            start.setTime(new Date(1000));
            end.setTime(new Date(2000));

            cv = Trip.toContentValues(TripType.Airline, "China", start, end, 2000, "trip2", 4);
            db.InsertTrip(cv);


            // test data

            ArrayList<Trip> trips = Trip.cursorToTrip(db.getTrips());
            Log.i("TEST", trips.toString());
        }
        catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
