package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ButterKnife.bind(this);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedType = sharedPreferences.getInt(getString(R.string.pref_sort),ApiBuilder.SORT_POPULAR);

        if(selectedType == ApiBuilder.SORT_POPULAR){
            rgSortBy.check(R.id.rb_popular);
        }else if(selectedType == ApiBuilder.SORT_TOP_RATED){
            rgSortBy.check(R.id.rb_top_rated);
        }

        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if(id == R.id.rb_popular){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(getString(R.string.pref_sort),ApiBuilder.SORT_POPULAR);
                    editor.apply();
                }else if (id == R.id.rb_top_rated){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(getString(R.string.pref_sort),ApiBuilder.SORT_TOP_RATED);
                    editor.apply();
                }
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
