package com.andalus.abomed7at55.popularmoviesstage1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Object,Object,String>(){


            @Override
            protected String doInBackground(Object... objects) {
                NetworkingManager manager = new NetworkingManager();
                try {
                    manager.startConnection("https://api.myjson.com/bins/kap5l");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return manager.retrieveData();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("DATA",s);
            }
        }.execute();
    }

}
