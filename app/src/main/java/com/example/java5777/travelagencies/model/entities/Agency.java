package com.example.java5777.travelagencies.model.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;

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

    // ContentValues constants

    public final static String ID_VALUE = "ID";
    public final static String NAME_VALUE = "name";
    public final static String PHONENUMBER_VALUE = "phonenumber";
    public final static String EMAIL_VALUE = "email";
    public final static String WEBSITE_VALUE = "website";

    // Cursor table column-names constant

    public static final String CURSOR_ID = "ID";
    public static final String CURSOR_NAME = "name";
    public static final String CURSOR_COUNTRY = "country";
    public static final String CURSOR_CITY = "city";
    public static final String CURSOR_STREET = "street";
    public static final String CURSOR_PHONENUMBER = "phonenumber";
    public static final String CURSOR_EMAIL = "email";
    public static final String CURSOR_WEBSITE = "website";

    public static final String[] CURSOR_COLUMNS = { CURSOR_ID, CURSOR_NAME, CURSOR_COUNTRY, CURSOR_CITY, CURSOR_STREET, CURSOR_PHONENUMBER, CURSOR_EMAIL, CURSOR_WEBSITE };

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
    public static ContentValues toContentValue(long ID, String name, ContentValues address, String phoneNumber, String email, String website) {
        ContentValues res = new ContentValues();

        res.put(ID_VALUE, ID);
        res.put(NAME_VALUE, name);
        res.putAll(address);
        res.put(PHONENUMBER_VALUE, phoneNumber);
        res.put(EMAIL_VALUE, email);
        res.put(WEBSITE_VALUE, website);

        return res;
    }

    public static ArrayList<Agency> cursorToAgencies(Cursor agencies) throws Exception {
        if (!agencies.getColumnNames().equals(CURSOR_COLUMNS)) // incorrect cursor
            throw new IllegalArgumentException("Cursor Must Contain a Result of Agencies");

        ArrayList<Agency> res = new ArrayList<>();

        agencies.moveToFirst();

        do { // iterate through each result

            // retrieve agency data
            long ID = agencies.getLong( agencies.getColumnIndex(CURSOR_ID) );
            String name = agencies.getString( agencies.getColumnIndex(CURSOR_NAME) );
            String country = agencies.getString( agencies.getColumnIndex(CURSOR_COUNTRY) );
            String city = agencies.getString( agencies.getColumnIndex(CURSOR_CITY) );
            String street = agencies.getString( agencies.getColumnIndex(CURSOR_STREET ) );
            String phonenumber = agencies.getString( agencies.getColumnIndex(CURSOR_PHONENUMBER) );
            String email = agencies.getString( agencies.getColumnIndex(CURSOR_EMAIL) );
            String website = agencies.getString( agencies.getColumnIndex(CURSOR_WEBSITE) );

            // create new agency and add it to list
            res.add(new Agency(ID, name, email, phonenumber, new Address(country, city, street), website));
        } while (agencies.moveToNext());

        return res;
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
