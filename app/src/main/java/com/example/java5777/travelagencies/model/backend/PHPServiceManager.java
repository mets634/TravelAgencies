package com.example.java5777.travelagencies.model.backend;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.example.java5777.travelagencies.model.datasource.PHPService;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;
import com.example.java5777.travelagencies.model.entities.Agency;

import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.AgencyEntry;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.TripEntry;
import com.example.java5777.travelagencies.model.entities.Trip;
import com.example.java5777.travelagencies.model.entities.TripType;

import java.util.ArrayList;

/**
 * Created by yonah on 1/16/17.
 */

public class PHPServiceManager implements  DSManager {
    @Override
    public int InsertUser(ContentValues userData) {
        return 0;
    }

    @Override
    public int InsertAgency(ContentValues agencyData) {
        try {
            PHPService.insertAgency(agencyData);
            return 1;
        }
        catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int InsertTrip(ContentValues tripData) {
        try {
            PHPService.insertTrip(tripData);
            return 1;
        }
        catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Cursor getUsers(String username, String password) {
        return null;
    }

    @Override
    public Cursor getAgencies() {
        MatrixCursor cursor = new MatrixCursor(AgencyEntry.COLUMNS);
        try {
            for (Agency a : PHPService.cloneAgencyArrayList()) {
                // format data into list
                ArrayList<String> temp = new ArrayList<>();
                temp.add(((Long) a.getID()).toString());
                temp.add(a.getName());
                temp.add(a.getAddress().country);
                temp.add(a.getAddress().city);
                temp.add(a.getAddress().street);
                temp.add(a.getPhoneNumber());
                temp.add(a.getEmail());
                temp.add(a.getWebsite());

                cursor.addRow(temp); // insert data
            }
            return cursor;
        }
        catch (Exception e) {
            return cursor;
        }
    }

    @Override
    public Cursor getTrips() {
        MatrixCursor cursor = new MatrixCursor(TripEntry.COLUMNS);
        try {
            ArrayList<Trip> trips = PHPService.cloneTripArrayList();
            for (Trip t : trips) {
                // format data into list
                ArrayList<String> temp = new ArrayList<>();
                temp.add(t.getType().toString());
                temp.add(t.getCountry());
                temp.add( ( (Long) t.getStart().getTimeInMillis() ).toString() );
                temp.add( ( (Long) t.getEnd().getTimeInMillis() ) .toString());
                temp.add(t.getPrice().toString());
                temp.add(t.getDescription());
                temp.add(((Long) t.getAgencyID()).toString());

                cursor.addRow(temp); // insert data
            }

            return cursor;
        }
        catch (Exception e) {
            return cursor;
        }
    }

    @Override
    public Cursor getTravelAgencyTrips() {
        MatrixCursor cursor = new MatrixCursor(TripEntry.COLUMNS);
        try {
            ArrayList<Trip> trips = PHPService.cloneTripArrayList();
            for (Trip t : trips) {
                if (t.getType() != TripType.TravelAgency) // wrong type of trip
                         continue; // skip this trip

                // format data into list
                ArrayList<String> temp = new ArrayList<>();
                temp.add(t.getType().toString());
                temp.add(t.getCountry());
                temp.add( ( (Long) t.getStart().getTimeInMillis() ).toString() );
                temp.add( ( (Long) t.getEnd().getTimeInMillis() ) .toString());
                temp.add(t.getPrice().toString());
                temp.add(t.getDescription());
                temp.add(((Long) t.getAgencyID()).toString());

                cursor.addRow(temp); // insert data
            }

            return cursor;
        }
        catch (Exception e) {
            return cursor;
        }
    }

    @Override
    public Cursor hasBeenUpdated() {
        try {

            MatrixCursor cur = new MatrixCursor(TravelAgenciesContract.HasBeenUpdatedEntry.COLUMNS);
            ArrayList<Agency> newAgencies = TravelAgenciesContract.AgencyEntry.cursorToList( getAgencies() );
            ArrayList<Trip> newTrips = TravelAgenciesContract.TripEntry.cursorToList( getTrips() );

            boolean a = !(
                    newAgencies.containsAll(agencies) && agencies.containsAll(newAgencies)
            && newTrips.containsAll(trips) && trips.containsAll(newTrips)
            );

            ArrayList<Integer> res = new ArrayList<>();
            if (a) {
                agencies = newAgencies;
                trips = newTrips;
                res.add(1);
            }
            else
                res.add(0);

            cur.addRow(res);
            return cur;
        }
        catch (Exception e) {
            return null;
        }
    }

    // lists helping with hasBeenUpdated method

    private static ArrayList<Agency> agencies = new ArrayList<>();
    private static ArrayList<Trip> trips = new ArrayList<>();
}
