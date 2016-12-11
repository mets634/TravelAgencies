package com.example.java5777.travelagencies.model.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;

import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.AgencyEntry;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yonah on 11/29/2016.
 */

/**
 * A class that represents an agency
 * using the application.
 */
public class Agency {
    /**
     * Class Agency constructor.
     * @param ID Agency ID.
     * @param name Agency's name.
     * @param email Agency's email address.
     * @param phoneNumber Agency's phone number.
     * @param website Link to business website.
     */
    public Agency(long ID, String name, String email, String phoneNumber, Address address, String website) {
        setID(ID);
        this.website = website;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Agency{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", website=" + website +
                '}';
    }


    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        if (ID < 0)
            throw new IllegalArgumentException("Agency ID must be positive");
        this.ID = ID;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    // Attributes

    private long ID;
    private String name;
    private Address address;
    private String phoneNumber;
    private String email;
    private String website;

}
