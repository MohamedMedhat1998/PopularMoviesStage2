package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements MyBackgroundTaskCallBacks<String, String>{

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
    @BindView(R.id.review_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.rv_videos)
    RecyclerView videoRecyclerView;

    //Rating Colors
    private static final int RATING_HIGHEST = 8;
    private static final int RATING_ABOVE_NORMAL = 6;
    private static final int RATING_NORMAL = 4;
    private static final int RATING_UNDER_NORMAL = 2;

    private static final char FLAG_REVIEW = 'R';
    private static final char FLAG_VIDEO = 'V';

    private String id;

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

        //RecyclerView setting up
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager videoLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        videoRecyclerView.setLayoutManager(videoLayoutManager);
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
            networkingVideos.startConnection(data);
            result = FLAG_VIDEO+networkingVideos.retrieveData();
            networkingReview.startConnection(data);
            result = FLAG_REVIEW+networkingReview.retrieveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onTaskFinished(String result) {
        if(result == null) return;
        //TODO fix this bug
        if (result.charAt(0)==FLAG_VIDEO) {
            Log.d("Here","I came here");
            result = resetResult(result);
            try {
                final MovieVideo[] movieVideos = DataPicker.pickVideos(result);
                VideosAdapter videosAdapter = new VideosAdapter(movieVideos, new AdapterClickListener() {
                    @Override
                    public void onItemClicked(int itemPosition) {
                        openLink(MovieVideo.YOUTUBE_BASE+movieVideos[itemPosition].getKey());
                    }
                });
                videoRecyclerView.setAdapter(videosAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (result.charAt(0)==FLAG_REVIEW) {
            result = resetResult(result);
            try {
                final MovieReview[] movieReviews = DataPicker.pickReviews(result);
                ReviewsAdapter reviewsAdapter = new ReviewsAdapter(movieReviews, new AdapterClickListener() {
                    @Override
                    public void onItemClicked(int itemPosition) {
                        openLink(movieReviews[itemPosition].getUrl());
                    }
                });
                mRecyclerView.setAdapter(reviewsAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is a special method for resetting the json data only. It removes the first char
     * which represents a token or flag
     * @param result the json data to be reset
     */
    private String resetResult(String result){
        StringBuilder stringBuilder = new StringBuilder(result);
        stringBuilder.deleteCharAt(0);
        result = stringBuilder.toString();
        return result;
    }

    /**
     * This method opens the given link in the browser
     * @param url the link to be opened
     */
    private void openLink(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
