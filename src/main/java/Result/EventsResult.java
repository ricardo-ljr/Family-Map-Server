package Result;

import Model.Event;

import java.util.ArrayList;

/**
 * This class is responsible for handling the result for a get request of all events
 */
public class EventsResult {

    private ArrayList<Event> events;

    /**
     * Initializes empty constructor
     */
    public EventsResult() {};

    /**
     * Constructor for result of event's data
     *
     * @param events
     */
    public EventsResult(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
