package com.andalus.abomed7at55.popularmoviesstage1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.rg_sort_by)
    RadioGroup rgSortBy;
    @BindView(R.id.rb_popular)
    RadioButton rbPopular;
    @BindView(R.id.rb_top_rated)
    RadioButton rbTopRated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ButterKnife.bind(this);

        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if(id == R.id.rb_popular){
                    Log.d("checked","POPULAR");
                }else if (id == R.id.rb_top_rated){
                    Log.d("checked","TOP_RATED");
                }
            }
        });
    }
}
