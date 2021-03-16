package Model;

import DAO.Database;

import java.util.UUID;

/**
 * An event
 */
public class Event {

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
     * Empty constructor for an event
     */
    public Event() {}

    /**
     * Creates an event for a person
     *
     * @param associatedUsername Event's associated username
     * @param latitude Latitude of event's location
     * @param longitude Longitude of event's location
     * @param country Country in which event occurred
     * @param city City in which event occurred
     * @param eventType The type of event
     * @param year The year in which the event occurred
     * @param personID Event's unique personID associated with
     */
    public Event(String eventID, String associatedUsername, float latitude, float longitude, String country, String city, String eventType, int year, String personID) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.personID = personID;

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
        return (float) latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return (float) longitude;
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
        Event event = (Event) o;
        return Double.compare(event.latitude, latitude) == 0 &&
                Double.compare(event.longitude, longitude) == 0 &&
                year == event.year && eventID.equals(event.eventID) &&
                associatedUsername.equals(event.associatedUsername) &&
                country.equals(event.country) && city.equals(event.city) &&
                eventType.equals(event.eventType) &&
                personID.equals(event.personID);
    }

}
