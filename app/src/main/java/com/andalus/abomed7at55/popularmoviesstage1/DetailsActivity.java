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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        ActionBar mActionBar = getSupportActionBar();
        if(mActionBar != null){
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        receiveAndShowData();
    }

    /**
     * This method brings the data from the selected movie in the main activity class and displays
     * them in the details activity
     */
    private void receiveAndShowData(){
        //Receiving
        Intent i = getIntent();
        int position = i.getExtras().getInt(getString(R.string.movie_position));
        Movie selectedMovie = MainActivity.getMovies()[position];
        String title = selectedMovie.getTitle();
        String posterPath = selectedMovie.getPoster();
        String plot = selectedMovie.getPlot();
        String rating = selectedMovie.getRating();
        String releaseDate = selectedMovie.getDate();
        float ratingFloat = Float.parseFloat(rating);
        int ratingColor;
        if(ratingFloat >= 8){
            ratingColor = getResources().getColor(R.color.rating_best);
        }else if(ratingFloat >= 6){
            ratingColor = getResources().getColor(R.color.rating_above_normal);
        }else if(ratingFloat >= 4){
            ratingColor = getResources().getColor(R.color.rating_normal);
        }else if(ratingFloat >= 2){
            ratingColor = getResources().getColor(R.color.rating_under_normal);
        }else {
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
