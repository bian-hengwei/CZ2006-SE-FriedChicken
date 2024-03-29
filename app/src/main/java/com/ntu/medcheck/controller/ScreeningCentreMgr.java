package com.ntu.medcheck.controller;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Layer;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon;
import com.google.maps.android.data.geojson.GeoJsonPoint;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.ntu.medcheck.R;
import com.ntu.medcheck.utils.SafeOnClickListener;
import com.ntu.medcheck.view.AddCheckupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Fu Yongding
 */
public class ScreeningCentreMgr extends Fragment implements OnMapReadyCallback {
    private int ACCESS_LOCATION_REQUEST_CODE = 10001;
    GoogleMap map;
    MapView mapView;
    View mView;

    String choice;
    private Spinner clinic_choice;
    private Button search;

    public TextView text_clinic_name;
    public TextView text_clinic_streetname;
    public TextView text_clinic_postalcode;
    public TextView text_clinic_hyperlink;

    private RequestQueue mQueue;


    /**
     * empty public constructor
     */
    public ScreeningCentreMgr() {}

    /**
     * Method to create and return the view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false); //get view
        //get textviews
        text_clinic_name = mView.findViewById(R.id.clinic_name);
        text_clinic_streetname = mView.findViewById(R.id.clinic_streetname);
        text_clinic_postalcode = mView.findViewById(R.id.clinic_postalcode);
        text_clinic_hyperlink = mView.findViewById(R.id.clinic_hyperlink);

        //dropdown box for selection of clinics
        clinic_choice = mView.findViewById(R.id.spinner);
        clinic_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //listen whenever an option in dropdown box selected
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get selection
                choice = parent.getItemAtPosition(position).toString();
                Log.i("choice", "choice is " + choice);
            }

            @Override
            //put nothing here
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        search = mView.findViewById(R.id.searchbutton); //get search button

        //Conduct search whenever search button is clicked
        search.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                if (choice.equals(mView.getResources().getString(R.string.clinicCervical))) {
                    try {
                        //reset the map before adding layers onto it
                        map.clear();
                        //resetting all textviews to be empty
                        text_clinic_name.setText("");
                        text_clinic_hyperlink.setText("");
                        text_clinic_postalcode.setText("");
                        text_clinic_streetname.setText("");
                        cervicalLayer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (choice.equals(mView.getResources().getString(R.string.clinicBreast))) {
                    try {
                        //reset the map before adding layers onto it
                        map.clear();
                        //resetting all textviews to be empty
                        text_clinic_name.setText("");
                        text_clinic_hyperlink.setText("");
                        text_clinic_postalcode.setText("");
                        text_clinic_streetname.setText("");
                        breastLayer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (choice.equals(mView.getResources().getString(R.string.clinicCHAS))) {
                    try {
                        //reset the map before adding layers onto it
                        map.clear();
                        //resetting all textviews to be empty
                        text_clinic_name.setText("");
                        text_clinic_hyperlink.setText("");
                        text_clinic_postalcode.setText("");
                        text_clinic_streetname.setText("");
                        chasLayer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Please select a type of clinic", Toast.LENGTH_SHORT).show(); //show message to remind user to select clinic type
                }
            }
        });
        return mView;
    }

    /**
     * called after onCreateView
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = mView.findViewById(R.id.mapview); //getting mapview view

        //initialising mapview
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }


        view.findViewById(R.id.addtoschedule).setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                String clinic_choice = text_clinic_name.getText().toString(); //get the string of at clinic name textview
                if (clinic_choice.isEmpty()) return;
                String confirmed_clinic_choice = clinic_choice.substring(8);
                //Log.i("testingstring", "clinic string is" + confirmed_clinic_choice);
                String type_of_checkup = null;
                if (choice.equals(mView.getResources().getString(R.string.clinicCervical))) {
                    type_of_checkup = "Cervical checkup";
                } else if (choice.equals(mView.getResources().getString(R.string.clinicBreast))) {
                    type_of_checkup = "Breast checkup";
                } else if (choice.equals(mView.getResources().getString(R.string.clinicCHAS))) {
                    type_of_checkup = "Others";
                } else return;
                mapView.onDestroy(); //destroy map before going to another activity
                Intent i = new Intent();
                i.putExtra("Clinic name set", confirmed_clinic_choice); //passing the clinic name string through the intent
                i.putExtra("type of checkup", type_of_checkup);
                //Log.i("testingstring", "clinic string is" + confirmed_clinic_choice);
                //Log.i("testingstring", "clinic string is" + type_of_checkup);
                getActivity().setResult(Activity.RESULT_OK, i);
                getActivity().finish();
            }
        });
    }

    /**
     * Method called after initialising mapview
     * @param googleMap Google Map object
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //user location
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i("permission granted", "going to call enableUserLocation function");
            enableUserLocation();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                //show user a dialog
                Log.i("request permission", "ask user for permission here");
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
                Log.i("request permission2", "user asked");
            }
        }

        //set camera to be on Singapore
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198), 10f));
        map.setMinZoomPreference(1);

        Log.i("map check", "map finish loading");
    }

    /**
     * method to enable location service
     */
    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if denied, go here
            return;
        }
        Log.i("location", "location is enabled here");
        map.setMyLocationEnabled(true);
    }

    /**
     * Method to reset the colours of the markers in the GeoJsonLayer
     * @param geoJsonLayer a GeoJsonLayer
     */
    public void resetLayer(GeoJsonLayer geoJsonLayer){
        BitmapDescriptor normalIcon = BitmapDescriptorFactory.defaultMarker(); //normal icon
        GeoJsonPointStyle geoJsonPointStyle = new GeoJsonPointStyle();
        geoJsonPointStyle.setIcon(normalIcon);
        for (GeoJsonFeature feature : geoJsonLayer.getFeatures()) {
            feature.setPointStyle(geoJsonPointStyle); //change color of the feature clicked
        }
    }

    /**
     * A method to display location of cervical screening centres
     * @throws IOException
     * @throws JSONException
     */
    public void cervicalLayer() throws IOException, JSONException{
        mQueue = Volley.newRequestQueue(getContext());
        String apiUrl = "https://data.gov.sg/api/action/resource_show?id=21c6f2dd-7b8d-418a-8d7c-c5f8de1ca184"; //api for CKAN Resource Show

        //get json file from apiUrl
        JsonObjectRequest apiObjectRequest = new JsonObjectRequest
                (Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String cervicalUrl = null;
                        JSONObject result = null;

                        try {
                            result = response.getJSONObject("result"); //get the result object
                            cervicalUrl = result.getString("url"); //getting string value of "url" value
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //get cervical geojson file from cervicalUrl
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.GET, cervicalUrl, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        GeoJsonLayer cervicalgeojsonlayer = new GeoJsonLayer(map,response); //creating layer
                                        cervicalgeojsonlayer.addLayerToMap(); //putting layer on google map
                                        //set onclick listener for the markers placed on the map
                                        cervicalgeojsonlayer.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                                            @Override
                                            public void onFeatureClick(Feature feature) {
                                                resetLayer(cervicalgeojsonlayer);

                                                // Get the icon for the feature
                                                BitmapDescriptor pointIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE); //set color
                                                GeoJsonPointStyle geoJsonPointStyle = new GeoJsonPointStyle();
                                                geoJsonPointStyle.setIcon(pointIcon);
                                                ((GeoJsonFeature) feature).setPointStyle(geoJsonPointStyle); //change color of the feature clicked



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
                                                text_clinic_name.setText("Clinic: "+ clinicName); //set clinic name to textview

                                                String hyperLink = description.substring(hyperlink_index+19, landxaddresspoint_index-38);
                                                text_clinic_hyperlink.setText("Website: " + hyperLink); //set website to textview
                                            }
                                        });
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                });
                        // Access the RequestQueue through your singleton class.
                        mQueue.add(jsonObjectRequest);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        // Access the RequestQueue through your singleton class.
        mQueue.add(apiObjectRequest);

        //set camera to be on Singapore
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198), 10f));
        map.setMinZoomPreference(1);
    }


    /**
     * A method to display location of breast screening centres
     * @throws IOException
     * @throws JSONException
     */
    public void breastLayer() throws IOException, JSONException{
        mQueue = Volley.newRequestQueue(getContext());
        String apiUrl = "https://data.gov.sg/api/action/resource_show?id=7ca09c2e-112f-4e8d-b476-738e5a91fc7f"; //api for CKAN Resource Show
        //get json file from apiUrl
        JsonObjectRequest apiObjectRequest = new JsonObjectRequest
                (Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String breastUrl = null;
                        JSONObject result = null;

                        try {
                            result = response.getJSONObject("result"); // get the result object
                            breastUrl = result.getString("url"); //getting string value of "url" value
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //get breast geojson file from breastUrl
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.GET, breastUrl, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        GeoJsonLayer breastgeojsonlayer = new GeoJsonLayer(map,response); //creating layer
                                        breastgeojsonlayer.addLayerToMap(); //putting layer on google map
                                        //set onclick listener for the markers placed on the map
                                        breastgeojsonlayer.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                                            @Override
                                            public void onFeatureClick(Feature feature) {
                                                resetLayer(breastgeojsonlayer);

                                                // Get the icon for the feature
                                                BitmapDescriptor pointIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE); //set color
                                                GeoJsonPointStyle geoJsonPointStyle = new GeoJsonPointStyle();
                                                geoJsonPointStyle.setIcon(pointIcon);
                                                ((GeoJsonFeature) feature).setPointStyle(geoJsonPointStyle); //change color of the feature clicked

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
                                                text_clinic_name.setText("Clinic: "+ clinicName); //set clinic name to textview

                                                String hyperLink = description.substring(hyperlink_index+19, landxaddresspoint_index-38);
                                                text_clinic_hyperlink.setText("Website: " + hyperLink); //set website to textview
                                            }
                                        });
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                });
                        // Access the RequestQueue through your singleton class.
                        mQueue.add(jsonObjectRequest);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        // Access the RequestQueue through your singleton class.
        mQueue.add(apiObjectRequest);

        //set camera to be on Singapore
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198), 10f));
        map.setMinZoomPreference(1);
    }

    /**
     * A method to display location of CHAS clinics
     * @throws IOException
     * @throws JSONException
     */
    public void chasLayer() throws IOException, JSONException{
        mQueue = Volley.newRequestQueue(getContext());
        String apiUrl = "https://data.gov.sg/api/action/resource_show?id=cb94adc3-aaf6-4352-982e-01014f6a5716"; //api for CKAN Resource Show
        //get json file from apiUrl
        JsonObjectRequest apiObjectRequest = new JsonObjectRequest
                (Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String chasUrl = null;
                        JSONObject result = null;

                        try {
                            result = response.getJSONObject("result"); // get the result object
                            chasUrl = result.getString("url"); //getting string value of "url" value
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //get chas geojson file from breastUrl
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.GET, chasUrl, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        GeoJsonLayer chasgeojsonlayer = new GeoJsonLayer(map,response); //creating layer
                                        chasgeojsonlayer.addLayerToMap(); //putting layer on google map
                                        //set onclick listener for the markers placed on the map
                                        chasgeojsonlayer.setOnFeatureClickListener(new Layer.OnFeatureClickListener() {
                                            @Override
                                            public void onFeatureClick(Feature feature) {
                                                resetLayer(chasgeojsonlayer);

                                                // Get the icon for the feature
                                                BitmapDescriptor pointIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE); //set color
                                                GeoJsonPointStyle geoJsonPointStyle = new GeoJsonPointStyle();
                                                geoJsonPointStyle.setIcon(pointIcon);
                                                ((GeoJsonFeature) feature).setPointStyle(geoJsonPointStyle); //change color of the feature clicked

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
                                                text_clinic_name.setText("Clinic: "+ clinicName); //set clinic name on textview
                                            }
                                        });
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                });
                        // Access the RequestQueue through your singleton class.
                        mQueue.add(jsonObjectRequest);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        // Access the RequestQueue through your singleton class.
        mQueue.add(apiObjectRequest);

        //set camera to be on Singapore
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198), 10f));
        map.setMinZoomPreference(1);
    }
}
