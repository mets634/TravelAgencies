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

    private long ID; // @// TODO: 12/20/2016 MAY REMOVE.
    private String username;
    private Password password;
}
