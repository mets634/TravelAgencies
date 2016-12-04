package com.example.java5777.travelagencies.model.datasource;

import com.example.java5777.travelagencies.model.entities.Agency;
import com.example.java5777.travelagencies.model.entities.Trip;
import com.example.java5777.travelagencies.model.entities.User;

import java.util.ArrayList;

/**
 * Created by yonah on 11/29/2016.
 */

/**
 * A class implementing a data source via lists.
 */
public class ListDS {
    // Data source management methods

    /**
     * Inserts new agency into data source.
     * @see Agency
     * @param agency The new agency to insert.
     */
    public static void insertAgency(Agency agency) {
        agencyArrayList.add(agency);
    }

    /**
     * Removes a agency from data source.
     * @see Agency
     * @param agency The agency to remove.
     */
    public static void removeAgency(Agency agency) {
        agencyArrayList.remove(agency);
    }

    /**
     * @see Agency
     * @return Copy of business's in the data source.
     */
    public static ArrayList<Agency> cloneAgencyArrayList() {
        return (ArrayList<Agency>) agencyArrayList.clone();
    }

    /**
     * Inserts new user into data source.
     * @see User
     * @param user The new user to insert.
     */
    public static void insertUser(User user) {
        userArrayList.add(user);
    }

    /**
     * Removes a user from data source.
     * @see User
     * @param user The user to remove.
     */
    public static void removeUser(User user) {
        userArrayList.remove(user);
    }

    /**
     * @see User
     * @return Copy of users in the data source.
     */
    public static ArrayList<User> cloneUserArrayList() {
        return (ArrayList<User>) userArrayList.clone();
    }

    /**
     * Inserts new trip into data source.
     * @see Trip
     * @param trip The new trip to insert.
     */
    public static void insertTrip(Trip trip) {
        tripArrayList.add(trip);
    }

    /**
     * Removes a trip from data source.
     * @see Trip
     * @param trip The business to remove.
     */
    public static void removeTrip(Trip trip) {
        tripArrayList.remove(trip);
    }

    /**
     * @see Trip
     * @return Copy of trips in the data source.
     */
    public static ArrayList<Trip> cloneTripArrayList() {
        return (ArrayList<Trip>) tripArrayList.clone();
    }


    // Data source's lists

    private static ArrayList<Agency> agencyArrayList = new ArrayList<>();
    private static ArrayList<User> userArrayList = new ArrayList<>();
    private static ArrayList<Trip> tripArrayList = new ArrayList<>();
}
