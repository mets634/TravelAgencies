package com.example.java5777.travelagencies.model.entities;

/**
 * Created by yonah on 11/29/2016.
 */

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * A class representing a user of the application.
 */
public class User {
    /**
     * Class User's constructor.
     * @param ID User's ID.
     * @param username User's username.
     * @param password User's password.
     */
    public User(long ID, String username, Password password) {
        setID(ID);
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + ID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public final static String ID_VALUE = "ID";
    public final static String USERNAME_VALUE = "username";
    public final static String PASSWORD_VALUE = "password";

    public final static String CURSOR_ID = "ID";
    public final static String CURSOR_USERNAME = "username";
    public final static String CURSOR_HASH = "hash";
    public final static String CURSOR_SALT = "salt";

    public static final String[] CURSOR_COLUMNS = { CURSOR_ID, CURSOR_USERNAME, CURSOR_HASH, CURSOR_SALT };

    /**
     * A method to convert User class type values
     * to an Android ContentValues.
     * @param ID User's ID.
     * @param username User's username.
     * @param password User's password.
     * @return ContentValue containing the values.
     */
    public static ContentValues toContentValues(long ID, String username, String password) {
        ContentValues res = new ContentValues();

        res.put(ID_VALUE, ID);
        res.put(USERNAME_VALUE, username);
        res.put(PASSWORD_VALUE, password);

        return res;
    }

    /**
     * A method to convert a cursor to a
     * list of users.
     * @param users Cursor that must contain result of users.
     * @return An ArrayList of users.
     * @throws Exception
     */
    public static ArrayList<User> cursorToUsers(Cursor users) throws Exception {
        if (!users.getColumnNames().equals(CURSOR_COLUMNS)) // incorrect cursor
            throw new IllegalArgumentException("Cursor Must Contain a Result of Users");

        ArrayList<User> res = new ArrayList<>();

        users.moveToFirst();

        do { // iterate through each result

            // retrieve user data
            long ID = users.getLong( users.getColumnIndex(CURSOR_ID) );
            String username = users.getString( users.getColumnIndex(CURSOR_USERNAME) );
            String hash = users.getString( users.getColumnIndex(CURSOR_HASH) );
            String salt = users.getString( users.getColumnIndex(CURSOR_SALT)) ;

            // create new user and add it to list
            res.add(new User(ID, username, new Password(hash, salt)));
        } while (users.moveToNext());

        return res;
    }


    // Getters and setters

    public long getID() {
        return ID;
    }

    public void setID(long userID) {
        if (userID < 0)
            throw new IllegalArgumentException("User ID must be a positive number");
        this.ID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }


    // Attributes

    private long ID;
    private String username;
    private Password password;
}
