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
import java.util.Date;

/**
 * A class implementing the data
 * source manager interface
 * using lists as it's data.
 * @see com.example.java5777.travelagencies.model.datasource.ListDS
 * @ TODO: 11/30/2016 May want to create a class for key constants, to make passing ContentValues easier.
 * @// TODO: 12/4/2016 CHECK TO SEE IF BYTE[] SCHEME WORKS. 
 */
public class ListDSManager implements DSManager {
    // implementation of DSManager interface

    // Insertion operations

    @Override
    public boolean InsertUser(ContentValues userData) {
        try {
            /*
            long ID = (Long) userData.get("ID");
            String username = (String) userData.get("username");
            String password = (String) userData.get("password");
            

            ListDS.insertUser(new User(ID, username, new Password(password)));
            */
            
            User user = (User) ClassSerializer.deserialize((byte[]) userData.get("User")); 
            ListDS.insertUser(user);
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean InsertAgency(ContentValues businessData) {
        try {
            /*
            long ID = (long) businessData.get("ID");
            String name = (String) businessData.get("name");
            ContactsContract.CommonDataKinds.Email email = (Email) ClassSerializer.deserialize((byte[]) businessData.get("email"));
            String phoneNumber = (String) businessData.get("phonenumber");
            Address address = (Address) ClassSerializer.deserialize((byte[]) businessData.get("address"));
            URL website = (URL)  ClassSerializer.deserialize((byte[]) businessData.get("website"));

            ListDS.insertAgency(new Agency(ID, name, email, phoneNumber,address, website));
            
            */
            
            Agency agency = (Agency) ClassSerializer.deserialize((byte[]) businessData.get("Agency"));
            ListDS.insertAgency(agency);
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean InsertTrip(ContentValues tripData) {
        try {
            /*
            TripType tripType = (TripType) tripData.get("triptype");
            String country = (String) tripData.get("country");
            Date start = (Date) tripData.get("start");
            Date end = (Date) tripData.get("end");
            Integer price = (Integer) tripData.get("price");
            String description = (String) tripData.get("description");
            long agencyID = (long) tripData.get("agencyID");

            ListDS.insertTrip(new Trip(tripType, country, start, end, price, description, agencyID));
            */
            
            Trip trip = (Trip) ClassSerializer.deserialize((byte[]) tripData.get("Trip"));
            ListDS.insertTrip(trip);
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }


    // Get Cursor operations

    @Override
    public Cursor getUsers() {
        final String[] columns = { "ID", "username", "password" };

        MatrixCursor cursor = new MatrixCursor(columns); // create cursor
        cursor.addRow(ListDS.cloneUserArrayList()); // add data to cursor

        return cursor;
    }

    @Override
    public Cursor getAgencies() {
        final String[] columns = { "ID", "name", "address", "phonenumber", "email", "website" };

        MatrixCursor cursor = new MatrixCursor(columns); // create cursor
        cursor.addRow(ListDS.cloneAgencyArrayList()); // add data to cursor

        return cursor;
    }

    @Override
    public Cursor getTrips() {
        final String[] columns = { "type", "country", "start", "end", "price", "description", "agencyID" };

        MatrixCursor cursor = new MatrixCursor(columns); // create cursor
        cursor.addRow(ListDS.cloneTripArrayList()); // add data to cursor

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

    @Override
    public boolean hasBeenUpdated() {
        boolean tmp = dsHasBeenUpdated; // hold onto old data
        dsHasBeenUpdated = false; // update for next check
        return tmp;
    }
}
