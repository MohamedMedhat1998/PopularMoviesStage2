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

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private static Movie[] movies;
    String api;
    NetworkingManager networkingManager;
    MyBackgroundTask myBackgroundTask;
    MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int sortType = prefs.getInt(getString(R.string.pref_sort),ApiBuilder.SORT_POPULAR);

        api = ApiBuilder.buildApi(sortType);

        networkingManager = new NetworkingManager();

        myBackgroundTask = new MyBackgroundTask(api);

        myBackgroundTask.execute();

        RecyclerView.LayoutManager layoutManager
                = new GridLayoutManager(MainActivity.this,2, LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(layoutManager);


        // TODO add option for sorting
        // TODO Add loading indicator in the main activity
        // TODO Clean Code in the main activity
        // TODO add back button
    }

    /**
     * This class is responsible for running background threads
     */
    private class MyBackgroundTask extends AsyncTask<Object,Object,String>{
        private String mApi;
        public MyBackgroundTask(String api){
            mApi = api;
        }
        @Override
        protected String doInBackground(Object... objects) {
            try {
                networkingManager.startConnection(mApi);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return networkingManager.retrieveData();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                movies = DataPicker.pickData(s);
                movieAdapter = new MovieAdapter(movies);
                mRecyclerView.setAdapter(movieAdapter);
                Log.d("State","POSTED");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int sortType = prefs.getInt(getString(R.string.pref_sort),ApiBuilder.SORT_POPULAR);
        api = ApiBuilder.buildApi(sortType);
        MyBackgroundTask myBackgroundTask = new MyBackgroundTask(api);
        myBackgroundTask.execute();
    }

    /**
     * This is the getter method for the retrieved movies
     * @return Movies array
     */
    public static Movie[] getMovies(){
        return movies;
    }
}
