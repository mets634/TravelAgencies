package com.example.java5777.travelagencies.model.backend;

/**
 * Created by yonah on 11/30/2016.
 */

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

/**
 * An interface to implement a data
 * source manager.
 */
public interface DSManager {
    // Insertion methods

    /**
     * Attempts to insert a new user
     * into data source.
     * @param userData A content value with all the required information.
     * @return Whether operation succeeded.
     */
    boolean InsertUser(ContentValues userData);

    /**
     * Attempts to insert a new agency
     * into data source.
     * @param agencyData A content value with all the required information.
     * @return Whether operation succeeded.
     */
    boolean InsertAgency(ContentValues agencyData);

    /**
     * Attempts to insert a new trip
     * into data source.
     * @param tripData A content value with all the required information.
     * @return Whether operation succeeded.
     */
    boolean InsertTrip(ContentValues tripData);

    // Get data cursor methods

    /**
     * @return A cursor containing all users.
     */
    Cursor getUsers(String username, String password);

    /**
     * @return A cursor containing all business's.
     */
    Cursor getAgencies();

    /**
     * @return A cursor containing all trips.
     */
    Cursor getTrips();


    /**
     * A method that checks whether
     * data source has been updated
     * since last check.
     * @return A cursor if has been updated, otherwise null.
     */
    Cursor hasBeenUpdated();
}
