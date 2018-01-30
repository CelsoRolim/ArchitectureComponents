package com.samsung.architecturecompcodelab.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.samsung.architecturecompcodelab.R;
import com.samsung.architecturecompcodelab.viewmodels.WeatherDetailViewModel;

public class DetailActivity extends AppCompatActivity {

    private WeatherDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //mViewModel = ViewModelProviders.of(this).get(WeatherDetailViewModel.class);
    }
}
