package com.example.java5777.travelagencies.model.datasource;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.java5777.travelagencies.model.entities.Address;
import com.example.java5777.travelagencies.model.entities.Agency;
import com.example.java5777.travelagencies.model.entities.Password;
import com.example.java5777.travelagencies.model.entities.Trip;
import com.example.java5777.travelagencies.model.entities.TripType;
import com.example.java5777.travelagencies.model.entities.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by yonah on 12/5/2016.
 */

/**
 * A contract class for TravelAgenciesProvider.
 * Any action done on content provider not
 * according to the contract will result in
 * either an exception or null will be returned.
 */
public final class TravelAgenciesContract {
    private TravelAgenciesContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.java5775.travelagencies.TravelAgenciesProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/cte");

    public static final String PATH_AGENCY = "agency";
    public static final String PATH_USER = "user";
    public static final String PATH_TRIP = "trip";

    /**
     * A class containing data used to access
     * the content provider for Agency-type data.
     *
     * There are two operation that can be done using this class:
     * 1) Inserting a new agency.
     * 2) Receiving a list of agencies. NOTE: Will contain all of the agencies.
     *
     * <h1>Inserting a new agency:</h1>
     * <p>To insert a new agency you must call the
     * content provider's 'insert' method with two parameters:
     * 1) <b>AgencyEntry.CONTENT_URI</b>.
     * 2) A ContentValues object containing the correct data.
     * Can be created using AgencyEntry.createContentValues.
     *
     * If successful a URI with id=1 will be returned.
     * Otherwise id=0 will be returned.</p>
     *
     * <h1>Receiving a list of agencies:</h1>
     * <p>To receive a list of agencies you must call
     * the content provider's 'query' method with every parameter
     * being null other than the URI which should be: <b>AgencyEntry.CONTENT_URI</b>.
     * Use AgencyEntry.cursorToList to convert returned cursor
     * to list of lgencies.</p>
     */
    public static final class AgencyEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_AGENCY).build();

        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_STREET = "street";
        public static final String COLUMN_PHONENUMBER = "phonenumber";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_WEBSITE = "website";
        
        public static final String[] COLUMNS = {
                COLUMN_ID, 
                COLUMN_NAME, 
                COLUMN_COUNTRY, 
                COLUMN_CITY, 
                COLUMN_STREET,
                COLUMN_PHONENUMBER,
                COLUMN_EMAIL,
                COLUMN_WEBSITE
        };

        /**
         * A method to convert cursor to list of agencies.
         * @param agencies Cursor.
         * @return List of agencies from cursor.
         * @throws Exception
         */
        public static ArrayList<Agency> cursorToList(Cursor agencies) throws Exception {
            if (!agencies.getColumnNames().equals(COLUMNS)) // incorrect cursor
                throw new IllegalArgumentException("Cursor Must Contain a Result of Agencies");

            ArrayList<Agency> res = new ArrayList<>();

            agencies.moveToFirst();

            do { // iterate through each result

                // retrieve agency data
                long ID = agencies.getLong( agencies.getColumnIndex( COLUMN_ID ) );
                String name = agencies.getString( agencies.getColumnIndex( COLUMN_NAME ) );
                String country = agencies.getString( agencies.getColumnIndex( COLUMN_COUNTRY ) );
                String city = agencies.getString( agencies.getColumnIndex( COLUMN_CITY ) );
                String street = agencies.getString( agencies.getColumnIndex( COLUMN_STREET ) );
                String phonenumber = agencies.getString( agencies.getColumnIndex( COLUMN_PHONENUMBER ) );
                String email = agencies.getString( agencies.getColumnIndex( COLUMN_EMAIL ) );
                String website = agencies.getString( agencies.getColumnIndex( COLUMN_WEBSITE ) );

                // create new agency and add it to list
                res.add(new Agency(ID, name, email, phonenumber, new Address(country, city, street), website));
            } while (agencies.moveToNext());

            return res;
        }

        /**
         * A method to convert Agency values to
         * Android ContentValues.
         * @param ID
         * @param name
         * @param address
         * @param phoneNumber
         * @param email
         * @param website
         * @return ContentValues containing values.
         */
        public static ContentValues createContentValues(long ID, String name, String country, String city, String street, String phoneNumber, String email, String website) {
            ContentValues res = new ContentValues();

            res.put(COLUMN_ID, ID);
            res.put(COLUMN_NAME, name);
            res.put(COLUMN_COUNTRY, country);
            res.put(COLUMN_CITY, city);
            res.put(COLUMN_STREET, street);
            res.put(COLUMN_PHONENUMBER, phoneNumber);
            res.put(COLUMN_EMAIL, email);
            res.put(COLUMN_WEBSITE, website);

            return res;
        }
    }

    /**
     * A class containing data used to access
     * the content provider for User-type data.
     *
     * There are two operation that can be done using this class:
     * 1) Inserting a new User.
     * 2) Receiving a given user.
     *
     * <h1>Inserting a new agency:</h1>
     * <p>To insert a new user you must call
     * the content provider's 'insert' method with
     * two parameters:
     * 1) <b>UserEntry.CONTENT_URI</b>.
     * 2) A ContentValues object containing the correct data.
     * Can be created using UserEntry.createContentValues.
     *
     * If successful a URI with id=1 will be returned.
     * Otherwise id=0 will be returned.</p>
     *
     * <h1>Receiving a given user</h1>
     * <p>To receive a given user you must call
     * the content provider's 'query' method
     * with all parameters being null other than two:
     * 1) The URI should be <b>UserEntry.CONTENT_URI</b>.
     * 2) The selectionArgs should contain exactly two
     * Strings in the following order: username, password.
     * You can convert the returned cursor to a list of users
     * by using the UserEntry.cursorToList method.
     *
     * NOTE: This is how you can validate a user by a given
     * username and password, as the content provider does
     * not return the actual passwords.</p>
     */
    public static final class UserEntry implements  BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        
        public static final String[] COLUMNS = {
                COLUMN_ID,
                COLUMN_USERNAME,
                COLUMN_PASSWORD
        };


        /**
         * A method to convert a cursor to a
         * list of users.
         * @param users Cursor that must contain result of users.
         * @return An ArrayList of users.
         * @throws Exception
         */
        public static ArrayList<User> cursorToUsers(Cursor users) throws Exception {
            if (!users.getColumnNames().equals(COLUMNS)) // incorrect cursor
                throw new IllegalArgumentException("Cursor Must Contain a Result of Users");

            ArrayList<User> res = new ArrayList<>();

            users.moveToFirst();

            do { // iterate through each result

                // retrieve user data
                long ID = users.getLong( users.getColumnIndex(COLUMN_ID) );
                String username = users.getString( users.getColumnIndex(COLUMN_USERNAME) );

                // create new user with no password and add it to list
                res.add(new User(ID, username, new Password()));
            } while (users.moveToNext());

            return res;
        }

        /**
         * A method to convert User class type values
         * to an Android ContentValues.
         * @param ID User's ID.
         * @param username User's username.
         * @param password User's password.
         * @return ContentValue containing the values.
         */
        public static ContentValues createContentValues(long ID, String username, String password) {
            ContentValues res = new ContentValues();

            res.put(COLUMN_ID, ID);
            res.put(COLUMN_USERNAME, username);
            res.put(COLUMN_PASSWORD, password);

            return res;
        }

    }

    /**
     * A class containing data used to access
     * the content provider for Trip-type data.
     *
     * There are two operation that can be done using this class:
     * 1) Inserting a new trip.
     * 2) Receiving a list of trip. NOTE: Will contain all of the agencies.
     *
     * <h1>Inserting a new trip:</h1>
     * <p>To insert a new trip you must call the
     * content provider's 'insert' method with two parameters:
     * 1) <b>TripEntry.CONTENT_URI</b>.
     * 2) A ContentValues object containing the correct data.
     * Can be created using TripEntry.createContentValues.
     *
     * If successful a URI with id=1 will be returned.
     * Otherwise id=0 will be returned.</p>
     *
     * <h1>Receiving a list of trips:</h1>
     * <p>To receive a list of trips you must call
     * the content provider's 'query' method with every parameter
     * being null other than the URI which should be: <b>TripEntry.CONTENT_URI</b>.
     * Use TripEntry.cursorToList to convert returned cursor
     * to List of trips.</p>
     */
    public static final class TripEntry implements  BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRIP).build();

        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_START = "start";
        public static final String COLUMN_END = "end";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AGENCYID = "agencyID";
        
        public static final String[] COLUMNS = {
                COLUMN_TYPE,
                COLUMN_COUNTRY,
                COLUMN_START,
                COLUMN_END,
                COLUMN_PRICE,
                COLUMN_DESCRIPTION,
                COLUMN_AGENCYID
        };

        /**
         * A method to convert a cursor to a list
         * of trips stored in it.
         * @param trips Cursor.
         * @return ArrayList containing result.
         * @throws Exception
         */
        public static ArrayList<Trip> cursorToList(Cursor trips) throws Exception {
            if (!trips.getColumnNames().equals(COLUMNS)) // incorrect cursor
                throw new IllegalArgumentException("Cursor Must Contain a Result of Trips");

            ArrayList<Trip> res = new ArrayList<>();

            trips.moveToFirst();

            do { // iterate through each result

                // retrieve trip data
                TripType type = TripType.valueOf( trips.getString( trips.getColumnIndex( COLUMN_TYPE ) ) );
                String country = trips.getString( trips.getColumnIndex( COLUMN_COUNTRY ) );

                GregorianCalendar start = new GregorianCalendar();
                start.setTimeInMillis( trips.getLong( trips.getColumnIndex( COLUMN_START ) ) );

                GregorianCalendar end = new GregorianCalendar();
                end.setTimeInMillis( trips.getLong( trips.getColumnIndex( COLUMN_END ) )); ;

                Integer price = trips.getInt( trips.getColumnIndex( COLUMN_PRICE ) );
                String description = trips.getString( trips.getColumnIndex( COLUMN_DESCRIPTION ) );
                long agencyID = trips.getLong( trips.getColumnIndex( COLUMN_AGENCYID ) );


                // create new trip and add it to list
                res.add(new Trip(type, country, start, end, price, description, agencyID));
            } while (trips.moveToNext());

            return res;
        }


        /**
         * A method to convert Trip type values
         * to Android ContentValues.
         * @param type
         * @param country
         * @param start
         * @param end
         * @param price
         * @param description
         * @param agencyID
         * @return A ContentValues conatining all the values.
         */
        public static ContentValues createContentValues(TripType type, String country, Calendar start, Calendar end, Integer price, String description, long agencyID) {
            ContentValues res = new ContentValues();

            res.put(COLUMN_TYPE, type.toString());
            res.put(COLUMN_COUNTRY, country);
            res.put(COLUMN_START, start.getTimeInMillis());
            res.put(COLUMN_END, end.getTimeInMillis());
            res.put(COLUMN_PRICE, price);
            res.put(COLUMN_DESCRIPTION, description);
            res.put(COLUMN_AGENCYID, agencyID);

            return res;
        }
    }
}
