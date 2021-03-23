package com.ntu.medcheck.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.ntu.medcheck.R;

public class mapFragment_default extends Fragment /*implements OnMapReadyCallback*/ {

    /**
     * Constructor for the fragment
     */
    public mapFragment_default() {
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     * This function is called to inflate a view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    }

    //functions needed to display map

    /**
     *
     * @param view
     * @param savedInstanceState
     * This function is called after onCreateView to ensure that the fragment's root view is non-null
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }


    /**
     *
     * @param googleMap
     * This function is triggered when the map is ready to be used
     */
    @Override
    public void onMapReady(GoogleMap googleMap){
    }

}

