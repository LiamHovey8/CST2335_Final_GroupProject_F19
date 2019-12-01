package com.example.cst2335_final_groupproject_f19;

public class CarChargingStation {
    private String locationName, latitude, longitude, contactPhone;

    //private String locationDistance;

    public CarChargingStation() {

    }

    public CarChargingStation(String locationName, String latitude, String longitude, String contactPhone) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this. contactPhone = contactPhone;
        //this.locationDistance = locationDistance;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    /*public String getLocationDistance() {
        return locationDistance;
    }*/
}
