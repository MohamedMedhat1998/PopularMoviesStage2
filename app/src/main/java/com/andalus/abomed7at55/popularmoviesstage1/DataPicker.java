package com.andalus.abomed7at55.popularmoviesstage1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    //JSON Keys for videos
    private static final String NAME = "name";
    private static final String KEY = "key";


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

    /**
     * This method converts the JSON data to review objects so that it can be used elsewhere
     * @param jsonData The Json data retrieved from the networking connection
     * @return Review array holding usable data
     * @throws JSONException if the json passed was incorrect
     */
    public static ArrayList<MovieReview> pickReviews(String jsonData) throws JSONException {
        JSONObject mainObject = new JSONObject(jsonData);
        JSONArray resultArray = mainObject.getJSONArray(RESULT_ARRAY);
        int n = resultArray.length();
        ArrayList<MovieReview> reviews = new ArrayList<>();
        JSONObject tempObject;
        String id,author,content,url;
        for (int i = 0 ; i < n ; i++){
            tempObject = resultArray.getJSONObject(i);
            id = tempObject.getString(ID);
            author = tempObject.getString(AUTHOR);
            content = tempObject.getString(CONTENT);
            url = tempObject.getString(URL_MOVIE);
            reviews.add(new MovieReview(id,author,content,url));
        }
        return reviews;
    }

    /**
     * This method converts the JSON data to video objects so that it can be used elsewhere
     * @param jsonData The Json data retrieved from the networking connection
     * @return Video array holding usable data
     * @throws JSONException if the json passed was incorrect
     */
    public static ArrayList<MovieVideo> pickVideos(String jsonData) throws JSONException{
        JSONObject mainObject = new JSONObject(jsonData);
        JSONArray resultArray = mainObject.getJSONArray(RESULT_ARRAY);
        int n = resultArray.length();
        ArrayList<MovieVideo> videos = new ArrayList<>();
        JSONObject tempObject;
        String name,key;
        for (int i = 0 ; i < n ; i++){
            tempObject = resultArray.getJSONObject(i);
            name = tempObject.getString(NAME);
            key = tempObject.getString(KEY);
            videos.add(new MovieVideo(name,key));
        }
        return videos;
    }
}
