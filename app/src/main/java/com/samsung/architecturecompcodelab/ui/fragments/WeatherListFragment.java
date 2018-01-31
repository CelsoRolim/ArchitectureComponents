package com.samsung.architecturecompcodelab.ui.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.architecturecompcodelab.R;
import com.samsung.architecturecompcodelab.persistence.entities.WeatherEntry;
import com.samsung.architecturecompcodelab.viewmodels.WeatherListViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherListFragment extends Fragment {

    public static final String TAG = "WeatherListFragment";

    public WeatherListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static Fragment newInstance() {
        WeatherListFragment fragment = new WeatherListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_list, container, false);
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final WeatherListViewModel viewModel =
                ViewModelProviders.of(this).get(WeatherListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(WeatherListViewModel viewModel) {

        viewModel.getObservableWeathers().observe(this, new Observer<List<WeatherEntry>>() {
            @Override
            public void onChanged(@Nullable List<WeatherEntry> weatherEntries) {

                Log.d(TAG, "Codelab WeatherListFragment.onChanged weatherEntries: " + weatherEntries);
                if (weatherEntries != null) {
                    Log.d(TAG, "Codelab weatherEntries.size: " + weatherEntries.size());
                }
            }
        });
    }
}
