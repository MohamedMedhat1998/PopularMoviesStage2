package com.andalus.abomed7at55.popularmoviesstage1;

/**
 * This class represents one video for a specific {@link Movie} object
 */
public class MovieVideo {

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

    public String getName() {
        return mName;
    }

    public String getKey() {
        return mKey;
    }
}
