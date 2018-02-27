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
    private static final String ID = "id";

    //JSON Keys for reviews
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String URL_MOVIE = "url";


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
        String title,poster,plot,rating,date,id;
        for(int i = 0 ; i < n ; i++){
            tempObject = resultArray.getJSONObject(i);
            title = tempObject.getString(ORIGINAL_TITLE);
            poster = tempObject.getString(POSTER_PATH);
            plot = tempObject.getString(A_PLOT_SYNOPSIS);
            rating = tempObject.getString(USER_RATING);
            date = tempObject.getString(RELEASE_DATE);
            id = tempObject.getString(ID);
            movies[i] = new Movie(title,poster,plot,rating,date,id);
        }
        return movies;
    }
// TODO create a function for picking the videos

    /**
     * This method converts the JSON data to review objects so that it can be used elsewhere
     * @param jsonData The Json data retrieved from the networking connection
     * @return Review array holding usable data
     * @throws JSONException if the json passed was incorrect
     */
    public static MovieReview[] pickReviews(String jsonData) throws JSONException {
        JSONObject mainObject = new JSONObject(jsonData);
        JSONArray resultArray = mainObject.getJSONArray(RESULT_ARRAY);
        int n = resultArray.length();
        MovieReview[] reviews = new MovieReview[n];
        JSONObject tempObject;
        String id,author,content,url;
        for (int i = 0 ; i < n ; i++){
            tempObject = resultArray.getJSONObject(i);
            id = tempObject.getString(ID);
            author = tempObject.getString(AUTHOR);
            content = tempObject.getString(CONTENT);
            url = tempObject.getString(URL_MOVIE);
            reviews[i] = new MovieReview(id,author,content,url);
        }
        return reviews;
    }
}
