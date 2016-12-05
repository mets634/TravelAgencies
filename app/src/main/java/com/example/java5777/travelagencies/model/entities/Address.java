package com.example.java5777.travelagencies.model.entities;

/**
 * Created by yonah on 11/29/2016.
 */

import android.content.ContentValues;

/**
 * A class to represent an address
 * by its country, city, and street name.
 */
public class Address {
    /**
     * Class Address's constructor.
     * @param country
     * @param city
     * @param street
     */
    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    public final static String COUNTRY_VALUE = "country";
    public final static String CITY_VALUE = "city";
    public final static String STREET_VALUE = "street";

    /**
     * A method to convert Address values to
     * an Android ContentValues.
     * @param country
     * @param city
     * @param street
     * @return Values as a ConventValues.
     */
    public static ContentValues toContentValues(String country, String city, String street) {
        ContentValues res = new ContentValues();

        res.put(COUNTRY_VALUE, country);
        res.put(CITY_VALUE, city);
        res.put(STREET_VALUE, street);

        return res;
    }

    public final String country;
    public final String city;
    public final String street;

}
