package com.example.java5777.travelagencies.controller;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java5777.travelagencies.R;
import com.example.java5777.travelagencies.model.Service.CheckUpdatesService;
import com.example.java5777.travelagencies.model.SharedPreferences.MySharedPreferences;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.*;

public class LoginActivity extends AppCompatActivity {
    // view components
    private Button loginButton;
    private Button clearButton;
    private Button registerButton;
    private EditText username;
    private EditText password;
    private CheckBox rememberMeBox;

    // shared preferences
    MySharedPreferences prefs;

    // service intent
    public static Intent service = null;

    /**
     * Initialize activity.
     * @param savedInstanceState no meaning.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize view components
        loginButton = (Button) findViewById(R.id.loginButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rememberMeBox = (CheckBox) findViewById(R.id.rememberMeBox);

        // initialize shared preferences
        prefs = new MySharedPreferences(this);

        // set text editors to value inside shared preference
        username.setText(prefs.getUserName());
        password.setText(prefs.getPassword());

        // initialize service intent
        service = new Intent(this, CheckUpdatesService.class);

        // start service
        this.startService(service);
    }

    /**
     * Ends the service.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Clear button handler.
     * @param v View on context.
     */
    public void clearButtonOnClick(View v) {
        username.setText("", TextView.BufferType.EDITABLE);
        password.setText("", TextView.BufferType.EDITABLE);
    }

    /**
     * Register button handler.
     * @param v View on context.
     */
    public void registerButtonOnClick(View v) {
        // go to register activity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * check if user is in the system and check if it's a correct password
     * if it's the user, save him in the shared preferences file and move to the main options screen
     * else go to the register screen
     */
    public void loginButtonOnClick(View v) {
        // hold onto credentials
        final String[] credentials = { username.getText().toString(), password.getText().toString() };

        // attempt login
        LoginAsyncTask at = new LoginAsyncTask(this, credentials);
        at.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    /**
     * A class to attempt to login a user
     * using a background asynctask
     */
    class LoginAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private final LoginActivity currentContext; // will hold current context
        private final String[] credentials;

        /**
         * Class constructor that sets the context of the caller.
         * @param context The context to use.
         */
        public LoginAsyncTask(LoginActivity context, final String[] credentials) {
            currentContext = context;
            this.credentials = credentials;
        }

        /**
         * Attempts to login.
         * @param params User Credentials
         * @return Whether succeeded or not.
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            // query for user
            Cursor res = getContentResolver().query(UserEntry.CONTENT_URI, null, null, credentials, null);
            return res.getCount() > 0;
        }

        /**
         * Calls next method to handle the
         * @param result
         */
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                //checks if the user wants to be remembered
                if (rememberMeBox.isChecked()) {
                    prefs.putUserName(username.getText().toString());
                    prefs.putPassword(password.getText().toString());
                }
                // commence login
                Intent intent = new Intent(LoginActivity.this, MainOptionsActivity.class);
                startActivity(intent);
            } else { // not a user
                // reset password to empty string
                password.setText("", TextView.BufferType.EDITABLE);

                // toast user for relogin
                Toast.makeText(currentContext, "Login Failed... Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
