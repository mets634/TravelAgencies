package com.example.java5777.travelagencies.model.datasource;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.java5777.travelagencies.model.backend.DSManager;
import com.example.java5777.travelagencies.model.backend.DSManagerFactory;

/**
 * Created by yonah on 11/30/2016.
 */

/**
 * A class to implement the
 * application's content provider.
 */
public class MyContentProvider extends ContentProvider {
    final static String AGENCY_STRING = "agencies";
    final static String USER_STRING = "users";
    final static String TRIP_STRING = "trips";

    private static DSManager manager = DSManagerFactory.getDSManager("List");
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Set the URI's
    static {
        sUriMatcher.addURI("com.example.java5777.travelagencies", AGENCY_STRING, 1);
        sUriMatcher.addURI("com.example.java5777.travelagencies", USER_STRING, 2);
        sUriMatcher.addURI("com.example.java5777.travelagencies", TRIP_STRING, 3);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table = uri.getPath().substring(1); // remove preceding '/'

        if (table.equalsIgnoreCase(AGENCY_STRING))
        {
            try {
                return manager.getAgencies();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        else if (table.equalsIgnoreCase(TRIP_STRING))
        {
            try {
                return manager.getTrips();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        throw new IllegalArgumentException("Unrecognized Query");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = uri.getPath().substring(1);
        if (table.equalsIgnoreCase(TRIP_STRING)) {
            manager.InsertTrip(values);
            return null;
        }
        if(table.equalsIgnoreCase(AGENCY_STRING)){
            manager.InsertAgency(values);
            return null;
        }
        if(table.equalsIgnoreCase(USER_STRING)){
            manager.InsertUser(values);
            return null;
        }

        throw new IllegalArgumentException("Unsupported Insertion Table");

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) { return 0; }
}
