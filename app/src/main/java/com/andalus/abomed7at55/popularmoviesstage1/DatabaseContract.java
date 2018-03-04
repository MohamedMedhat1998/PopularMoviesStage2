package com.andalus.abomed7at55.popularmoviesstage1;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This class represents the structure of the database
 */

public class DatabaseContract implements BaseColumns {

    /**
     * This class represents a single table
     */
    public static class TableFavourites{

        public static final String TABLE_NAME = "favourites";

        //Columns
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_DATE = "date";

    }
}
