package com.example.java5777.travelagencies.model.entities;

/**
 * Created by yonah on 11/29/2016.
 */

/**
 * A class to represent an address
 * by its country, city, and street name.
 * @version 1.0
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

    public final String country;
    public final String city;
    public final String street;

}
