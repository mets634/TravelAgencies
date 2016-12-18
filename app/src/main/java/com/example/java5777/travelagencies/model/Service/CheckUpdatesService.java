package com.example.java5777.travelagencies.model.Service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.java5777.travelagencies.controller.MainActivity;
import com.example.java5777.travelagencies.model.backend.DSManager;
import com.example.java5777.travelagencies.model.backend.DSManagerFactory;

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

    private boolean isRunning = false;
    private DSManager manager;



    /**
     * OnCreate implementation for service.
     * Initiates manager field.
     */
    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");

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
        Log.i(TAG, "Service onStartCommand");

        // creating new async task to
        // check for update in background
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                while (isRunning) {
                    try {
                        Thread.sleep(INTERVAL * 1000);
                        //if (manager.hasBeenUpdated()) {

                            // construct intent and send broadcast
                        Intent intent = new Intent();
                        intent.setAction(ACTION);
                        intent.putExtra("yonah", "mann");
                        sendBroadcast(intent);
                        //}
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
        Log.i(TAG, "Service onBind");
        return null;
    }

    /**
     * onDestroy implementation.
     */
    @Override
    public void onDestroy() {
        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }
}
