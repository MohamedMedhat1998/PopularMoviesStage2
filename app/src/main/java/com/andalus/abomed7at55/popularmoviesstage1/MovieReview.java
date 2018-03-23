package com.andalus.abomed7at55.popularmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents one review for a specific {@link Movie} object
 */

public class MovieReview implements Parcelable{

    private String mId,mAuthor,mContent,mUrl;

    /**
     * This constructor fills the whole review information
     * @param id the id of the review
     * @param author the author of the review
     * @param content the content of the review
     * @param url the link of the review
     */
    public MovieReview(String id,String author,String content,String url){
        mId = id;
        mAuthor = author;
        mContent = content;
        mUrl = url;
    }

    protected MovieReview(Parcel in) {
        mId = in.readString();
        mAuthor = in.readString();
        mContent = in.readString();
        mUrl = in.readString();
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };

    public String getId() {
        return mId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mAuthor);
        parcel.writeString(mContent);
        parcel.writeString(mUrl);
    }
}
