package com.example.cst2335_final_groupproject_f19;

/**
 * Class for storing a Car Charging Station object
 */
public class CarChargingStation {
    private Long id;
    private String locationName, latitude, longitude, contactPhone;

    public CarChargingStation() {

    }

    public CarChargingStation(String locationName, String latitude, String longitude, String contactPhone) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactPhone = contactPhone;
    }

    public CarChargingStation(Long id, String locationName, String latitude, String longitude, String contactPhone) {
        this.id = id;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactPhone = contactPhone;
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

    public Long getId() {
        return id;
    }
}
