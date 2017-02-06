package com.example.java5777.travelagencies.model.entities;

/**
 * Created by yonah on 11/29/2016.
 */

import android.content.ContentValues;

import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.AgencyEntry;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!country.equals(address.country)) return false;
        if (!city.equals(address.city)) return false;
        return street.equals(address.street);

    }

    @Override
    public int hashCode() {
        int result = country.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + street.hashCode();
        return result;
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
