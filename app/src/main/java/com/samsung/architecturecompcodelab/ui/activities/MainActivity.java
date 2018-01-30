package com.samsung.architecturecompcodelab.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.samsung.architecturecompcodelab.R;
import com.samsung.architecturecompcodelab.ui.fragments.WeatherListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {

            // TODO
            // look for fragment.TAG
            fragment = WeatherListFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }
}
