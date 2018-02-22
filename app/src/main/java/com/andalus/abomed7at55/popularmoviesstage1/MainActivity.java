package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // TODO add option for sorting
        // TODO Add loading indicator in the main activity
        // TODO Clean Code in the main activity
        // TODO add back button


        //ApiBuilder Test
        final String test = ApiBuilder.buildApi();
        //Log.d("api",test);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null){
            new AsyncTask<Object,Object,String>(){


                @Override
                protected String doInBackground(Object... objects) {
                    NetworkingManager manager = new NetworkingManager();
                    try {
                        manager.startConnection(test);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return manager.retrieveData();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //Log.d("DATA",s);
                    try {
                        movies = DataPicker.pickData(s);
                        MovieAdapter adapter = new MovieAdapter(movies);
                        RecyclerView.LayoutManager layoutManager
                                = new GridLayoutManager(MainActivity.this,2, LinearLayoutManager.VERTICAL,false);

                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }else {
            Toast.makeText(getBaseContext(),"Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
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

    /**
     * This is the getter method for the retrieved movies
     * @return Movies array
     */
    public static Movie[] getMovies(){
        return movies;
    }
}
