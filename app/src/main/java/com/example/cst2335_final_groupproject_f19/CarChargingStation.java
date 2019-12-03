package com.example.cst2335_final_groupproject_f19;

/**
 * Class for storing a CarChargingStation object
 */
public class CarChargingStation {
    /**
     * Stores the id from the associated database
     */
    private Long id;

    /**
     * String variables associated with the CarChargingStation
     */
    private String locationName, latitude, longitude, contactPhone;

    /**
     * Default no-arg constructor
     */
    public CarChargingStation() {

    }

    /**
     * Constructor for CarChargingStation object in the initial search ListView
     *
     * @param locationName
     * @param latitude
     * @param longitude
     * @param contactPhone
     */
    public CarChargingStation(String locationName, String latitude, String longitude, String contactPhone) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactPhone = contactPhone;
    }

    /**
     * Constructor for CarChargingStation object created in ListView pulled from database
     *
     * @param id
     * @param locationName
     * @param latitude
     * @param longitude
     * @param contactPhone
     */
    public CarChargingStation(Long id, String locationName, String latitude, String longitude, String contactPhone) {
        this.id = id;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactPhone = contactPhone;
    }

    /**
     * Getter for locationName
     *
     * @return locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Getter for latitude
     *
     * @return latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Getter for longitude
     *
     * @return longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Getter for contact telephone
     *
     * @return contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * Getter for database generated ID
     *
     * @return id
     */
    public Long getId() {
        return id;
    }
}
