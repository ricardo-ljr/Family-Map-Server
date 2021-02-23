package Services;

import Result.EventByIdResult;

/**
 * This class is responsible for returning data for an event by its id
 */
public class EventsByIdService {

    /**
     * Initializes empty constructor
     */
    public EventsByIdService() {}

    /**
     * This method will query and return a specific event by its id and user's auth token,
     * as it's required
     *
     * @param eventID Unique identifier for the event
     * @param authToken Authorization Token required to retrieve a single event object
     * @return This will return the body of a single event if successful
     */
    public EventByIdResult getEventById(String eventID, String authToken) {
        return null;
    }
}
