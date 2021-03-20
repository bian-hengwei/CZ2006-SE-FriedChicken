package com.example.test.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;

public class ScreeningCentre {
    String typeOfCheckup;
    String clinicName;
    LocalTime openingHour;
    LocalTime closingHour;
    String hyperlink;
    String contactNumber;
    String email;

    public void setTypeOfCheckup(String typeOfCheckup) {
        this.typeOfCheckup = typeOfCheckup;
    }

    public String getTypeOfCheckup() {
        return typeOfCheckup;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicName() {
        return clinicName;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setOpeningHour(int hour, int min) {
        LocalTime openingHour = LocalTime.of(hour, min);
        this.openingHour = openingHour;
    }

    public LocalTime getOpeningHour() {
        return openingHour;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setClosingHour(int hour, int min) {
        LocalTime closingHour = LocalTime.of(hour, min);
        this.closingHour = closingHour;
    }

    public LocalTime getClosingHour() {
        return closingHour;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
