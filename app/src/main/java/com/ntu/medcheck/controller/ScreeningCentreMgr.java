package com.ntu.medcheck.controller;

public class ScreeningCentreMgr {

    /**
     * search according to the type of check up from the database
     */
    public void search(/* type of checkup */){}

    /**
     * get the current geolocation of the device
     */
    public void getGeolocation(){}

    /**
     * get geolocation of clinics and draw on maps
     */
    public void getClinics(){} // return geolocation of clinics to the map and show

    /**
     * get information like phone number, operating time etc.
     * @param clinic
     * @return A string containing relative information
     */
    public String getClinicInfo(String clinic) {return "";}
}
