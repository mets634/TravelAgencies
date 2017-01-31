package com.example.java5777.travelagencies.model.datasource;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import com.example.java5777.travelagencies.model.entities.Address;
import com.example.java5777.travelagencies.model.entities.Agency;
import com.example.java5777.travelagencies.model.entities.Trip;
import com.example.java5777.travelagencies.model.entities.TripType;
import com.example.java5777.travelagencies.model.entities.User;

import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.AgencyEntry;
import com.example.java5777.travelagencies.model.datasource.TravelAgenciesContract.TripEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.net.URL;
import java.util.GregorianCalendar;

import javax.net.ssl.HttpsURLConnection;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by yonah on 1/10/17.
 */

/**
 * A class to give access to online database.
 */
public class PHPService {
    private static final String BASE_URI = "HTTP://yonahm.vlab.jct.ac.il/";
    private static final String QUERY_ACTION_URI = "action=query";
    private static final String INSERT_ACTION_URI = "action=insert";
    private static final String AGENCIES_TABLE_URI= "table=Agencies";
    private static final String TRIPS_TABLE_URI = "table=Trips";



    // Data source management methods

    /**
     * Inserts new agency into data source.
     * @see Agency
     * @param cv The new agency to insert.
     */
    public static void insertAgency(ContentValues cv) throws Exception {
        cv.put("table", "Agencies");
        cv.put("action", "insert");

        try {
            String res = POST(BASE_URI, cv);
        }
        catch (Exception e) {
            throw new Exception("Unable To Insert New Agency");
        }
    }


    /**
     * @see Agency
     * @return Copy of business's in the data source.
     */
    public static ArrayList<Agency> cloneAgencyArrayList() throws Exception {
        String uri = BASE_URI + "?" + QUERY_ACTION_URI + "&" + AGENCIES_TABLE_URI;

        try {
            String res = GET(uri);

            ArrayList<Agency> agencies = parseJSONAgency(res);
            return agencies;
        }
        catch (Exception e) {
            throw new Exception("Failed To Retreive Agencies");
        }
    }


    /**
     * Inserts new trip into data source.
     * @see Trip
     * @param cv The new trip to insert.
     */
    public static void insertTrip(ContentValues cv) throws Exception {
        cv.put("table", "Trips");
        cv.put("action", "insert");

        try {
            String res = POST(BASE_URI, cv);
        }
        catch (Exception e) {
            throw new Exception("Unable To Insert New Agency");
        }
    }

    /**
     * @see Trip
     * @return Copy of trips in the data source.
     */
    public static ArrayList<Trip> cloneTripArrayList() throws Exception {
        String uri = BASE_URI + "?" + QUERY_ACTION_URI + "&" + TRIPS_TABLE_URI;

        try {
            String res = GET(uri);
            ArrayList<Trip> trips = parseJSONTrip(res);
            return trips;
        }
        catch (Exception e) {
            throw new Exception("Failed To Retreive Agencies");
        }
    }



    // HTTP METHODS

    /**
     * A method to make a get request to a given url.
     * @param uri The url to go to
     * @return A HttpURLConnection object containing result.
     * @throws Exception
     */
    private static String GET(String uri) throws Exception {
        URL obj = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();

            // print result
            return response.toString();
        }
        else
            return "";
    }

    /**
     * A method to make a post request to a given url.
     * @param url The url to go to
     * @param params The content values to send.
     * @return A HttpURLConnection object containing result.
     * @throws Exception
     */
    public static String POST(String url, ContentValues params) throws IOException {

        //Convert Map<String,Object> into key=value&key=value pairs.
        StringBuilder postData = new StringBuilder();
        for (String param : params.keySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param, "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(params.get(param)), "UTF-8"));
        }

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(postData.toString().getBytes("UTF-8"));
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        else return "";
    }



    /**
     * A method to convert a JSON object in a
     * form of a String to a list of Agencies.
     * @param msg String containing JSON.
     * @return ArrayList of agencies from input.
     * @throws Exception
     */
    private static ArrayList<Agency> parseJSONAgency(String msg) throws Exception {
        ArrayList<Agency> res = new ArrayList<>();
        try {
            // retrieve all the agencies
            JSONArray objects = new JSONArray(msg);
            for (int i = 0; i < objects.length(); i++) {
                // convert each object in array to
                // agency and insert into res

                JSONObject agency = objects.getJSONObject(i);
                res.add(new Agency(
                        agency.getLong(AgencyEntry.COLUMN_ID),
                        agency.getString(AgencyEntry.COLUMN_NAME),
                        agency.getString(AgencyEntry.COLUMN_EMAIL),
                        agency.getString(AgencyEntry.COLUMN_PHONENUMBER),
                        new Address(
                                agency.getString(AgencyEntry.COLUMN_COUNTRY),
                                agency.getString(AgencyEntry.COLUMN_CITY),
                                agency.getString(AgencyEntry.COLUMN_STREET)
                        ),
                        agency.getString(AgencyEntry.COLUMN_WEBSITE)
                ));
            }
            
        }
        catch (Exception e) {
            throw new Exception("Unable to Convert JSON to Agencies");
        }

        return res;
    }

    /**
     * A method to convert a JSON String to an ArrayList of type Trip.
     * @param msg The JSON to convert.
     * @return
     * @throws Exception
     */
    private static ArrayList<Trip> parseJSONTrip(String msg) throws Exception {
        ArrayList<Trip> res = new ArrayList<>();
        try {
            // retrieve all the agencies
            JSONArray objects = new JSONArray(msg);
            for (int i = 0; i < objects.length(); i++) {
                // convert each object in array to
                // agency and insert into res

                JSONObject trip = objects.getJSONObject(i);
                String type = trip.getString(TripEntry.COLUMN_TYPE);
                String country = trip.getString(TripEntry.COLUMN_COUNTRY);

                GregorianCalendar start = new GregorianCalendar();
                start.setTimeInMillis( trip.getLong(TripEntry.COLUMN_START) );

                GregorianCalendar end = new GregorianCalendar();
                end.setTimeInMillis( trip.getLong(TripEntry.COLUMN_END) );

                int price = trip.getInt(TripEntry.COLUMN_PRICE);
                String description = trip.getString(TripEntry.COLUMN_DESCRIPTION);
                long id = trip.getLong(TripEntry.COLUMN_AGENCYID);

                res.add(new Trip(
                        TripType.valueOf( type ), country, start, end, price, description, id
                ));
            }
        }
        catch (Exception e) {
            return new ArrayList<>();
        }

        return res;
    }
}
