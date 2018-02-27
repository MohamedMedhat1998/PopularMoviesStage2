package com.andalus.abomed7at55.popularmoviesstage1;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

/**
 * This class is responsible for running background threads
 */
public class MyBackgroundTask extends AsyncTask<Object,Object,String> {

    private String mApi;
    private MyBackgroundTaskCallBacks<String,String> mCallBacks;

    public MyBackgroundTask(String api,MyBackgroundTaskCallBacks callBacks){
        mApi = api;
        mCallBacks = callBacks;
    }
    @Override
    protected String doInBackground(Object... objects) {
        return mCallBacks.onBackground(mApi);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mCallBacks.onTaskFinished(s);
    }
}