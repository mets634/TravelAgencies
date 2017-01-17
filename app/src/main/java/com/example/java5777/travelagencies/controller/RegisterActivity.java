package com.example.java5777.travelagencies.controller;

import android.content.ContentValues;
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
import com.example.java5777.travelagencies.model.SharedPreferences.MySharedPreferences;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;

public class RegisterActivity extends AppCompatActivity {
    // view components
    private EditText username;
    private EditText password;
    private CheckBox rememberMeBox;
    private Button registerButton;

    // shared preferences
    private MySharedPreferences prefs;

    /**
     * Implementation of onCreate method to
     * initialize all view components and shared preferences.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialize view components
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rememberMeBox = (CheckBox) findViewById(R.id.rememberMeBox);
        registerButton = (Button) findViewById(R.id.registerButton);

        // initialize shared preferences
        prefs = new MySharedPreferences(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * A OnClick method for the register button.
     * @param v the view.
     */
    public void registerButtonOnClick(View v) {
        // get use credentials
        final String[] credentials = new String[] {username.getText().toString(), password.getText().toString() };

        // attempt to register
        RegisterAsyncTask at = new RegisterAsyncTask(this, credentials);
        at.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * A class to attempt to register a user
     * using a background asynctask
     */
    class RegisterAsyncTask extends AsyncTask<Void, Void, Integer> {
        private final RegisterActivity currentContext; // will hold current context
        private final String[] credentials;

        /**
         * Class constructor that sets the context of the caller.
         * @param context The context to use.
         */
        public RegisterAsyncTask(RegisterActivity context, final String[] credentials) {
            currentContext = context;
            this.credentials = credentials;
        }

        /**
         * Attempts to register new user.
         * @param params User Credentials
         * @return Whether succeeded or not.
         */
        @Override
        protected Integer doInBackground(Void... params) {
            ContentValues cv = TravelAgenciesContract.UserEntry.createContentValues(1, credentials[0], credentials[1]);
            return Integer.valueOf( // return CODE
                    getContentResolver().insert(TravelAgenciesContract.UserEntry.CONTENT_URI, cv).getLastPathSegment()
            );
        }

        /**
         * Handle the result of registering.
         * @param result Code from insert query.
         * @see TravelAgenciesContract For code information.
         */
        @Override
        protected void onPostExecute(Integer result) {
            Intent intent = null;

            switch(result) {

                case TravelAgenciesContract.CODE_USER_EXISTS:
                    Toast.makeText(currentContext, "Username already taken", Toast.LENGTH_SHORT).show();

                    break;

                case TravelAgenciesContract.CODE_ERROR:
                    Toast.makeText(currentContext, "Register failed", Toast.LENGTH_SHORT).show();

                    // go back to login screen
                    intent = new Intent(currentContext, LoginActivity.class);
                    startActivity(intent);

                    break;

                case TravelAgenciesContract.CODE_SUCCESS:
                    if (rememberMeBox.isChecked()) { // save user credentials in shared preferences
                        prefs.putUserName( credentials[0] );
                        prefs.putPassword( credentials[1] );
                    }

                    // go to main screen
                    intent = new Intent(currentContext, MainOptionsActivity.class);
                    startActivity(intent);

                    break;
            }
        }
    }
}
