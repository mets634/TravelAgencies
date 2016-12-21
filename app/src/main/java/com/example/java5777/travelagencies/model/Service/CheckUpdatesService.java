package com.example.java5777.travelagencies.model.Service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.java5777.travelagencies.controller.MainActivity;
import com.example.java5777.travelagencies.model.backend.DSManager;
import com.example.java5777.travelagencies.model.backend.DSManagerFactory;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.HasBeenUpdatedEntry;

/**
 * A class that implements a service that will check every
 * 'interval' number of seconds whether there has been a
 * change in the data source.
 * @// TODO: 12/6/2016 CHANGE ACCESS FROM USING DATABASE TO USING CONTENT PROVIDER. 
 */
public class CheckUpdatesService extends Service {
    private static final String TAG = "CheckUpdatesService";
    private final static int INTERVAL = 10;

    private final static String ACTION = "ACTION_UPDATE";
    private final static String EXTRA = "EXTRA";

    private final static String USER_EXTRA = "USER_EXTRA";
    private final static String AGENCY_EXTRA = "AGENCY_EXTRA";
    private final static String TRIP_EXTRA = "TRIP_EXTRA";

    private boolean isRunning = false;
    private DSManager manager;



    /**
     * OnCreate implementation for service.
     * Initiates manager field.
     */
    @Override
    public void onCreate() {
        manager = DSManagerFactory.getDSManager(DSManagerFactory.LIST); // initiate manager
        isRunning = true;
    }

    /**
     * onStartCommand implementation. Checks every
     * 'INTERVAL' seconds whether data source has
     * been updated.
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // creating new async task to
        // check for update in background
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                while (isRunning) {
                    try {
                        Thread.sleep(INTERVAL * 1000);

                        // check if any updates happened
                        // and broadcast them

                        Intent intent = null;

                        // get has been updated
                        Cursor current = getContentResolver().query(HasBeenUpdatedEntry.CONTENT_URI, null, null, null, null);

                        if (userUpdated(current)) {
                            intent = new Intent(ACTION);
                            intent.putExtra(EXTRA, USER_EXTRA);
                            sendBroadcast( intent );
                        }


                        if (agencyUpdated(current)) {
                            intent = new Intent(ACTION);
                            intent.putExtra(EXTRA, AGENCY_EXTRA);
                            sendBroadcast( intent );
                        }

                        if (tripUpdated(current)) {
                            intent = new Intent(ACTION);
                            intent.putExtra(EXTRA, TRIP_EXTRA);
                            sendBroadcast( intent );
                        }


                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
                return null;
            }
        }.execute();

        return Service.START_STICKY;
    }

    /**
     * Implementation of onBind. Does nothing.
     * @param arg0 Irrelevant.
     * @return null.
     */
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * onDestroy implementation.
     */
    @Override
    public void onDestroy() {
        isRunning = false;
    }


    // helpful methods

    private boolean userUpdated(Cursor c) {
        return HasBeenUpdatedEntry.UserHasBeenUpdated( c );
    }

    private boolean agencyUpdated(Cursor c) {
        return HasBeenUpdatedEntry.AgencyHasBeenUpdated( c );
    }

    private boolean tripUpdated(Cursor c) {
        return HasBeenUpdatedEntry.TripHasBeenUpdated( c );
    }
}
