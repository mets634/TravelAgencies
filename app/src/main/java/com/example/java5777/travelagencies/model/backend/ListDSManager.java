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
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;
import com.example.java5777.travelagencies.model.entities.Address;
import com.example.java5777.travelagencies.model.entities.Agency;
import com.example.java5777.travelagencies.model.entities.ClassSerializer;
import com.example.java5777.travelagencies.model.entities.Password;
import com.example.java5777.travelagencies.model.entities.Trip;
import com.example.java5777.travelagencies.model.entities.TripType;
import com.example.java5777.travelagencies.model.entities.User;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.AgencyEntry;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.TripEntry;

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
            long ID = (long) userData.get(TravelAgenciesContract.UserEntry.COLUMN_ID);
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
            long ID = (long) agencyData.get(AgencyEntry.COLUMN_ID);
            String name = (String) agencyData.get(AgencyEntry.COLUMN_NAME);
            String email = (String) agencyData.get(AgencyEntry.COLUMN_EMAIL);
            String phoneNumber = (String) agencyData.get(AgencyEntry.COLUMN_PHONENUMBER);
            String country = (String) agencyData.get(AgencyEntry.COLUMN_COUNTRY);
            String city = (String) agencyData.get(AgencyEntry.COLUMN_CITY);
            String street = (String) agencyData.get(AgencyEntry.COLUMN_STREET);
            String website = (String) agencyData.get(AgencyEntry.COLUMN_WEBSITE);

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
            TripType tripType = TripType.valueOf( (String) tripData.get( TripEntry.COLUMN_TYPE ) );
            String country = (String) tripData.get( TripEntry.COLUMN_COUNTRY );

            GregorianCalendar start = new GregorianCalendar();
            start.setTimeInMillis( (Long) tripData.get( TripEntry.COLUMN_START ) );

            GregorianCalendar end = new GregorianCalendar();
            end.setTimeInMillis( (Long) tripData.get( TripEntry.COLUMN_END ) );

            Integer price = (Integer) tripData.get( TripEntry.COLUMN_PRICE );
            String description = (String) tripData.get( TripEntry.COLUMN_DESCRIPTION );
            long agencyID = (long) tripData.get( TripEntry.COLUMN_AGENCYID );

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
    public Cursor getUsers(String username, String password) {
        MatrixCursor cursor = new MatrixCursor(User.CURSOR_COLUMNS); // create cursor

        // for each user, insert it into cursor
        for (User u : ListDS.cloneUserArrayList()) {
            // format data into list
            ArrayList<String> temp = new ArrayList<>();
            try {
                if (u.getUsername().equals(username) && u.getPassword().validatePassword(password)) { // validate user

                    temp.add(((Long) u.getID()).toString());
                    temp.add(u.getUsername());

                    cursor.addRow(temp); // insert data
                    return cursor;
                }
            }
            catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    @Override
    public Cursor getAgencies() {
        MatrixCursor cursor = new MatrixCursor(AgencyEntry.COLUMNS); // create cursor

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


        MatrixCursor cursor = new MatrixCursor(TripEntry.COLUMNS); // create cursor

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
