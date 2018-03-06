package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.andalus.abomed7at55.popularmoviesstage1.DatabaseContract.TableFavourites;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterClickListener , MyBackgroundTaskCallBacks<String,String> {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private static Movie[] movies;

    private String api;
    private NetworkingManager networkingManager;
    private MovieAdapter movieAdapter;
    private MyBackgroundTask backgroundTask;
    private ContentResolver contentResolver;

    private static final int SETTINGS_REQUEST_CODE = 1;
    private static final int DETAILS_REQUEST_CODE = 2;

    private static final String PARCELABLE_MOVIES = "movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        contentResolver = getContentResolver();
        networkingManager = new NetworkingManager();

        if(savedInstanceState != null){
            movies = (Movie[]) savedInstanceState.getParcelableArray(PARCELABLE_MOVIES);
            movieAdapter = new MovieAdapter(movies,this);
            mRecyclerView.setAdapter(movieAdapter);
        }else{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            int sortType = prefs.getInt(getString(R.string.pref_sort),ApiBuilder.SORT_POPULAR);

            if(sortType != ApiBuilder.SORT_FAVORITE){
                api = ApiBuilder.buildApi(sortType);

                backgroundTask = new MyBackgroundTask(api,this);

                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if(netInfo != null){
                    backgroundTask.execute();
                }else {
                    favoriteProcess();
                    Toast.makeText(getBaseContext(), R.string.network_issue_message,Toast.LENGTH_LONG).show();
                }
            }else{
                favoriteProcess();
            }
        }

        RecyclerView.LayoutManager layoutManager
                = new GridLayoutManager(MainActivity.this,2, LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(PARCELABLE_MOVIES,movies);
    }


    @Override
    public void onItemClicked(int itemPosition) {
        Intent i = new Intent(MainActivity.this,DetailsActivity.class);
        i.putExtra(getString(R.string.movie_position),itemPosition);
        startActivityForResult(i,DETAILS_REQUEST_CODE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.itm_open_settings){
            startActivityForResult(new Intent(MainActivity.this,SettingsActivity.class),SETTINGS_REQUEST_CODE);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTINGS_REQUEST_CODE && resultCode == SettingsActivity.RESULT_NORMAL){
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if(netInfo != null){
                refreshAdapter();
            }else {
                favoriteProcess();
                Toast.makeText(getBaseContext(), R.string.network_issue_message,Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == SETTINGS_REQUEST_CODE && resultCode == SettingsActivity.RESULT_FAVOURITE){
            favoriteProcess();
        }else if(requestCode == DETAILS_REQUEST_CODE && resultCode == DetailsActivity.RESULT_FAVORITE_CHANGED){
            favoriteProcess();
        }
    }

    /**
     * This method updates the shown movies when the user changes sort type
     */
    public void refreshAdapter(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int sortType = prefs.getInt(getString(R.string.pref_sort),ApiBuilder.SORT_POPULAR);
        api = ApiBuilder.buildApi(sortType);
        MyBackgroundTask myBackgroundTask = new MyBackgroundTask(api,this);
        myBackgroundTask.execute();
    }

    /**
     * This is the getter method for the retrieved movies
     * @return Movies array
     */
    public static Movie[] getMovies(){
        return movies;
    }

    @Override
    public String onBackground(String api) {
        try {
            networkingManager.startConnection(api);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return networkingManager.retrieveData();
    }

    @Override
    public void onTaskFinished(String result) {
        try {
            movies = DataPicker.pickData(result);
            movieAdapter = new MovieAdapter(movies,MainActivity.this);
            mRecyclerView.setAdapter(movieAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method runs the adapter when the selected sort type is SORT_FAVOURITE
     */
    private void favoriteProcess(){
        Cursor cursor = contentResolver.query(MoviesContentProvider.buildAppUri(),null,null,null,null);
        cursor.moveToFirst();
        int cnt = cursor.getCount();
        Movie[] favoriteMovies = new Movie[cnt];
        String originalTitle ,  posterPath ,  aPlotSynopsis, userRating, releaseDate, movieId;
        for(int i = 0 ; i < cnt ; i++){
            cursor.moveToPosition(i);
            originalTitle = cursor.getString(cursor.getColumnIndex(TableFavourites.COLUMN_NAME));
            posterPath = cursor.getString(cursor.getColumnIndex(TableFavourites.COLUMN_IMAGE));
            aPlotSynopsis = cursor.getString(cursor.getColumnIndex(TableFavourites.COLUMN_SYNOPSIS));
            userRating = cursor.getString(cursor.getColumnIndex(TableFavourites.COLUMN_RATING));
            releaseDate = cursor.getString(cursor.getColumnIndex(TableFavourites.COLUMN_DATE));
            movieId = "" + cursor.getLong(cursor.getColumnIndex(DatabaseContract._ID));
            favoriteMovies[i] = new Movie(originalTitle,posterPath,aPlotSynopsis,userRating,releaseDate,movieId);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.pref_sort),ApiBuilder.SORT_FAVORITE);
        editor.apply();

        movies = favoriteMovies;
        movieAdapter = new MovieAdapter(movies,this);
        mRecyclerView.setAdapter(movieAdapter);
    }
}
