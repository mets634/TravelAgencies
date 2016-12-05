package com.example.java5777.travelagencies.model.backend;

/**
 * Created by yonah on 11/30/2016.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;

import com.example.java5777.travelagencies.model.datasource.ListDS;
import com.example.java5777.travelagencies.model.entities.Address;
import com.example.java5777.travelagencies.model.entities.Agency;
import com.example.java5777.travelagencies.model.entities.ClassSerializer;
import com.example.java5777.travelagencies.model.entities.Password;
import com.example.java5777.travelagencies.model.entities.Trip;
import com.example.java5777.travelagencies.model.entities.TripType;
import com.example.java5777.travelagencies.model.entities.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A class implementing the data
 * source manager interface
 * using lists as it's data.
 * @see com.example.java5777.travelagencies.model.datasource.ListDS
 */
public class ListDSManager implements DSManager {
    // implementation of DSManager interface

    // Insertion operations

    @Override
    public boolean InsertUser(ContentValues userData) {
        try {
            // get data
            long ID = (long) userData.get(User.ID_VALUE);
            String username = (String) userData.get(User.USERNAME_VALUE);
            String password = (String) userData.get(User.PASSWORD_VALUE);

            // insert data
            ListDS.insertUser(new User(ID, username, new Password(password)));
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean InsertAgency(ContentValues agencyData) {
        try {
            // get data
            long ID = (long) agencyData.get(Agency.ID_VALUE);
            String name = (String) agencyData.get(Agency.NAME_VALUE);
            String email = (String) agencyData.get(Agency.EMAIL_VALUE);
            String phoneNumber = (String) agencyData.get(Agency.PHONENUMBER_VALUE);
            String country = (String) agencyData.get(Address.COUNTRY_VALUE);
            String city = (String) agencyData.get(Address.CITY_VALUE);
            String street = (String) agencyData.get(Address.STREET_VALUE);
            String website = (String) agencyData.get("website");

            // insert data
            ListDS.insertAgency(new Agency(ID, name, email, phoneNumber, new Address(country, city, street), website));

        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean InsertTrip(ContentValues tripData) {
        try {
            // get data
            TripType tripType = TripType.valueOf( (String) tripData.get(Trip.TYPE_VALUE) );
            String country = (String) tripData.get(Trip.COUNTRY_VALUE);

            GregorianCalendar start = new GregorianCalendar();
            start.setTimeInMillis( (Long) tripData.get(Trip.START_VALUE ) );

            GregorianCalendar end = new GregorianCalendar();
            end.setTimeInMillis( (Long) tripData.get(Trip.END_VALUE) );

            Integer price = (Integer) tripData.get(Trip.PRICE_VALUE);
            String description = (String) tripData.get(Trip.DESCRIPTION_VALUE);
            long agencyID = (long) tripData.get(Trip.AGENCYID_VALUE);

            // insert data
            ListDS.insertTrip(new Trip(tripType, country, start, end, price, description, agencyID));
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }


    // Get Cursor operations

    @Override
    public Cursor getUsers() {
        MatrixCursor cursor = new MatrixCursor(User.CURSOR_COLUMNS); // create cursor

        // for each user, insert it into cursor
        for (User u : ListDS.cloneUserArrayList()) {
            // format data into list
            ArrayList<String> temp = new ArrayList<>();
            temp.add(((Long) u.getID()).toString());
            temp.add(u.getUsername());
            temp.add(u.getPassword().getHash());
            temp.add(u.getPassword().getSalt());

            cursor.addRow(temp); // insert data
        }

        return cursor;
    }

    @Override
    public Cursor getAgencies() {
        MatrixCursor cursor = new MatrixCursor(Agency.CURSOR_COLUMNS); // create cursor

        // for each agency, insert it into cursor
        for (Agency a : ListDS.cloneAgencyArrayList()) {
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

    @Override
    public Cursor getTrips() {


        MatrixCursor cursor = new MatrixCursor(Trip.CURSOR_COLUMNS); // create cursor

        // for each trip, insert it into cursor
        for (Trip t : ListDS.cloneTripArrayList()) {
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


    // Check if data source has been updated operation

    /**
     * Variable that whenever an update occurs, the
     * method that updated it must change variable
     * to true. Method hasBeenUpdated uses variable
     * to check it's condition.
     */
    private boolean dsHasBeenUpdated = false;

    // @// TODO: 12/5/2016 TEST HASBEENUPDATED METHOD 
    
    @Override
    public boolean hasBeenUpdated() {
        boolean tmp = dsHasBeenUpdated; // hold onto old data
        dsHasBeenUpdated = false; // update for next check
        return tmp;
    }
}
