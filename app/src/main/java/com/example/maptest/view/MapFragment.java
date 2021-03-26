package com.example.maptest.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.maptest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.kml.KmlLayer;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private int ACCESS_LOCATION_REQUEST_CODE = 10001;
    GoogleMap map;
    MapView mapView;
    View mView;

    String choice;
    private Spinner clinic_choice;
    private Button search;
    private Button back;

    private TextView text_clinic_name;
    private TextView text_clinic_streetname;
    private TextView text_clinic_postalcode;
    private TextView text_clinic_hyperlink;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        text_clinic_name = mView.findViewById(R.id.clinic_name);
        text_clinic_streetname = mView.findViewById(R.id.clinic_streetname);
        text_clinic_postalcode = mView.findViewById(R.id.clinic_postalcode);
        text_clinic_hyperlink = mView.findViewById(R.id.clinic_hyperlink);


        clinic_choice = mView.findViewById(R.id.spinner);
        clinic_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        search = mView.findViewById(R.id.searchbutton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(choice.equals("Cervical screening centres")){
                    try {
                        cervicalLayer();
                        text_clinic_name.setText("");
                        text_clinic_hyperlink.setText("");
                        text_clinic_postalcode.setText("");
                        text_clinic_streetname.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(choice.equals("Breast screening centres")){
                    try {
                        breastLayer();
                        text_clinic_name.setText("");
                        text_clinic_hyperlink.setText("");
                        text_clinic_postalcode.setText("");
                        text_clinic_streetname.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        chasLayer();
                        text_clinic_name.setText("");
                        text_clinic_hyperlink.setText("");
                        text_clinic_postalcode.setText("");
                        text_clinic_streetname.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //temporary back btn
        back = mView.findViewById(R.id.button2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HomepageActivity.class));
                mapView.onDestroy(); //destory mapview before leaving the page
            }
        });

        return mView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = mView.findViewById(R.id.mapview);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    //called when the map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                //show user a dialog
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }
        }

        //set camera to be on Singapore
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198), 10f));
        map.setMinZoomPreference(1);
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }

    //adding cervical screening centres on the map
    public void cervicalLayer() throws IOException, JSONException{
        GeoJsonLayer cervicalgeojsonlayer = new GeoJsonLayer(map, R.raw.cervicalgeojson, getContext());

        //reset the map before adding layers onto it
        map.clear();

        cervicalgeojsonlayer.addLayerToMap();
        cervicalgeojsonlayer.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
            @Override
            public void onFeatureClick(Feature feature) {
                String description = feature.getProperty("Description");
                int addresspostalcode_index = description.lastIndexOf("ADDRESSPOSTALCODE"); //start of ADDRESSPOSTALCODE
                int addressstreetname_index = description.lastIndexOf("ADDRESSSTREETNAME"); //start of ADDRESSTREETNAME
                int addresstype_index = description.lastIndexOf("ADDRESSTYPE"); //start of ADDRESSTYPE
                int name_index = description.lastIndexOf("NAME"); //start of NAME
                int photourl_index = description.lastIndexOf("PHOTOURL"); //start of PHOTOURL
                int hyperlink_index = description.lastIndexOf("HYPERLINK"); //start of HYPERLINK
                int landxaddresspoint_index = description.lastIndexOf("LANDXADDRESSPOINT"); //start of LANDXADDRESSPOINT


                String postalCode = description.substring(addresspostalcode_index+28, addressstreetname_index-38);
                text_clinic_postalcode.setText("Postal code: " + postalCode); //set postal code on textview

                String streetName = description.substring(addressstreetname_index+27, addresstype_index-31);
                text_clinic_streetname.setText("Street name: " + streetName); //set street name on textview

                String clinicName = description.substring(name_index+14, photourl_index-31);
                text_clinic_name.setText("Clinic: "+ clinicName);

                String hyperLink = description.substring(hyperlink_index+19, landxaddresspoint_index-38);
                text_clinic_hyperlink.setText("Website: " + hyperLink);

            }
        });
    }

    //adding breast screening centres on the map
    public void breastLayer() throws IOException, JSONException{
        GeoJsonLayer breastgeojsonlayer = new GeoJsonLayer(map, R.raw.breastgeojson, getContext());

        //reset the map before adding layers onto it
        map.clear();

        breastgeojsonlayer.addLayerToMap();
        breastgeojsonlayer.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
            @Override
            public void onFeatureClick(Feature feature) {
                String description = feature.getProperty("Description");
                int addresspostalcode_index = description.lastIndexOf("ADDRESSPOSTALCODE"); //start of ADDRESSPOSTALCODE
                int addressstreetname_index = description.lastIndexOf("ADDRESSSTREETNAME"); //start of ADDRESSTREETNAME
                int addresstype_index = description.lastIndexOf("ADDRESSTYPE"); //start of ADDRESSTYPE
                int name_index = description.lastIndexOf("NAME"); //start of NAME
                int photourl_index = description.lastIndexOf("PHOTOURL"); //start of PHOTOURL
                int hyperlink_index = description.lastIndexOf("HYPERLINK"); //start of HYPERLINK
                int landxaddresspoint_index = description.lastIndexOf("LANDXADDRESSPOINT"); //start of LANDXADDRESSPOINT


                String postalCode = description.substring(addresspostalcode_index+28, addressstreetname_index-38);
                text_clinic_postalcode.setText("Postal code: " + postalCode); //set postal code on textview

                String streetName = description.substring(addressstreetname_index+27, addresstype_index-31);
                text_clinic_streetname.setText("Street name: " + streetName); //set street name on textview

                String clinicName = description.substring(name_index+14, photourl_index-31);
                text_clinic_name.setText("Clinic: "+ clinicName);

                String hyperLink = description.substring(hyperlink_index+19, landxaddresspoint_index-38);
                text_clinic_hyperlink.setText("Website: " + hyperLink);
            }
        });
    }

    //adding chas clinic centres on the map
    public void chasLayer() throws IOException, JSONException{
        GeoJsonLayer chasgeojsonlayer = new GeoJsonLayer(map, R.raw.chasgeojson, getContext());

        //reset the map before adding layers onto it
        map.clear();

        chasgeojsonlayer.addLayerToMap();
        chasgeojsonlayer.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
            @Override
            public void onFeatureClick(Feature feature) {
                String description = feature.getProperty("Description");
                int hciname_index = description.lastIndexOf("HCI_NAME"); //start of HCI_NAME
                int licence_type_index = description.lastIndexOf("LICENCE_TYPE"); //start of LICENCE_TYPE
                int streetname_index = description.lastIndexOf("STREET_NAME"); //start of ADDRESSTYPE
                int buildingname_index = description.lastIndexOf("BUILDING_NAME"); //start of NAME
                int postalcd_index = description.lastIndexOf("POSTAL_CD"); //start of POSTAL_CD
                int addrtype_index = description.lastIndexOf("ADDR_TYPE"); //start of ADDR_TYPE


                String postalCode = description.substring(postalcd_index+19, addrtype_index-31);
                text_clinic_postalcode.setText("Postal code: " + postalCode); //set postal code on textview

                String streetName = description.substring(streetname_index+21, buildingname_index-38);
                text_clinic_streetname.setText("Street name: " + streetName); //set street name on textview

                String clinicName = description.substring(hciname_index+18, licence_type_index-38);
                text_clinic_name.setText("Clinic: "+ clinicName);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == ACCESS_LOCATION_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                enableUserLocation();
            }else{
                //show a dialog that permission is not granted
            }
        }
    }
}