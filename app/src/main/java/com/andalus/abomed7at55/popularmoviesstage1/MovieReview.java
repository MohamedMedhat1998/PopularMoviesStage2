package com.andalus.abomed7at55.popularmoviesstage1;

/**
 * This class represents one review for a specific {@link Movie} object
 */

public class MovieReview {

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
}
