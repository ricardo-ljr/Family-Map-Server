package Result;

import java.util.Objects;

/**
 * This class is responsible for handling the results
 * of querying a single event by id
 */
public class EventByIdResult extends ResultBool {

    /**
     * The event's unique identifier
     */
    private String eventID;

    /**
     * The event's associated username
     */
    private String associatedUsername;

    /**
     * The event's latitude location
     */
    private float latitude;

    /**
     * The event's longitude location
     */
    private float longitude;

    /**
     * The event's country name in which it occurred
     */
    private String country;

    /**
     * The event's city name in which it occurred
     */
    private String city;

    /**
     * The event's type (such as baptism, christening, marriage, death, etc...)
     */
    private String eventType;

    /**
     * The event's year in which it occurred
     */
    private int year;

    /**
     * The event's unique identifier for a person
     */
    private String personID;

    /**
     * This is a non-empty constructor for each event result
     *
     * @param eventID A unique identifies for an event
     * @param associatedUsername Event's associated username
     * @param latitude Latitude of event's location
     * @param longitude Longitude of event's location
     * @param country Country in which event occurred
     * @param city City in which event occurred
     * @param eventType The type of event
     * @param year The year in which the event occurred
     * @param personID Event's unique personID associated with
     */
    public EventByIdResult(String associatedUsername, String eventID, String personID,  float latitude, float longitude, String country, String city, String eventType, int year) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        success = true;
    }

    public EventByIdResult() {
        success = true;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventByIdResult that = (EventByIdResult) o;
        return Float.compare(that.latitude, latitude) == 0 && Float.compare(that.longitude, longitude) == 0 && year == that.year && eventID.equals(that.eventID) && associatedUsername.equals(that.associatedUsername) && country.equals(that.country) && city.equals(that.city) && eventType.equals(that.eventType) && personID.equals(that.personID);
    }

}
