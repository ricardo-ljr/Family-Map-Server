package JSONReader;

/**
 * This class keeps track of locations to mimic a location in the file
 */
public class Location {
    private String country;
    private String city;
    private float latitude;
    private float longitude;

    /**
     * Initializes empty constructor
     *
     */
    public Location() {}

    /**
     * Constructor for Location Class
     *
     * @param country Country
     * @param city City
     * @param latitude Latitude
     * @param longitude Longitute
     */
    public Location(String country, String city, float latitude, float longitude) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
