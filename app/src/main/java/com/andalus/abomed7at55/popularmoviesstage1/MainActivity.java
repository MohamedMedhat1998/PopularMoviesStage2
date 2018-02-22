package com.andalus.abomed7at55.popularmoviesstage1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
        // TODO Handle "there is no internet connection , couldn't retrieve data"
        // TODO Clean Code in the main activity

        //ApiBuilder Test
        final String test = ApiBuilder.buildApi();
        //Log.d("api",test);

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
    }
    public static Movie[] getMovies(){
        return movies;
    }
}
