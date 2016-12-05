package com.example.java5777.travelagencies.model.entities;

/**
 * Created by yonah on 11/29/2016.
 */

import android.content.ContentValues;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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
    public Trip(TripType type, String country, GregorianCalendar start, GregorianCalendar end, Integer price, String description, long agencyID) {
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
                ", start=" + ((Long) start.getTimeInMillis()).toString() +
                ", end=" + ((Long) end.getTimeInMillis()).toString() +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", agencyID='" + agencyID + '\'' +
                '}';
    }

    public final static String TYPE_VALUE = "type";
    public final static String COUNTRY_VALUE = "country";
    public final static String START_VALUE = "start";
    public final static String END_VALUE = "end";
    public final static String PRICE_VALUE = "price";
    public final static String DESCRIPTION_VALUE = "description";
    public final static String AGENCYID_VALUE = "agencyID";

    public static final String CURSOR_TYPE = "type";
    public static final String CURSOR_COUNTRY = "country";
    public static final String CURSOR_START = "start";
    public static final String CURSOR_END = "end";
    public static final String CURSOR_PRICE = "price";
    public static final String CURSOR_DESCRIPTION = "description";
    public static final String CURSOR_AGENCYID = "agencyID";

    public static final String[] CURSOR_COLUMNS = { CURSOR_TYPE, CURSOR_COUNTRY, CURSOR_START, CURSOR_END, CURSOR_PRICE, CURSOR_DESCRIPTION, CURSOR_AGENCYID };

    /**
     * A method to convert Trip type values
     * to Android ContentValues.
     * @param type
     * @param country
     * @param start
     * @param end
     * @param price
     * @param description
     * @param agencyID
     * @return A ContentValues conatining all the values.
     */
    public static ContentValues toContentValues(TripType type, String country, Calendar start, Calendar end, Integer price, String description, long agencyID) {
        ContentValues res = new ContentValues();

        res.put(TYPE_VALUE, type.toString());
        res.put(COUNTRY_VALUE, country);
        res.put(START_VALUE, start.getTimeInMillis());
        res.put(END_VALUE, end.getTimeInMillis());
        res.put(PRICE_VALUE, price);
        res.put(DESCRIPTION_VALUE, description);
        res.put(AGENCYID_VALUE, agencyID);

        return res;
    }

    /**
     * A method to convert a cursor to a list
     * of trips stored in it.
     * @param trips Cursor.
     * @return ArrayList containing result.
     * @throws Exception
     */
    public static ArrayList<Trip> cursorToTrip(Cursor trips) throws Exception {
        if (!trips.getColumnNames().equals(CURSOR_COLUMNS)) // incorrect cursor
            throw new IllegalArgumentException("Cursor Must Contain a Result of Trips");

        ArrayList<Trip> res = new ArrayList<>();

        trips.moveToFirst();

        do { // iterate through each result

            // retrieve trip data
            TripType type = TripType.valueOf( trips.getString( trips.getColumnIndex(CURSOR_TYPE) ) );
            String country = trips.getString( trips.getColumnIndex(CURSOR_COUNTRY) );

            GregorianCalendar start = new GregorianCalendar();
            start.setTimeInMillis( trips.getLong( trips.getColumnIndex(CURSOR_START) ) );

            GregorianCalendar end = new GregorianCalendar();
            end.setTimeInMillis( trips.getLong( trips.getColumnIndex(CURSOR_END) )); ;

            Integer price = trips.getInt( trips.getColumnIndex(CURSOR_PRICE) );
            String description = trips.getString( trips.getColumnIndex(CURSOR_DESCRIPTION) );
            long agencyID = trips.getLong( trips.getColumnIndex(CURSOR_AGENCYID) );


            // create new trip and add it to list
            res.add(new Trip(type, country, start, end, price, description, agencyID));
        } while (trips.moveToNext());

        return res;
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

    public GregorianCalendar getStart() {
        return start;
    }

    public void setStart(GregorianCalendar start) {
        this.start = start;
    }

    public GregorianCalendar getEnd() {
        return end;
    }

    public void setEnd(GregorianCalendar end) {
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
    private GregorianCalendar start;
    private GregorianCalendar end;
    private Integer price;
    private String description;
    private long agencyID;
}
