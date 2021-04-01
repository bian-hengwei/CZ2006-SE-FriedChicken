package com.ntu.medcheck.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment /*implements OnMapReadyCallback*/ {

    // Required empty public constructor
    public MapFragment() {
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
        return null;
    }

    //functions needed to display map

//    /**
//     *
//     * @param view
//     * @param savedInstanceState
//     * This function is called after onCreateView to ensure that the fragment's root view is non-null
//     */
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//    }
//
//
//    /**
//     *
//     * @param googleMap
//     * This function is triggered when the map is ready to be used
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap){
//    }

}

