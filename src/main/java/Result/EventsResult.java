package Result;

import Model.Event;

import java.util.ArrayList;

/**
 * This class is responsible for handling the result for a get request of all events
 */
public class EventsResult {

    private ArrayList<Event> data;

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
        this.data = events;
    }

    public ArrayList<Event> getEvents() {
        return data;
    }

    public void setEvents(ArrayList<Event> events) {
        this.data = events;
    }
}
