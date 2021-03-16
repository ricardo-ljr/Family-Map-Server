package Result;

import Model.Event;

import java.util.ArrayList;

/**
 * This class is responsible for handling the result for a get request of all events
 */
public class EventsResult extends ResultBool {

    private Event[] data;

    /**
     * Initializes empty constructor
     */
    public EventsResult() {
        success = true;
    };

    /**
     * Constructor for result of event's data
     *
     * @param events
     */

    public EventsResult(Event[] events) {
        this.data = events;
    }

    public Event[] getEvents() {
        return data;
    }

    public void setEvents(Event[] events) {

        this.data = events;
    }
}
