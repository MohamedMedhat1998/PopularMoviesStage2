package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements MyBackgroundTaskCallBacks<String, String> {

    @BindView(R.id.tv_original_title)
    TextView tvOriginalTitle;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_plot_synopsis)
    TextView tvPlot;
    @BindView(R.id.tv_user_rating)
    TextView tvRating;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    //Rating Colors
    private static final int RATING_HIGHEST = 8;
    private static final int RATING_ABOVE_NORMAL = 6;
    private static final int RATING_NORMAL = 4;
    private static final int RATING_UNDER_NORMAL = 2;

    private static final int FLAG_REVIEW = 0;
    private static final int FLAG_VIDEO = 1;

    private String id;
    private int flag;

    private MyBackgroundTask myBackgroundTask;
    private NetworkingManager networkingReview;
    private NetworkingManager networkingVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        receiveAndShowData();
    }

    /**
     * This method brings the data from the selected movie in the main activity class and displays
     * them in the details activity
     */
    private void receiveAndShowData() {
        //Receiving
        Intent i = getIntent();
        int position = i.getExtras().getInt(getString(R.string.movie_position));
        Movie selectedMovie = MainActivity.getMovies()[position];
        String title = selectedMovie.getTitle();
        String posterPath = selectedMovie.getPoster();
        String plot = selectedMovie.getPlot();
        String rating = selectedMovie.getRating();
        String releaseDate = selectedMovie.getDate();
        id = selectedMovie.getId();
        float ratingFloat = Float.parseFloat(rating);
        int ratingColor;
        if (ratingFloat >= RATING_HIGHEST) {
            ratingColor = getResources().getColor(R.color.rating_best);
        } else if (ratingFloat >= RATING_ABOVE_NORMAL) {
            ratingColor = getResources().getColor(R.color.rating_above_normal);
        } else if (ratingFloat >= RATING_NORMAL) {
            ratingColor = getResources().getColor(R.color.rating_normal);
        } else if (ratingFloat >= RATING_UNDER_NORMAL) {
            ratingColor = getResources().getColor(R.color.rating_under_normal);
        } else {
            ratingColor = getResources().getColor(R.color.rating_worst);
        }
        //Displaying
        tvOriginalTitle.setText(title);
        Picasso.with(this)
                .load(posterPath)
                .error(android.R.drawable.stat_notify_error)
                .into(ivPoster);
        tvPlot.setText(plot);
        tvRating.setText(rating);
        tvRating.setTextColor(ratingColor);
        tvReleaseDate.setText(releaseDate);
        reviews();
        videos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * This method handles the whole process for displaying reviews
     */
    private void reviews() {
        flag = FLAG_REVIEW;
        //Reviews
        networkingReview = new NetworkingManager();
        String apiReview = ApiBuilder.buildApi(id, ApiBuilder.REVIEW);
        myBackgroundTask = new MyBackgroundTask(apiReview, this);
        myBackgroundTask.execute();
    }

    /**
     * This method handles the whole process for displaying trailers
     */
    private void videos() {
        flag = FLAG_VIDEO;
        //videos
        networkingVideos = new NetworkingManager();
        String apiVideos = ApiBuilder.buildApi(id, ApiBuilder.VIDEOS);
        myBackgroundTask = new MyBackgroundTask(apiVideos, this);
        myBackgroundTask.execute();
    }

    @Override
    public String onBackground(String data) {
        String result = null;
        try {
            if (flag == FLAG_VIDEO) {
                networkingVideos.startConnection(data);
                result = networkingVideos.retrieveData();
            }
            if (flag == FLAG_REVIEW) {
                networkingReview.startConnection(data);
                result = networkingReview.retrieveData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onTaskFinished(String result) {
        if(result == null) return;

        if (flag == FLAG_VIDEO) {
            Log.d("VIDEO",result);
        }
        if (flag == FLAG_REVIEW) {
            Log.d("REVIEW",result);
        }
    }
}
