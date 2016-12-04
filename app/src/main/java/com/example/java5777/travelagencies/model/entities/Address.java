package com.example.java5777.travelagencies.model.entities;

/**
 * Created by yonah on 11/29/2016.
 */

/**
 * A class to represent an address
 * by its country, city, and street name.
 * @version 1.0
 * @// TODO: 11/29/2016 Choose between option 1 and option 2. 
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

    // Option 1:
    public final String country;
    public final String city;
    public final String street;
    
    /* Option 2:
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    private String country;
    private String city;
    private String street;
    */
}
