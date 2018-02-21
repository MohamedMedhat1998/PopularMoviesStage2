package com.andalus.abomed7at55.popularmoviesstage1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is used to extract real data from JSON Objects
 */
public class DataPicker {
    //JSON keys
    private static final String ORIGINAL_TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String A_PLOT_SYNOPSIS = "overview";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String RESULT_ARRAY = "results";

    /**
     * This method converts the JSON data to movie objects so that it can be used elsewhere
     * @param jsonData The Json data retrieved from the networking connection
     * @return Movie array holding usable data
     * @throws JSONException if the json passed was incorrect
     */
    public static Movie[] pickData(String jsonData) throws JSONException {
        JSONObject mainObject = new JSONObject(jsonData);
        JSONArray resultArray = mainObject.getJSONArray(RESULT_ARRAY);
        int n = resultArray.length();
        Movie[] movies = new Movie[n];
        JSONObject tempObject;
        String title,poster,plot,rating,date;
        for(int i = 0 ; i < n ; i++){
            tempObject = resultArray.getJSONObject(i);
            title = tempObject.getString(ORIGINAL_TITLE);
            poster = tempObject.getString(POSTER_PATH);
            plot = tempObject.getString(A_PLOT_SYNOPSIS);
            rating = tempObject.getString(USER_RATING);
            date = tempObject.getString(RELEASE_DATE);
            movies[i] = new Movie(title,poster,plot,rating,date);
        }
        return movies;
    }

}
