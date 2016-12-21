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
import com.example.java5777.travelagencies.model.entities.Password;
import com.example.java5777.travelagencies.model.entities.Trip;
import com.example.java5777.travelagencies.model.entities.TripType;
import com.example.java5777.travelagencies.model.entities.User;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.AgencyEntry;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.TripEntry;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.UserEntry;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.HasBeenUpdatedEntry;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    public int InsertUser(ContentValues userData) {
        try {
            // get data
            long ID = (long) userData.get(TravelAgenciesContract.UserEntry.COLUMN_ID);
            String username = (String) userData.get(UserEntry.COLUMN_USERNAME);
            String password = (String) userData.get(UserEntry.COLUMN_PASSWORD);

            // make sure user doesn't already exist
            if (user_exists(username))
                return TravelAgenciesContract.CODE_USER_EXISTS;

            // insert data
            ListDS.insertUser(new User(ID, username, new Password(password)));
            userHasBeenUpdated = true;
        }
        catch (Exception e) {
            return TravelAgenciesContract.CODE_ERROR;
        }

        return TravelAgenciesContract.CODE_SUCCESS;
    }

    @Override
    public int InsertAgency(ContentValues agencyData) {
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

            // check if agency already exists
            if (agency_exists(ID))
                return TravelAgenciesContract.CODE_AGENCY_EXISTS;

            // insert data
            ListDS.insertAgency(new Agency(ID, name, email, phoneNumber, new Address(country, city, street), website));
            agencyHasBeenUpdated = true;
        }
        catch (Exception e) {
            return TravelAgenciesContract.CODE_ERROR;
        }

        return TravelAgenciesContract.CODE_SUCCESS;
    }

    @Override
    public int InsertTrip(ContentValues tripData) {
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

            if (!agency_exists(agencyID)) // no such agency
                    return TravelAgenciesContract.CODE_AGENCY_NOT_FOUND;

            // insert data
            ListDS.insertTrip(new Trip(tripType, country, start, end, price, description, agencyID));
            tripHasBeenUpdated = true;
        }
        catch (Exception e) {
            return TravelAgenciesContract.CODE_ERROR;
        }

        return TravelAgenciesContract.CODE_SUCCESS;
    }


    // Get Cursor operations

    @Override
    public Cursor getUsers(String username, String password) {
        MatrixCursor cursor = new MatrixCursor(UserEntry.COLUMNS); // create cursor

        // for each user, insert it into cursor
        for (User u : ListDS.cloneUserArrayList()) {
            // format data into list
            ArrayList<String> temp = new ArrayList<>();
            try {
                if (u.getUsername().equals(username) && u.getPassword().validatePassword(password)) { // validate user

                    temp.add(((Long) u.getID()).toString());
                    temp.add(u.getUsername());
                    temp.add(""); // return password = ""

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

    // variables used to check if ds has been updated

    private boolean userHasBeenUpdated = false;
    private boolean agencyHasBeenUpdated = false;
    private boolean tripHasBeenUpdated = false;

    /**
     * A method to check if DS has
     * been updated.
     * @return A cursor with information about DS change.
     * @see TravelAgenciesContract for what cursor will contain.
     */
    @Override
    public Cursor hasBeenUpdated() {
        MatrixCursor cur = new MatrixCursor(HasBeenUpdatedEntry.COLUMNS);

        // build result row
        ArrayList<Integer> results = new ArrayList<>();

        // foreach table (users, agencies, trips) if
        // table has been modified insert it into results
        // and set has been updated flag to false
        if (userHasBeenUpdated) {
            userHasBeenUpdated = false;
            results.add(1);
        }
        else results.add(0);

        if (agencyHasBeenUpdated) {
            agencyHasBeenUpdated = false;
            results.add(1);
        }
        else results.add(0);
        if (tripHasBeenUpdated) {
            tripHasBeenUpdated = false;
            results.add(1);
        }
        else results.add(0);

        cur.addRow(results);
        return cur;
    }

    // helpful methods

    private static boolean user_exists(String username) {
        for (User u : ListDS.cloneUserArrayList())
            if (u.getUsername() == username)
                return true;
        return false;
    }

    private static boolean agency_exists(long ID) {
        for (Agency a : ListDS.cloneAgencyArrayList())
            if (a.getID() == ID)
                return true;
        return false;
    }
}
