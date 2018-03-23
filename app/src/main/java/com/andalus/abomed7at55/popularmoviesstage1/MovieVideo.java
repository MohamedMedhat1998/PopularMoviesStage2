package com.andalus.abomed7at55.popularmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents one video for a specific {@link Movie} object
 */
public class MovieVideo implements Parcelable{

    public static final String YOUTUBE_BASE = "https://www.youtube.com/watch?v=";

    private String mName,mKey;

    /**
     * This constructor fills the whole video information
     * @param name the name of the video
     * @param key the id of the video
     */
    MovieVideo(String name , String key){
        mName = name;
        mKey = key;
    }

    protected MovieVideo(Parcel in) {
        mName = in.readString();
        mKey = in.readString();
    }

    public static final Creator<MovieVideo> CREATOR = new Creator<MovieVideo>() {
        @Override
        public MovieVideo createFromParcel(Parcel in) {
            return new MovieVideo(in);
        }

        @Override
        public MovieVideo[] newArray(int size) {
            return new MovieVideo[size];
        }
    };

    public String getName() {
        return mName;
    }

    public String getKey() {
        return mKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mKey);
    }
}
