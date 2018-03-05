package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andalus.abomed7at55.popularmoviesstage1.DatabaseContract.TableFavourites;

/**
 * This class is responsible for accessing the data
 */

public class MoviesContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.andalus.abomed7at55.popularmoviesstage1";
    private static final String SCHEME = "content://";
    private static final String PATH_SINGLE_MOVIE = "movie";
    private static final int SINGLE_MOVIE = 100;
    private SQLiteDatabase database;
    UriMatcher uriMatcher;
    //TODO complete the uri builder

    /**
     * This method builds a uri that matches the matcher in the content provider
     *
     * @param movieId the id of the movie you want to access
     * @return a uri object
     */
    public static Uri buildAppUri(String movieId) {
        Uri.Builder builder = Uri.parse(SCHEME + AUTHORITY)
                .buildUpon()
                .appendPath(PATH_SINGLE_MOVIE)
                .appendPath(movieId);
        return builder.build();
    }

    /**
     * This method builds the matcher which analyze the uris
     *
     * @return a matcher object
     */
    private UriMatcher buildMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, PATH_SINGLE_MOVIE + "/#", SINGLE_MOVIE);
        return matcher;
    }

    /**
     * This method creates a contentValues that should be passed to the insert method from the
     * content resolver
     *
     * @param id       the id of the selected movie
     * @param name     the title of the selected movie
     * @param synopsis the synopsis of the selected movie
     * @param rating   the rating of the selected movie
     * @param date     the release date of the selected movie
     * @return a ContentValues object
     */
    public static ContentValues createContentValues(long id, String name, String synopsis, String rating, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract._ID, id);
        contentValues.put(TableFavourites.COLUMN_NAME, name);
        contentValues.put(TableFavourites.COLUMN_SYNOPSIS, synopsis);
        contentValues.put(TableFavourites.COLUMN_RATING, rating);
        contentValues.put(TableFavourites.COLUMN_DATE, date);
        return contentValues;
    }

    @Override
    public boolean onCreate() {
        DatabaseManager databaseManager = new DatabaseManager(getContext());
        database = databaseManager.getDatabase();
        uriMatcher = buildMatcher();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (uriMatcher.match(uri) == SINGLE_MOVIE) {
            try {
                database.insert(DatabaseContract.TableFavourites.TABLE_NAME, null, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted = -1;
        if(uriMatcher.match(uri) == SINGLE_MOVIE){
            try {
                String idString = uri.getPathSegments().get(1) + "";
                Log.d("idString",idString);
                deleted = database.delete(TableFavourites.TABLE_NAME,DatabaseContract._ID + "=?",new String[]{idString});
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);
        Log.d("Deleted",deleted + "");
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
