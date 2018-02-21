package com.andalus.abomed7at55.popularmoviesstage1;

/**
 * This class holds the whole information of the movie
 */

public class Movie {
    //Movie data
    private String title,poster,plot,rating,date;
    private static final String BASE_URL = "http://image.tmdb.org/t/p/w185";

    /**
     * This constructor fills the whole movie information
     * @param originalTitle the title of the movie
     * @param posterPath the path of the poster of the movie
     * @param aPlotSynopsis movie overview
     * @param userRating users rating to the movie
     * @param releaseDate the release date of the movie
     */
    public Movie(String originalTitle , String posterPath , String aPlotSynopsis,String userRating,String releaseDate){
        title = originalTitle;
        poster = posterPath;
        plot = aPlotSynopsis;
        rating = userRating;
        date = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return BASE_URL+poster;
    }

    public String getPlot() {
        return plot;
    }

    public String getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }
}
