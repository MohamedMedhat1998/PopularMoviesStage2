package com.andalus.abomed7at55.popularmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class holds the whole information of the movie
 */

public class Movie implements Parcelable{
    //Movie data
    private String title,poster,plot,rating,date,id;
    private static final String BASE_URL = "http://image.tmdb.org/t/p/w185";

    /**
     * This constructor fills the whole movie information
     * @param originalTitle the title of the movie
     * @param posterPath the path of the poster of the movie
     * @param aPlotSynopsis movie overview
     * @param userRating users rating to the movie
     * @param releaseDate the release date of the movie
     */
    public Movie(String originalTitle , String posterPath , String aPlotSynopsis,String userRating,String releaseDate,String movieId){
        title = originalTitle;
        poster = posterPath;
        plot = aPlotSynopsis;
        rating = userRating;
        date = releaseDate;
        id = movieId;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        poster = in.readString();
        plot = in.readString();
        rating = in.readString();
        date = in.readString();
        id = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getFullPoster() {
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

    public String getPoster(){
        return poster;
    }

    public String getId(){
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(plot);
        parcel.writeString(rating);
        parcel.writeString(date);
        parcel.writeString(id);
    }
}
