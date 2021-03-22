package com.example.maptest.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.maptest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;



public class mapFragment_default extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    GoogleMap map;
    MapView mapView;
    View mView;

    String choice;
    private Spinner clinic_choice;
    private Button search;


    public mapFragment_default() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map_default, container, false);

        clinic_choice = (Spinner) mView.findViewById(R.id.spinner);
        clinic_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get selection
                choice = parent.getItemAtPosition(position).toString();
            }
            @Override
            //put nothing here
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        search = (Button) mView.findViewById(R.id.searchbutton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice == "CHAS clinics"){
                    ((Screening_CentreActivity)getActivity()).replaceFragment_CHAS();
                }
                else if (choice == "Cervical screening centres"){
                    ((Screening_CentreActivity)getActivity()).replaceFragment_cervical();
                }
                else{
                    ((Screening_CentreActivity)getActivity()).replaceFragment_breast();
                }
            }
        });

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) mView.findViewById(R.id.mapview);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //set camera to be on Singapore
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198), 10f));
        map.setMinZoomPreference(1);

            /*try {
                KmlLayer cervicalkmlLayer = new KmlLayer(map, R.raw.cervicalkml, getContext());
                cervicalkmlLayer.addLayerToMap();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                KmlLayer breastkmlLayer = new KmlLayer(map, R.raw.breastkml, getContext());
                breastkmlLayer.addLayerToMap();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                KmlLayer chaskmlLayer = new KmlLayer(map, R.raw.chaskml, getContext());
                chaskmlLayer.addLayerToMap();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
    }

}