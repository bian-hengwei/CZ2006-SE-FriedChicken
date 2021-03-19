package com.example.test;

public class Factory {
    public Entry getEntry(String entryType) {
        if(entryType == null) {
            return null;
        }

        if(entryType.equalsIgnoreCase("checkup")) {
            return new CheckUpEntry();
        }

        else if(entryType.equalsIgnoreCase("medication")) {
            return new MedicationEntry();
        }
        return null;
    }

    public ScreeningCentre getScreeningCentre() {
        return new ScreeningCentre();
    }

    public Schedule getSchedule() {
        return new Schedule();
    }
}
