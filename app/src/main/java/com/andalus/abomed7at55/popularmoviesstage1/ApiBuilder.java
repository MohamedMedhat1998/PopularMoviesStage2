package com.andalus.abomed7at55.popularmoviesstage1;

import android.net.Uri;

/**
 * This class is used to build the api that should be passed to startConnection in {@link NetworkingManager}
 */

public class ApiBuilder {
    //api components
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "api.themoviedb.org";
    private static final String PATH_SEGMENT_1 = "3";
    private static final String PATH_SEGMENT_2 = "movie";
    private static final String PATH_SEGMENT_TOP_RATED = "top_rated";
    private static final String PATH_SEGMENT_POPULAR = "popular";
    private static final String API_KEY = "api_key";

    //sort types
    public static final int SORT_TOP_RATED = 0;
    public static final int SORT_POPULAR = 1;

    /**
     * This method builds the full api that should be passed to startConnection in {@link NetworkingManager}
     * @param sortBy Chooses the sorting method of the movie , pass {@link #SORT_TOP_RATED} or {@link #SORT_POPULAR}
     * @return the full api
     */
    public static String buildApi(int sortBy){
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(SCHEME);
        uriBuilder.authority(AUTHORITY);
        uriBuilder.appendPath(PATH_SEGMENT_1).appendPath(PATH_SEGMENT_2);
        if(sortBy == SORT_TOP_RATED)
            uriBuilder.appendPath(PATH_SEGMENT_TOP_RATED);
        else if(sortBy == SORT_POPULAR)
            uriBuilder.appendPath(PATH_SEGMENT_POPULAR);
        uriBuilder.appendQueryParameter(API_KEY,Keys.API_KEY);
        Uri uri = uriBuilder.build();
        return uri.toString();
    }

    /**
     * This method builds the full api that should be passed to startConnection in {@link NetworkingManager}.
     * This method uses the default sort which is {@link #SORT_POPULAR}
     * @return the full api
     */
    public static String buildApi(){
        return buildApi(SORT_POPULAR);
    }
}
