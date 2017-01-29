package com.example.java5777.travelagencies.model.datasource;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.java5777.travelagencies.model.backend.DSManager;
import com.example.java5777.travelagencies.model.backend.DSManagerFactory;
import com.example.java5777.travelagencies.model.entities.TripType;

/**
 * Created by yonah on 11/30/2016.
 */

/**
 * A class to implement the
 * application's content provider.
 * @see TravelAgenciesContract How to use the content provider.
 */
public class TravelAgenciesProvider extends ContentProvider {
    private final static int AGENCY_URI_ID = 1;
    private final static int USER_URI_ID = 2;
    private final static int TRIP_URI_ID = 3;
    private final static int HASBEENUPDATED_URI_ID = 4;

    private static DSManager manager = DSManagerFactory.getDSManager(DSManagerFactory.PHP);
    private static DSManager usersManager = DSManagerFactory.getDSManager(DSManagerFactory.LIST);
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Set the URI's
    static {
        sUriMatcher.addURI(TravelAgenciesContract.CONTENT_AUTHORITY, TravelAgenciesContract.PATH_AGENCY, AGENCY_URI_ID);
        sUriMatcher.addURI(TravelAgenciesContract.CONTENT_AUTHORITY, TravelAgenciesContract.PATH_USER, USER_URI_ID);
        sUriMatcher.addURI(TravelAgenciesContract.CONTENT_AUTHORITY, TravelAgenciesContract.PATH_TRIP, TRIP_URI_ID);
        sUriMatcher.addURI(TravelAgenciesContract.CONTENT_AUTHORITY, TravelAgenciesContract.PATH_HASBEENUPDATED, HASBEENUPDATED_URI_ID);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch(sUriMatcher.match(uri)) {

            case AGENCY_URI_ID:
                return manager.getAgencies();

            case TRIP_URI_ID:
                return manager.getTravelAgencyTrips();

            case USER_URI_ID:
                if (selectionArgs.length != 2)
                    return null;
                return usersManager.getUsers(selectionArgs[0], selectionArgs[1]);

            case HASBEENUPDATED_URI_ID:
                return manager.hasBeenUpdated();

            default:
                throw new IllegalArgumentException("Unrecognized Query-Table");
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch(sUriMatcher.match(uri)) {
            case AGENCY_URI_ID:
                int code = manager.InsertAgency(values);
                return ContentUris.withAppendedId(TravelAgenciesContract.AgencyEntry.CONTENT_URI, code);

            case TRIP_URI_ID:
                code = manager.InsertTrip(values);
                return ContentUris.withAppendedId(TravelAgenciesContract.TripEntry.CONTENT_URI, code);

            case USER_URI_ID:
                code = usersManager.InsertUser(values);
                return ContentUris.withAppendedId(TravelAgenciesContract.UserEntry.CONTENT_URI, code);

            default:
                throw new IllegalArgumentException("Unrecognized Insertion-Table");
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) { return 0; }
}
