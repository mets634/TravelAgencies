package com.example.java5777.travelagencies.model.entities;

/**
 * Created by yonah on 11/29/2016.
 */

import java.util.Date;

/**
 * A class to represent a trip on the application.
 */
public class Trip {
    /**
     * The constructor for class Trip.
     * @param type Type of trip.
     * @param country Country which trip takes place in.
     * @param start Starting date.
     * @param end Ending date.
     * @param price Trip price.
     * @param description Trip description.
     * @param agencyID ID of business sponsoring the trip.
     */
    public Trip(TripType type, String country, Date start, Date end, Integer price, String description, long agencyID) {
        this.type = type;
        this.country = country;
        this.start = start;
        this.end = end;
        setPrice(price);
        this.description = description;
        this.agencyID = agencyID;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "type=" + type +
                ", country='" + country + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", agencyID='" + agencyID + '\'' +
                '}';
    }


    // Getters and setters

    public TripType getType() {
        return type;
    }

    public void setType(TripType type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        if (price < 0)
            throw new IllegalArgumentException("Price must be positive");
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(long agencyID) {
        this.agencyID = agencyID;
    }


    // Attributes

    private TripType type;
    private String country;
    private Date start;
    private Date end;
    private Integer price;
    private String description;
    private long agencyID;
}
