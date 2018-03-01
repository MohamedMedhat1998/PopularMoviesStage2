package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class manages the database of the app
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favourite_movies.db";
    private static String DATABASE_PATH;
    private static final String TAG = "DatabaseManager";

    private static SQLiteDatabase sqLiteDatabase;
    private Context mContext;

    /**
     * This constructor initialize the DatabaseManager when instantiated
     *
     * @param context the context of the calling activity
     */
    DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        DATABASE_PATH = context.getFilesDir().getPath() + "/";
    }

    /**
     * This method assigns the database in the app folder which was copied from assets using
     * {@link #startBuffering()} to the app folder to the variable {@link #sqLiteDatabase}
     */
    private void createDatabase() {
        sqLiteDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * This method copies the database file from assets folder to the app folders
     *
     * @throws IOException if the file name is incorrect or if the path is invalid
     */
    private void startBuffering() throws IOException {
        InputStream inputStream = mContext.getAssets().open(DATABASE_NAME);
        OutputStream outputStream = new FileOutputStream(DATABASE_PATH + DATABASE_NAME);
        int sz;
        byte[] buffer = new byte[1024];
        while ((sz = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, sz);
        }
        outputStream.flush();
        outputStream.close();
    }

    /**
     * This is the getter method for the database created from this class methods
     *
     * @return the database object
     */
    public SQLiteDatabase getDatabase() {
        try {
            if (!isCreated()) {
                startBuffering();
                Log.d(TAG, "Copied");
            }
            createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqLiteDatabase;
    }

    /**
     * This method checks if the database is created previously
     * @return true if the database is created and false if it is not created
     */
    private boolean isCreated() {
        SQLiteDatabase temp = null;
        try {
            temp = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(temp!=null) temp.close();
        return temp != null;
    }
}
