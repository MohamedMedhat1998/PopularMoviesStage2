package com.andalus.abomed7at55.popularmoviesstage1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * This class is used to set up the connection to the server to bring data
 */

public class NetworkingManager {

    //The request method for httpUrlConnection
    private static final String REQUEST_GET = "GET";
    //This string holds the data coming from the connection
    private String jsonData;


    // TODO Change main Activity to be a custom list view or recyclerView
    // TODO Display the posters in the main activity
    // TODO Handle "there is no internet connection , couldn't retrieve data"
    /**
     * Use this method to start the connection
     * @param api the api that returns the json data
     * @throws IOException if the api isn't valid
     */
    public void startConnection(String api) throws IOException {
        //connecting
        URL url = new URL(api);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(REQUEST_GET);
        urlConnection.connect();
        //reading
        InputStream in = urlConnection.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        Scanner scanner = new Scanner(bufferedInputStream);
        scanner.useDelimiter("\\A");
        jsonData = scanner.next();
    }

    /**
     * This method is used to read the json data retrieved from the internet
     * @throws NullPointerException if the {@link #startConnection(String)} method failed to make
     * the connection
     */
    public String retrieveData() throws NullPointerException{
        return jsonData;
    }
}
