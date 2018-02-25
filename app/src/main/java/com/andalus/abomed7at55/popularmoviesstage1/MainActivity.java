package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterClickListener , MyBackgroundTaskCallBacks<String,String> {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private static Movie[] movies;
    String api;
    NetworkingManager networkingManager;
    MovieAdapter movieAdapter;
    MyBackgroundTask backgroundTask;
    private static final int SETTINGS_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int sortType = prefs.getInt(getString(R.string.pref_sort),ApiBuilder.SORT_POPULAR);

        api = ApiBuilder.buildApi(sortType);

        networkingManager = new NetworkingManager();

        backgroundTask = new MyBackgroundTask(api,this);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null){
            backgroundTask.execute();
        }else {
            Toast.makeText(getBaseContext(), R.string.network_issue_message,Toast.LENGTH_LONG).show();
        }


        RecyclerView.LayoutManager layoutManager
                = new GridLayoutManager(MainActivity.this,2, LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onItemClicked(int itemPosition) {
        Intent i = new Intent(MainActivity.this,DetailsActivity.class);
        i.putExtra(getString(R.string.movie_position),itemPosition);
        startActivity(i);
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
        if(requestCode == SETTINGS_REQUEST_CODE && resultCode == RESULT_OK){
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if(netInfo != null){
                refreshAdapter();
            }else {
                Toast.makeText(getBaseContext(), R.string.network_issue_message,Toast.LENGTH_LONG).show();
            }

        }
    }

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
}
